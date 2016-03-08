package lcc.ishuhui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lcc.state_refresh_recyclerview.Recycler.LoadMoreFooter;
import com.lcc.state_refresh_recyclerview.Recycler.StateRecyclerView;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.BookAdapter;
import lcc.ishuhui.fragment.IView.IView;
import lcc.ishuhui.fragment.presenter.CategoryPresenter;
import lcc.ishuhui.model.BookModel;
import okhttp3.Call;

public class CategoryFragment extends BaseFragment implements IView<BookModel>{
    private static final String CLASSIFY_ID = "CLASSIFY_ID";

    //ClassifyId   分类标识，0热血，1国产，2同人，3鼠绘
    public static final String CLASSIFY_ID_HOT = "0";
    public static final String CLASSIFY_ID_CHINA = "1";
    public static final String CLASSIFY_ID_SAME = "2";
    public static final String CLASSIFY_ID_MOUSE = "3";
    private String classifyId;

    @Bind(R.id.stateRecyclerView)
    StateRecyclerView niceRecyclerView;

    CategoryPresenter presenter;

    LoadMoreFooter loadMoreFooter;

    BookAdapter bookAdapter;

    private int PageIndex = 0,prevPage;

    public static CategoryFragment newInstance(String classifyId) {
        CategoryFragment fragment = new CategoryFragment();
        String title = "热血";
        Bundle args = new Bundle();
        switch (classifyId)
        {
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
        niceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        niceRecyclerView.setAdapter(bookAdapter = new BookAdapter(getContext()), true);
        loadMoreFooter = bookAdapter.getLoadMoreFooter();
        niceRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreFooter.showLoadMoreView();
                prevPage = PageIndex;
                PageIndex = 0;
                if(bookAdapter.isDataEmpty())
                {
                    niceRecyclerView.showProgressView();
                }
                getData();
            }
        });

        loadMoreFooter.setOnLoadMoreListener(new LoadMoreFooter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                prevPage = PageIndex;
                ++PageIndex;
                getData();
            }
        });
        loadMoreFooter.setOnErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreFooter.showLoadMoreView();
                PageIndex = prevPage;
                getData();
            }
        });

        getData();
    }


    private void getData() {
        presenter.getData(classifyId,PageIndex);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categoty;
    }

    @Override
    public void onSuccess(BookModel bookModel) {
        niceRecyclerView.showRecyclerView();
        if (bookModel.Return.List.isEmpty()) {
            loadMoreFooter.showNoMoreView();
        }
        else
        {
            if (PageIndex == 0)
            {
                bookAdapter.initData(bookModel.Return.List);
            } else {
                bookAdapter.addData(bookModel.Return.List);
            }
        }
        niceRecyclerView.setRefreshing(false);
    }

    @Override
    public void onFailure(Call call, Exception e) {
        if (bookAdapter.isDataEmpty()) {
            niceRecyclerView.showErrorView();
        } else {
            loadMoreFooter.showErrorView();
        }
    }
}
