package com.fengniao.news.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengniao.news.R;
import com.fengniao.news.ui.base.FNAdapter;
import com.fengniao.news.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListTestActivity extends BaseActivity implements FNAdapter.ViewProvider {
    @BindView(R.id.test_list)
    RecyclerView testList;
    private List<String> mList = new ArrayList();
    private FNAdapter adapter = new FNAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);
        loadData();
        testList.setLayoutManager(new LinearLayoutManager(this));
        testList.setAdapter(adapter);
        adapter.setViewProvider(this);
    }

    public void loadData() {
        mList.add("");
        mList.add("");
        mList.add("");
    }

    @Override
    public FNAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(ListTestActivity.this);
        textView.setText("test");
        return new FNAdapter.MyViewHolder(textView);
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
