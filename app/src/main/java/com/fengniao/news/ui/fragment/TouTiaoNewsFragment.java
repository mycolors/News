package com.fengniao.news.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fengniao.news.R;
import com.fengniao.news.bean.TouTiaoNews;
import com.fengniao.news.net.Api;
import com.fengniao.news.net.FNCallback;
import com.fengniao.news.net.HttpUtils;
import com.fengniao.news.ui.activity.InnerBrowserActivity;
import com.fengniao.news.ui.base.FNAdapter;
import com.fengniao.news.ui.base.BaseListFragment;
import com.fengniao.news.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

import static com.fengniao.news.app.Constant.UNKNOWN_ERROR;

/**
 * A simple {@link Fragment} subclass.
 */
public class TouTiaoNewsFragment extends BaseListFragment<TouTiaoNews> {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    public TouTiaoNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_tou_tiao_news;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }

    @Override
    public void loadData(boolean isClear) {
        getNews(isClear);
    }


    public void initList() {
        enableRefresh(false);
        enableLoadMore(true);
    }

    @Override
    public boolean enableLazyLoad() {
        return true;
    }


    public void getNews(final boolean isClear) {
        HttpUtils.getInstance().getTouTiaoNews(new FNCallback() {
            @Override
            public void onReceiveData(String function, String data, String msg) {
                try {
                    JSONObject responseJson = new JSONObject(data);
                    JSONArray dataArray = responseJson.getJSONObject("result")
                            .getJSONArray("data");
                    List<TouTiaoNews> list = JsonUtils.jsonToList(dataArray.toString(), TouTiaoNews.class);
                    if (isClear) mList.clear();
                    mList.addAll(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setListAdapter();
                    }
                });
            }

            @Override
            public void onReceiveError(String function, int errorCode, String msg) {
                Toast.makeText(getContext(), UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View getView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(getContext()).inflate(R.layout.item_list_news, parent, false);
    }

    @Override
    public void bindDataToView(FNAdapter.MyViewHolder holder, int position) {
        holder.setText(R.id.text_title, mList.get(position).title);
        holder.setText(R.id.text_date, mList.get(position).date);
        holder.setText(R.id.text_from, mList.get(position).author);

        if (!TextUtils.isEmpty(mList.get(position).picOne))
            Glide.with(getContext()).load(mList.get(position).picOne).into((ImageView) holder.getView(R.id.img_one));
        if (!TextUtils.isEmpty(mList.get(position).picTwo))
            Glide.with(getContext()).load(mList.get(position).picTwo).into((ImageView) holder.getView(R.id.img_two));
        if (!TextUtils.isEmpty(mList.get(position).picThree))
            Glide.with(getContext()).load(mList.get(position).picThree).into((ImageView) holder.getView(R.id.img_three));
    }

    @Override
    public void onItemClick(FNAdapter.MyViewHolder holder, int position) {
        Intent intent = new Intent(getActivity(), InnerBrowserActivity.class);
        intent.putExtra("url", mList.get(position).url);
        startActivity(intent);
    }

}
