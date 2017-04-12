package com.fengniao.news.net;


public interface FNCallback {

    void onReceiveData(String function, String data, String msg);

    void onReceiveError(String function, int errorCode, String msg);
}
