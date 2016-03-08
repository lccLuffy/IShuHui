package lcc.ishuhui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lcc.state_refresh_recyclerview.Recycler.StateRecyclerView;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.LoginActivity;
import lcc.ishuhui.adapter.SubscribeAdapter;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.model.BookModel;
import lcc.ishuhui.model.User;
import lcc.ishuhui.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.Response;

public class SubscribeFragment extends BaseFragment {

    @Bind(R.id.stateRecyclerView)
    StateRecyclerView stateRecyclerView;

    SubscribeAdapter subscribeAdapter;

    public static SubscribeFragment newInstance() {
        return new SubscribeFragment();
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        init();
    }

    @Override
    public String toString() {
        return "订阅";
    }

    private void init() {
        stateRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        stateRecyclerView.setAdapter(subscribeAdapter = new SubscribeAdapter(getContext()), true);
        stateRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(subscribeAdapter.isDataEmpty())
                {
                    stateRecyclerView.showProgressView();
                }
                getData();
            }
        });

        subscribeAdapter.getLoadMoreFooter().showNoMoreView();
    }

    @Override
    public void onCreateView() {
        getData();
    }

    private void getData() {
        if (User.getInstance().isLogin())
        {
            HttpUtil.post()
                    .addHeader(User.COOKIE_KEY,User.getInstance().getCookie())
                    .url(API.GET_SUBSCRIBE_BOOK)
                    .build()
                    .execute(new HttpCallBack<BookModel>() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            if (subscribeAdapter.isDataEmpty())
                            {
                                stateRecyclerView.showErrorView();
                                toast(e.toString());
                            } else
                            {
                                subscribeAdapter.getLoadMoreFooter().showErrorView();
                            }
                        }

                        @Override
                        public BookModel parse(Response response) throws Exception {
                            return JsonUtil.getInstance().fromJson(response.body().string(), BookModel.class);
                        }

                        @Override
                        public void onSuccess(Call call, BookModel result) {
                            stateRecyclerView.showRecyclerView();
                            if (result.Return.List.isEmpty())
                            {
                                stateRecyclerView.showEmptyView("您啥也没订阅");
                            }
                            else
                            {
                                subscribeAdapter.initData(result.Return.List);
                            }
                            stateRecyclerView.setRefreshing(false);
                        }
                    });
        }
        else
        {

            stateRecyclerView.showEmptyView("您还没有登录");
            stateRecyclerView.setEmptyAction("登录", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast("您还没有登录");
                    getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categoty;
    }
}
