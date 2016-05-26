package lcc.ishuhui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lcc.stateLayout.StateLayout;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.ChapterListAdapter;
import lcc.ishuhui.adapter.LoadMoreAdapter;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.http.callback.StringHttpCallBack;
import lcc.ishuhui.model.ChapterListModel;
import lcc.ishuhui.model.User;
import lcc.ishuhui.utils.JsonUtil;
import lcc.ishuhui.utils.PreferencesUtil;
import okhttp3.Call;
import okhttp3.Response;

public class ChapterListActivity extends BaseActivity implements View.OnClickListener {
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";


    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.iv_cover)
    ImageView iv_cover;


    @Bind(R.id.fab_subscribe)
    FloatingActionButton fab_subscribe;

    ChapterListAdapter adapter;

    private int PageIndex = 0;
    private String id, title;

    private boolean isSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(ID);
        title = getIntent().getStringExtra(TITLE);
        setTitle(title);
        isSubscribed = PreferencesUtil.getInstance().getBoolean("bookId" + id + "isSubscribed", false);
        if (isSubscribed) {
            fab_subscribe.setImageResource(R.mipmap.ic_done);
        }
        init();

        PreferencesUtil.getInstance().start().put("latest_see_id", Integer.parseInt(id)).put("latest_see_title", title).commit();
    }

    private void init() {
        adapter = new ChapterListAdapter(this);
        int spanCount = 2;
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), spanCount);
        gridLayoutManager.setSpanSizeLookup(adapter.spanSizeLookup(spanCount));

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.canLoadMore();
                PageIndex = 0;
                getData();
            }
        });

        adapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                ++PageIndex;
                getData();
            }
        });
        fab_subscribe.setOnClickListener(this);
        getData();
    }

    private void getData() {

        HttpUtil.post()
                .url(API.GET_COMIC_BOOK_DATA)
                .addParam("id", id)
                .addParam("PageIndex", PageIndex + "")
                .build()
                .execute(new HttpCallBack<ChapterListModel>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public ChapterListModel parse(Response response) throws Exception {
                        return JsonUtil.getInstance().fromJson(response.body().string(), ChapterListModel.class);
                    }

                    @Override
                    public void onSuccess(Call call, ChapterListModel result) {
                        if (result.Return.List.isEmpty()) {
                            adapter.noMoreData();
                        } else {
                            if (PageIndex == 0) {
                                Glide.with(ChapterListActivity.this).load(result.Return.ParentItem.FrontCover).centerCrop().into(iv_cover);
                                adapter.setData(result.Return.List);
                            } else {
                                adapter.addData(result.Return.List);
                            }
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chapter_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(title, API.GET_COMIC_BOOK_DATA + "?id=" + id));
                toast(title + " 的章节地址已复制");
                return true;
            case R.id.action_share_link:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, API.GET_COMIC_BOOK_DATA + "?id=" + id);
                i.setType("text/plain");
                startActivity(i);
                return true;
            case R.id.action_subscribe:
                subscribe();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void subscribe() {
        if (!User.getInstance().isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            toast("登陆后才能订阅吆");
            return;
        }
        HttpUtil.post()
                .url(API.SUBSCRIBE)
                .addParam("bookid", id)
                .addParam("isSubscribe", String.valueOf(!isSubscribed))
                .addHeader(User.COOKIE_KEY, User.getInstance().getCookie())
                .build()
                .execute(new StringHttpCallBack() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        toast(e.toString());
                    }

                    @Override
                    public void onSuccess(Call call, String result) {
                        if (!isSubscribed) {
                            toast("订阅成功");
                        } else {
                            toast("已取消订阅");
                        }
                        ChapterListActivity.this.isSubscribed = !ChapterListActivity.this.isSubscribed;
                        if (isSubscribed) {
                            fab_subscribe.setImageResource(R.mipmap.ic_done);
                        } else {
                            fab_subscribe.setImageResource(R.mipmap.ic_add);
                        }
                        PreferencesUtil.getInstance()
                                .start()
                                .put("bookId" + id + "isSubscribed", isSubscribed)
                                .commit();
                    }

                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chapter_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_subscribe:
                subscribe();
                break;
        }
    }
}
