package com.fengniao.news.net;

/**
 * Created by a1 on 2017/3/28.
 */

public interface FNCallback {

    void onReceiveData(String function, String data, String msg);

    void onReceiveError(String function, int errorCode, String msg);
}
