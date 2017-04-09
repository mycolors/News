package com.fengniao.news.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengniao.news.R;
import com.fengniao.news.ui.base.BaseListActivity;
import com.fengniao.news.ui.base.FNAdapter;
import com.fengniao.news.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListTestActivity extends BaseListActivity {


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_list_test);
    }


    @Override
    public void loadData() {
        enableRefresh(false);
        enableLoadMore(false);
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");

        mList.add("");
        setListAdapter();
    }


    @Override
    public View getView(ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(this).inflate(R.layout.item_list_zhihu,parent,false);
        return view;
    }

    @Override
    public void bindDataToView(FNAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onItemClick(FNAdapter.MyViewHolder holder, int position) {

    }

}
