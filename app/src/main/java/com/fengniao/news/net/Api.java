package com.fengniao.news.net;

import java.sql.Time;

/**
 * Created by a1 on 2017/3/29.
 */

public class Api {
    //获取最新消息
    public static final String URL_GET_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    //消息内容获取与离线下载
    public static final String URL_NEWS_DETAIL = "http://news-at.zhihu.com/api/4/news/";
    //获取之前的文章
    public static final String URL_NEWS_BEFORE_ZHIHU = "http://news.at.zhihu.com/api/4/news/before/";
}

