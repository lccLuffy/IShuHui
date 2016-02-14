package lcc.ishuhui.fragment.IView;

import okhttp3.Call;

/**
 * Created by lcc_luffy on 2016/2/1.
 */
public interface IView<Result> {
    void onSuccess(Result result);
    void onFailure(Call call, Exception e);
}
