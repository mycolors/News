package com.fengniao.news.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fengniao.news.R;
import com.fengniao.news.bean.TouTiaoNews;
import com.fengniao.news.net.Api;
import com.fengniao.news.ui.activity.InnerBrowserActivity;
import com.fengniao.news.ui.base.FNAdapter;
import com.fengniao.news.ui.base.BaseListFragment;
import com.fengniao.news.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fengniao.news.app.Constant.UNKNOWN_ERROR;

/**
 * A simple {@link Fragment} subclass.
 */
public class TouTiaoNewsFragment extends BaseListFragment<TouTiaoNews> {
    private boolean isViewCreated = false;
    private boolean isFirst = true;
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
        isViewCreated = true;
    }

    public void initList() {
        setLayoutManager(new GridLayoutManager(getContext(), 3));
        enableRefresh(false);
        enableLoadMore(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) lazyLoad();
    }

    public void lazyLoad() {
        if (isFirst) {
            loadData();
            isFirst = false;
        }
    }

    @Override
    public void loadData() {
        getNews();
    }

    public void getNews() {
        OkHttpClient client = new OkHttpClient();
        Request.Builder mBuilder = new Request.Builder().url(Api.URL_NEWS_TOU_TIAO);
        mBuilder.method("GET", null);
        final Request request = mBuilder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    JSONArray dataArray = responseJson.getJSONObject("result")
                            .getJSONArray("data");
                    List<TouTiaoNews> list = JsonUtils.jsonToList(dataArray.toString(), TouTiaoNews.class);
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
        });
    }

    @Override
    public View getView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list_news, parent, false);
        return view;
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
