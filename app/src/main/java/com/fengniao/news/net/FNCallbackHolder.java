package com.fengniao.news.net;

import android.content.Context;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FNCallbackHolder implements Callback {
    public static final String TAG = "FNCallbackHolder";
    private String mFunction;
    private FNCallback mFNCallback;

    public FNCallbackHolder(String function, FNCallback callback) {
        mFunction = function;
        mFNCallback = callback;

    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
