package com.fengniao.news.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fengniao.news.R;
import com.fengniao.news.bean.ZhiHuArticle;
import com.fengniao.news.net.FNCallback;
import com.fengniao.news.net.HttpUtils;
import com.fengniao.news.ui.activity.DetailsActivity;
import com.fengniao.news.ui.adapter.ZhiHuArticleListAdapter;
import com.fengniao.news.util.DateUtils;
import com.fengniao.news.util.JsonUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fengniao.news.app.Constant.UNKNOWN_ERROR;
import static com.fengniao.news.net.Api.URL_NEWS_BEFORE_ZHIHU;

//知乎文章
public class ZhiHuArticleFragment extends Fragment {
    @BindView(R.id.news_list)
    RecyclerView newsList;
    @BindView(R.id.fab_calendar)
    FloatingActionButton fabCalendar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private ZhiHuArticleListAdapter mAdapter;
    private List<ZhiHuArticle> mList;
    private int mYear, mMonth, mDay;


    public ZhiHuArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zhi_hu_article, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadData();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new ZhiHuArticleListAdapter(getContext(), mList);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new ZhiHuArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("id", mList.get(position).id);
                startActivity(intent);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        newsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //隐藏或显示fab按钮
                if (dy > 0) {
                    fabCalendar.hide();
                } else {
                    fabCalendar.show();
                }


                //判断是否滑动底部，返回值为false，则表示不能往上滚动
                //传入值为-1，则判断是否到顶部
                if (!newsList.canScrollVertically(1))
                    loadMore();
            }


        });
    }

    @OnClick(R.id.fab_calendar)
    public void chooseDate(View view) {
        showPickDialog();
    }

    public void showPickDialog() {
        Calendar now = Calendar.getInstance();
        now.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year, monthOfYear, dayOfMonth);
                getArticle(temp.getTime(), true);

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        //知乎日报首次上线实在2013.5.20   在Calendar中设置月份要比实际月份小1，同时从Calender中拿到月份也比实际月份小1
        minDate.set(2013, 4, 20);
        dialog.setMinDate(minDate);
        dialog.vibrate(false);
        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    public void initDate(Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void loadData() {
        Calendar calendar = Calendar.getInstance();
        initDate(calendar);
        getArticle(calendar.getTime(), true);
    }


    public void loadMore() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(mYear, mMonth, mDay);
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        initDate(yesterday);
        getArticle(yesterday.getTime(), false);
    }

    public void getArticle(Date date, final boolean isClean) {
        String time = DateUtils.dateToString("yyyyMMdd", date);
        HttpUtils.getInstance().getZhiHuArticle(time, new FNCallback() {
            @Override
            public void onReceiveData(String function, String data, String msg) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("date").equals("20130519")) {
                        mAdapter.setIsEnd(true);
                    } else {
                        mAdapter.setIsEnd(false);
                    }
                    JSONArray result = jsonObject.getJSONArray("stories");
                    List<ZhiHuArticle> list = JsonUtils.jsonToList(result.toString(), ZhiHuArticle.class);
                    if (isClean)
                        mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    if (swipeRefresh.isRefreshing())
                        swipeRefresh.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveError(String function, int errorCode, String msg) {
                Toast.makeText(getContext(), UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
