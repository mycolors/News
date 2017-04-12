package com.fengniao.news.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fengniao.news.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements FNAdapter.ViewProvider {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    protected List<T> mList;
    FNAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int mPage, mCount = 10;
    private boolean isViewCreated = false;
    private boolean isFirst = true;

    public BaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!enableLazyLoad()) return;
        if (isViewCreated) lazyLoad();
    }

    public void lazyLoad() {
        //只有第一次进入才进行懒加载
        if (isFirst) {
            loadData(true);
            isFirst = false;
        }
    }

    //是否开启懒加载
    public abstract boolean enableLazyLoad();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        initView();
    }

    //刷新控件开关
    public void enableRefresh(boolean status) {
        //禁止下拉刷新
        swipeRefreshLayout.setEnabled(status);
    }

    //是否显示refreshBar
    public void showRefreshBar(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }

    public void setListAdapter() {
        if (getActivity() == null) return;

        if (mAdapter == null) {
            initAdapter();
            mRecyclerView.setAdapter(mAdapter);
        } else
            notifyDataSetChanged();

        if (swipeRefreshLayout.isRefreshing())
            showRefreshBar(false);

        mAdapter.setLoadMoreStatus(false);
    }

    public abstract void loadData(boolean isClear);

    @Override
    public void loadMore() {
        mPage++;
        loadData(false);
    }

    private void initView() {
        mList = new ArrayList<>();
        showRefreshBar(true);
        initAdapter();
        mLayoutManager = onCreateLayoutManager();
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListFragment.this.onRefresh();
            }
        });
        if (!enableLazyLoad()) loadData(false);
    }

    public void onRefresh() {
        mPage = 0;
        loadData(true);
    }

    public void initAdapter() {
        mAdapter = new FNAdapter<T>(getContext(), mList);
        mAdapter.setViewProvider(this);
    }

    public void enableLoadMore(boolean isLoadMore) {
        mAdapter.enableLoadMore(isLoadMore);
    }

    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return null;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mLayoutManager = manager;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
