package com.fengniao.news.net


interface FNCallback {

    fun onReceiveData(function: String, data: String, msg: String)

    fun onReceiveError(function: String, errorCode: Int, msg: String)
}
