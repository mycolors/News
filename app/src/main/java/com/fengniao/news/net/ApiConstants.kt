package com.fengniao.news.net


object ApiConstants {
    //获取最新消息
    val URL_GET_NEWS = "http://news-at.zhihu.com/api/4/news/latest"
    //消息内容获取与离线下载
    val URL_NEWS_DETAIL = "http://news-at.zhihu.com/api/4/news/"
    //获取之前的文章
    val URL_NEWS_BEFORE_ZHIHU = "http://news.at.zhihu.com/api/4/news/before/"
    //头条新闻
    val URL_NEWS_TOU_TIAO = "http://jiudian.365os.com/index.php?c=NewApi&m=Headlines"

    val URL_CHANGE_NAME = "http://jiudian.365os.com/index.php?c=NewApi&m=ChangeName"

    val URL_YUE_ARTICLE = "http://srv.yue.fm/"
}

