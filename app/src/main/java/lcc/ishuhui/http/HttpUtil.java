package lcc.ishuhui.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lcc.ishuhui.http.builders.GetMethodBuilder;
import lcc.ishuhui.http.builders.PostMethodBuilder;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.http.cookie.SimpleCookieJar;
import lcc.ishuhui.http.request.HttpRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class HttpUtil {
    public static final String TAG = "HttpUtil";

    private static OkHttpClient.Builder builder;
    /**
     * config
     */
    public static long READ_TIMEOUT = 10000;
    public static long WRITE_TIMEOUT = 10000;
    public static long CONNECT_TIMEOUT = 5000;



    private OkHttpClient okHttpClient;
    private Handler delivery;

    private static HttpUtil httpUtil;

    private static boolean initialized = false;
    public static OkHttpClient.Builder getBuilder()
    {
        if(initialized)
            throw new RuntimeException("OkHttpClient has been initialized!!");

        initialized = true;

        if(builder == null)
            builder = new OkHttpClient.Builder();
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

        builder.cookieJar(new SimpleCookieJar());
        return builder;
    }

    private HttpUtil()
    {
        delivery = new Handler(Looper.getMainLooper());

        if(!initialized)
            okHttpClient = getBuilder().build();
        else
            okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
    public static HttpUtil getInstance()
    {
        if(httpUtil == null)
        {
            synchronized (HttpUtil.class)
            {
                if(httpUtil == null)
                    httpUtil = new HttpUtil();
            }
        }
        return httpUtil;
    }

    public void execute(HttpRequest httpRequest, HttpCallBack httpCallBack)
    {
        final HttpCallBack finalHttpCallBack = (httpCallBack == null ? HttpCallBack.DEFAULT : httpCallBack);
        httpRequest.getCall().enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                deliveryFailureCallBack(call,e,finalHttpCallBack);
            }

            @Override
            public void onResponse(Call call, Response response){
                finalHttpCallBack.response = response;
                if(response.isSuccessful())
                {
                    try
                    {
                        deliverySuccessCallBack(call,finalHttpCallBack.parse(response),finalHttpCallBack);
                    }
                    catch (Exception e)
                    {
                        deliveryFailureCallBack(call,e,finalHttpCallBack);
                        e.printStackTrace();
                    }

                }
                else
                {
                    try
                    {
                        deliveryFailureCallBack(call,new RuntimeException(response.body().string()),finalHttpCallBack);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void deliveryFailureCallBack(final Call call, final Exception e, final HttpCallBack httpCallBack)
    {
        delivery.post(new Runnable() {
            @Override
            public void run() {
                httpCallBack.onFailure(call,e);
            }
        });
    }

    private void deliverySuccessCallBack(final Call call, final Object result, final HttpCallBack httpCallBack)
    {
        delivery.post(new Runnable() {
            @Override
            public void run() {
                httpCallBack.onSuccess(call,result);
            }
        });
    }

    public static GetMethodBuilder get()
    {
        return new GetMethodBuilder();
    }

    public static PostMethodBuilder post()
    {
        return new PostMethodBuilder();
    }

    public void cancelByTag(Object tag)
    {
        for(Call call : okHttpClient.dispatcher().queuedCalls())
        {
            if(tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for(Call call : okHttpClient.dispatcher().runningCalls())
        {
            if(tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }
}
