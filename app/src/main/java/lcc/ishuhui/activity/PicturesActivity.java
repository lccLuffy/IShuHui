package lcc.ishuhui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lcc.stateLayout.StateLayout;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.LoadMoreAdapter;
import lcc.ishuhui.adapter.PictureAdapter;
import lcc.ishuhui.constants.Gank;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.model.PictureModel;
import lcc.ishuhui.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.Response;

public class PicturesActivity extends BaseActivity {

    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    PictureAdapter adapter;

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
                        adapter.noMoreData(e.toString());
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
                                adapter.setData(result.results);
                            }
                            else
                            {
                                adapter.addData(result.results);
                            }
                        }
                        else
                        {
                            adapter.noMoreData();
                            if(adapter.isDataEmpty())
                            {
                                stateLayout.showEmptyView();
                            }
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void init() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter = new PictureAdapter(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter.canLoadMore();
                getData();
            }
        });

        adapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
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
