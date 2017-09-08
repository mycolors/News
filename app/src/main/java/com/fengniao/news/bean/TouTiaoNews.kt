package com.fengniao.news.bean

import com.google.gson.annotations.SerializedName

class TouTiaoNews {
    var title: String? = null
    var date: String? = null
    @SerializedName("author_name")
    var author: String? = null
    var url: String? = null
    @SerializedName("thumbnail_pic_s")
    var picOne: String? = null
    @SerializedName("thumbnail_pic_s02")
    var picTwo: String? = null
    @SerializedName("thumbnail_pic_s03")
    var picThree: String? = null
}
