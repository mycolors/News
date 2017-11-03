package com.fengniao.news.net

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Created by a1 on 2017/4/12.
 */

class OkHttpManager private constructor() {
    private val mOkHttpClient: OkHttpClient = OkHttpClient()


    //异步get
    fun getAsyn(url: String, holder: FNCallbackHolder) {
        val request = Request.Builder().url(url).build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(holder)
    }

    //异步post
    fun postAsyn(url: String, body: RequestBody, holder: FNCallbackHolder) {
        val request = Request.Builder().url(url).post(body).build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(holder)
    }

    companion object {
        private var mInstance: OkHttpManager? = null

        val instance: OkHttpManager
            get() {
                if (mInstance == null) {
                    mInstance = OkHttpManager()
                }
                return mInstance as OkHttpManager
            }
    }
}
