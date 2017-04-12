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
    //头条新闻
    public static final String URL_NEWS_TOU_TIAO = "http://jiudian.365os.com/index.php?c=NewApi&m=Headlines";

    public static final String URL_CHANGE_NAME = "http://jiudian.365os.com/index.php?c=NewApi&m=ChangeName";
}

