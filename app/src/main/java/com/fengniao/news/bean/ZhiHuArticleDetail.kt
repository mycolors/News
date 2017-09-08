package com.fengniao.news.bean

import com.google.gson.annotations.SerializedName

class ZhiHuArticleDetail {
    var body: String? = null
    var title: String? = null
    var image: String? = null
    var id: Int = 0
    var section: Section? = null
    @SerializedName("css")
    var cssList: List<String>? = null
    var recommenders: List<Recommenders>? = null

    inner class Section {
        var thumbnail: String? = null
        var id: Int = 0
        var name: String? = null
    }

    inner class Recommenders {
        var avatar: String? = null
    }
}
