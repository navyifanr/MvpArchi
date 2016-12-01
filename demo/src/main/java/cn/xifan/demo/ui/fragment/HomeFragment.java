package cn.xifan.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.mvparchi.core.BaseFragment;
import cn.cfanr.mvparchi.widget.ptr.PtrLoadMoreListener;
import cn.cfanr.mvparchi.widget.ptr.PtrRecyclerView;
import cn.xifan.demo.R;
import cn.xifan.demo.adapter.HomeAdapter;
import cn.xifan.demo.model.News;
import cn.xifan.demo.presenter.HomePresenter;
import cn.xifan.demo.ui.view.HomeView;

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter mPresenter;

    private PtrRecyclerView mRecyclerView;

    private List<News> newsList = new ArrayList<>();
    private HomeAdapter mAdapter;

    private int mDayNum = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HomePresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    public void initViews(View layoutView) {
        mRecyclerView = $(layoutView, R.id.ptr_recycler_view_home);
        mAdapter = new HomeAdapter(getActivity(), newsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.autoRefresh();
        mRecyclerView.setPtrLoadMoreListener(new PtrLoadMoreListener() {
            @Override
            public void onRefresh() {
//                getActivity().setTitle("首页");
                mDayNum = 0;
                mPresenter.loadHomeData(mDayNum);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mDayNum ++;
                mPresenter.loadHomeData(mDayNum);
                mRecyclerView.refreshComplete();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    changeTitle(firstVisibleItemPosition);
                }
            }
        });
    }

    private void changeTitle(int firstVisibleItemPosition) {
        //TODO
    }

    @Override
    public void setNewsList(String title, List<News> newsList) {
//        getActivity().setTitle(title);
        if (mDayNum == 0) {
            this.newsList.clear();
        }
        this.newsList.addAll(newsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void showProgress() {
        mProgressDialog.showProgress();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hideProgress();
    }
}
