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

    public BaseListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void setRefresh(boolean status) {
        //禁止下拉刷新
        swipeRefreshLayout.setEnabled(status);
    }


    private void initView() {
        mAdapter = new FNAdapter();
        mAdapter.setViewProvider(this);
        mList = new ArrayList<>();
        mLayoutManager = onCreateLayoutManager();
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
