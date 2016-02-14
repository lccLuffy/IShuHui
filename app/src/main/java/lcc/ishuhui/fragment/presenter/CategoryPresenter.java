package lcc.ishuhui.fragment.presenter;

import android.support.annotation.NonNull;

import lcc.ishuhui.constants.API;
import lcc.ishuhui.fragment.IView.IView;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.model.BookModel;
import lcc.ishuhui.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/2/1.
 */
public class CategoryPresenter {
    private IView<BookModel> iView;
    public CategoryPresenter(@NonNull IView<BookModel> iView) {
        this.iView = iView;
    }

    public void getData(String classifyId,int pageIndex)
    {
        HttpUtil.post()
                .addParam("ClassifyId", classifyId)
                .addParam("PageIndex", pageIndex + "")
                .url(API.GET_BOOK_BY_PARAM)
                .tag(this)
                .build()
                .execute(new HttpCallBack<BookModel>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        iView.onFailure(call,e);
                    }

                    @Override
                    public BookModel parse(Response response) throws Exception {
                        return JsonUtil.getInstance().fromJson(response.body().string(), BookModel.class);
                    }

                    @Override
                    public void onSuccess(Call call, BookModel result) {
                        iView.onSuccess(result);
                    }
                });
    }
}
