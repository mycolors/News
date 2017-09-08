package com.fengniao.news.bean


class ZhiHuResult(var date: String, var stories: List<Story>) {

    data class Story(var images: List<String>, var type: Int, var id: Int,
                     var ga_prefix: String, var title: String);
}