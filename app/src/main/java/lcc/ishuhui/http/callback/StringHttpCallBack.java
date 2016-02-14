package lcc.ishuhui.http.callback;

import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public abstract class StringHttpCallBack extends HttpCallBack<String>{
    @Override
    public String parse(Response response) throws Exception {
        return response.body().string();
    }
}
