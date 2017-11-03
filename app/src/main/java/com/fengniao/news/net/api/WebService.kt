package com.fengniao.news.net.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET


interface WebService {

    //获取web页面的源码
    @GET("")
    fun getWebCode(): Observable<ResponseBody>

}