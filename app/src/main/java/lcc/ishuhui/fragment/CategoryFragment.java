package lcc.ishuhui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcc.stateLayout.StateLayout;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.BookAdapter;
import lcc.ishuhui.adapter.LoadMoreAdapter;
import lcc.ishuhui.fragment.IView.IView;
import lcc.ishuhui.fragment.presenter.CategoryPresenter;
import lcc.ishuhui.model.BookModel;
import okhttp3.Call;

public class CategoryFragment extends BaseFragment implements IView<BookModel> {
    private static final String CLASSIFY_ID = "CLASSIFY_ID";

    //ClassifyId   分类标识，0热血，1国产，2同人，3鼠绘
    public static final String CLASSIFY_ID_HOT = "0";
    public static final String CLASSIFY_ID_CHINA = "1";
    public static final String CLASSIFY_ID_SAME = "2";
    public static final String CLASSIFY_ID_MOUSE = "3";
    private String classifyId;

    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    CategoryPresenter presenter;


    BookAdapter adapter;

    private int PageIndex = 0, prevPage;

    public static CategoryFragment newInstance(String classifyId) {
        CategoryFragment fragment = new CategoryFragment();
        String title = "热血";
        Bundle args = new Bundle();
        switch (classifyId) {
            case CLASSIFY_ID_HOT:
                title = "热血";
                break;
            case CLASSIFY_ID_CHINA:
                title = "国产";
                break;
            case CLASSIFY_ID_SAME:
                title = "同人";
                break;
            case CLASSIFY_ID_MOUSE:
                title = "鼠绘";
                break;
        }
        args.putString(CLASSIFY_ID, classifyId);
        fragment.setArguments(args);
        fragment.title = title;
        return fragment;
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        if (getArguments() != null) {
            classifyId = getArguments().getString(CLASSIFY_ID);
        }
        init();
    }

    private String title;

    @Override
    public String toString() {
        return title;
    }

    private void init() {
        presenter = new CategoryPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new BookAdapter(getContext()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.noMoreData();
                prevPage = PageIndex;
                PageIndex = 0;
                if (adapter.isDataEmpty()) {
                    stateLayout.showProgressView();
                }
                getData();
            }
        });

        adapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                prevPage = PageIndex;
                ++PageIndex;
                getData();
            }
        });
        getData();
    }


    private void getData() {
        if (adapter.isDataEmpty())
            stateLayout.showProgressView();
        presenter.getData(classifyId, PageIndex);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categoty;
    }

    @Override
    public void onSuccess(BookModel bookModel) {
        stateLayout.showContentView();
        if (bookModel.Return.List.isEmpty()) {
            adapter.noMoreData();
        } else {
            if (PageIndex == 0) {
                adapter.setData(bookModel.Return.List);
            } else {
                adapter.addData(bookModel.Return.List);
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call call, Exception e) {
        if (adapter.isDataEmpty()) {
            stateLayout.showErrorView();
        } else {
            adapter.noMoreData("加载出错");
        }
    }
}
