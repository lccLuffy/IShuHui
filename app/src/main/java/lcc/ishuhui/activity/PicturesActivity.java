package lcc.ishuhui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lcc.state_refresh_recyclerview.Recycler.LoadMoreFooter;
import com.lcc.state_refresh_recyclerview.Recycler.StateRecyclerView;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.PictureAdapter;
import lcc.ishuhui.constants.Gank;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.model.PictureModel;
import lcc.ishuhui.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.Response;

public class PicturesActivity extends BaseActivity {

    @Bind(R.id.stateRecyclerView)
    StateRecyclerView stateRecyclerView;

    PictureAdapter pictureAdapter;

    LoadMoreFooter loadMoreFooter;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getData();
    }

    private void getData() {
        HttpUtil.get()
                .url(Gank.PICTURES+page)
                .build()
                .execute(new HttpCallBack<PictureModel>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        loadMoreFooter.showErrorView();
                    }

                    @Override
                    public PictureModel parse(Response response) throws Exception {
                        return JsonUtil.getInstance().fromJson(response.body().string(),PictureModel.class);
                    }

                    @Override
                    public void onSuccess(Call call, PictureModel result) {
                        if(!result.results.isEmpty())
                        {
                            if(page == 1)
                            {
                                pictureAdapter.initData(result.results);
                            }
                            else
                            {
                                pictureAdapter.addData(result.results);
                            }
                        }
                        else
                        {
                            loadMoreFooter.showNoMoreView();
                            if(pictureAdapter.isDataEmpty())
                            {
                                stateRecyclerView.showEmptyView();
                            }
                        }
                        stateRecyclerView.setRefreshing(false);
                    }
                });
    }

    private void init() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        stateRecyclerView.setLayoutManager(layoutManager);

        stateRecyclerView.setAdapter(pictureAdapter = new PictureAdapter(this),true);
        loadMoreFooter = pictureAdapter.getLoadMoreFooter();
        stateRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadMoreFooter.showLoadMoreView();
                getData();
            }
        });

        loadMoreFooter.setOnLoadMoreListener(new LoadMoreFooter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getData();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pictures;
    }
}
