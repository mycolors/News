package com.fengniao.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fengniao.news.R;
import com.fengniao.news.bean.ZhiHuArticleDetail;
import com.fengniao.news.ui.base.BaseActivity;
import com.fengniao.news.util.JsonUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fengniao.news.app.Constant.UNKNOWN_ERROR;
import static com.fengniao.news.net.Api.URL_NEWS_DETAIL;

public class DetailsActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.img)
    ImageView img;
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    private int ariticleId;
    private ZhiHuArticleDetail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initWebView();
        initView();
    }

    private void initView() {
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetail();
            }
        });
        ariticleId = getIntent().getIntExtra("id", -1);
        if (ariticleId < 0) return;
        getDetail();
    }

    @OnClick(R.id.toolbar)
    public void toolBar(View view) {
        scrollView.smoothScrollTo(0, 0);
    }


    private void getDetail() {
        OkHttpClient client = new OkHttpClient();
        Request.Builder mBuilder = new Request.Builder().url(URL_NEWS_DETAIL + ariticleId);
        mBuilder.method("GET", null);
        final Request request = mBuilder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
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
            public void onResponse(Call call, final Response response) throws IOException {
                detail = JsonUtils.jsonToBean(response.body().string(), ZhiHuArticleDetail.class);
                final String html = getHtml(detail.body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //加载本地html源码
                        String encoding = "UTF-8";
                        String mimeType = "text/html";
                        webView.loadDataWithBaseURL("x-data://base", html, mimeType, encoding, null);
                        Glide.with(DetailsActivity.this).load(detail.image).into(img);
                        setCollapsingToolbarLayoutTitle(detail.title);
                        if (swipeRefresh.isRefreshing())
                            swipeRefresh.setRefreshing(false);
                    }
                });

            }
        });
    }

    // to change the title's font size of toolbar layout
    private void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }


    public String getHtml(String body) {
        body = body.replace("<div class=\"img-place-holder\">", "");
        body = body.replace("<div class=\"headline\">", "");

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_news.css\" type=\"text/css\">";
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>\n");
        builder.append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        builder.append("<head>\n");
        builder.append("\t<meta charset=\"utf-8\" />");
        builder.append(css);
        builder.append("\n</head>\n");
        builder.append("<body>\n");
        builder.append(body);
        builder.append("</body></html>");
        return builder.toString();
    }

    private void getCss(String cssUrl) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder mBuilder = new Request.Builder().url(cssUrl);
        mBuilder.method("GET", null);
        Request request = mBuilder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    private void initWebView() {
        //能够和js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);

        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(DetailsActivity.this, InnerBrowserActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("tag", "exit");
        System.exit(0);
    }
}
