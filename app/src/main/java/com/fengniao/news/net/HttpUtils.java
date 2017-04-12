package com.fengniao.news.net;

import android.text.TextUtils;

import okhttp3.FormBody;

import static com.fengniao.news.net.Api.URL_NEWS_BEFORE_ZHIHU;
import static com.fengniao.news.net.Api.URL_NEWS_TOU_TIAO;

public class HttpUtils {
    private static HttpUtils mHttpUtils;
    private FormBody.Builder mBuilder;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils();
        }
        return mHttpUtils;
    }

    //添加公共参数
    public FormBody.Builder getBaseFormBodyBuilder() {
        if (mBuilder == null)
            mBuilder = new FormBody.Builder();
        return mBuilder;
    }

    public void setToken(String token) {
        if (!TextUtils.isEmpty(token))
            getBaseFormBodyBuilder().add("token", token);
    }


    //获取头条新闻
    public void getTouTiaoNews(FNCallback callback) {
        OkHttpManager.getInstance().getAsyn(URL_NEWS_TOU_TIAO, new FNCallbackHolder(URL_NEWS_TOU_TIAO, callback));
    }

    //获取知乎文章
    public void getZhiHuArticle(String date, FNCallback callback) {
        OkHttpManager.getInstance().getAsyn(URL_NEWS_BEFORE_ZHIHU + date, new FNCallbackHolder(URL_NEWS_BEFORE_ZHIHU, callback));
    }

}
