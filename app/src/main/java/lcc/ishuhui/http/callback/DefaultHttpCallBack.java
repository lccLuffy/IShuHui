package lcc.ishuhui.http.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class DefaultHttpCallBack extends HttpCallBack<Response>{
    @Override
    public void onFailure(Call call, Exception e) {

    }

    @Override
    public Response parse(Response response) throws Exception {
        return response;
    }

    @Override
    public void onSuccess(Call call, Response result) {

    }
}
