package com.fengniao.news.net

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

open class FNCallbackHolder(private val mFunction: String, private val mFNCallback: FNCallback) : Callback {
    val mHandler: Handler = Handler(Looper.getMainLooper())

    override fun onFailure(call: Call, e: IOException) {
        mHandler.post { mFNCallback.onReceiveError(mFunction, -1, "请求失败") }
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        val result = response.body()!!.string()
        mHandler.post {
            try {
                val `object` = JSONObject(result)
                mFNCallback.onReceiveData(mFunction, result, "请求成功")
            } catch (e: JSONException) {
                mFNCallback.onReceiveError(mFunction, -2, "解析数据失败")
                e.printStackTrace()
            }
        }
    }

    companion object {
        val TAG = "FNCallbackHolder"
    }
}
