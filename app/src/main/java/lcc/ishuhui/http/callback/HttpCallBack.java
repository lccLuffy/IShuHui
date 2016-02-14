package lcc.ishuhui.http.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public abstract class HttpCallBack<T> {

    /**
     * response: when a request is success,response will be firstly initialized,but when failed,response will always be null.
     */
    public Response response = null;

    public static final HttpCallBack DEFAULT = new DefaultHttpCallBack();

    /**
     * UI thread
     * @param call
     * @param e
     */
    public abstract void onFailure(Call call, Exception e);


    /**
     * not UI thread,so you can do any work
     * @param response
     * @return
     * @throws Exception
     */
    public abstract T parse(Response response) throws Exception;


    /**
     * UI thread
     * @param call
     * @param result
     */
    public abstract void onSuccess(Call call, T result);
}
