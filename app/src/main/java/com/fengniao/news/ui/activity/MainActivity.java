package com.fengniao.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fengniao.news.R;
import com.fengniao.news.bean.News;
import com.fengniao.news.ui.adapter.NewsListAdapter;
import com.fengniao.news.ui.base.BaseActivity;
import com.fengniao.news.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fengniao.news.app.Constant.UNKNOWN_ERROR;

public class MainActivity extends BaseActivity {
    @BindView(R.id.news_list)
    RecyclerView newsList;
    private NewsListAdapter mAdapter;
    private List<News> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new NewsListAdapter(this, mList);
        newsList.setLayoutManager(new LinearLayoutManager(this));
        newsList.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", mList.get(position).id);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        getNews();
    }


    private void getNews() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url("http://news-at.zhihu.com/api/4/news/latest");
        builder.method("GET", null);
        final Request request = builder.build();
        Call mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(UNKNOWN_ERROR);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String date = jsonObject.getString("date");
                    JSONArray result = jsonObject.getJSONArray("stories");
                    List<News> list = JsonUtils.jsonToList(result.toString(), News.class);
                    mList.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
