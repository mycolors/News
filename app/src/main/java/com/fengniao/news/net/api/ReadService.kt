package com.fengniao.news.net.api


import com.fengniao.news.bean.YueArticle
import io.reactivex.Observable
import retrofit2.http.GET

interface ReadService {

    @get:GET("article/random?device_id=03918819782bf9aa71986642ba9e81c0")
    val yueArticle: Observable<YueArticle>



}
