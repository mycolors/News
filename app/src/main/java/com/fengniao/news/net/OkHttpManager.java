package com.fengniao.news.net;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by a1 on 2017/4/12.
 */

public class OkHttpManager {
    private static OkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;


    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpManager();
        }
        return mInstance;
    }


    //异步get
    public void getAsyn(String url, FNCallbackHolder holder) {
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(holder);
    }

    //异步post
    public void postAsyn(String url, RequestBody body, FNCallbackHolder holder) {
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(holder);
    }
}
