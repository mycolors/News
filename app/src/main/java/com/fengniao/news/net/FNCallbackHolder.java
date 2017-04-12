package com.fengniao.news.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FNCallbackHolder implements Callback {
    public static final String TAG = "FNCallbackHolder";
    private String mFunction;
    private FNCallback mFNCallback;
    private Handler mHandler;

    public FNCallbackHolder(String function, FNCallback callback) {
        mFunction = function;
        mFNCallback = callback;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFNCallback.onReceiveError(mFunction, -1, "请求失败");
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject object = new JSONObject(result);
                    mFNCallback.onReceiveData(mFunction, result, "请求成功");
                } catch (JSONException e) {
                    mFNCallback.onReceiveError(mFunction, -2, "解析数据失败");
                    e.printStackTrace();
                }
            }
        });
    }
}
