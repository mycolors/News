package com.fengniao.news.net.api

import com.fengniao.news.bean.ZhiHuResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ZhiHuService {

    @GET("{date}")
    fun getZhiHuArticle(@Path("date") date: String): Observable<ZhiHuResult>
}