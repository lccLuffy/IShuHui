package lcc.ishuhui.http.request;

import java.io.IOException;

import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.builders.RequestBuilder;
import lcc.ishuhui.http.callback.HttpCallBack;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class HttpRequest {
    private Call call;
    private Request request;
    private RequestBuilder requestBuilder;

    public HttpRequest(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }
    public Call getCall() {
        return call;
    }

    public void execute(HttpCallBack callBack)
    {
        generateCall();

        HttpUtil.getInstance().execute(this,callBack);
    }

    public Response execute() throws IOException
    {
        generateCall();

        return call.execute();
    }

    private void generateCall()
    {
        call = HttpUtil.getInstance().getOkHttpClient().newCall(request = requestBuilder.generateRequest());
    }
}
