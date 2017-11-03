package com.fengniao.news.net

import android.text.TextUtils
import com.fengniao.news.util.LogUtil
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class AllDataCallBackHolder(private val mFunction: String, private val mFNCallback: FNCallback)
    : FNCallbackHolder(mFunction, mFNCallback) {


    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        val result = response.body()!!.string()
        mHandler.post {
            mFNCallback.onReceiveData(mFunction, result, "请求成功")
            LogUtil.i(TAG, result)
            if (TextUtils.isEmpty(result))
                mFNCallback.onReceiveError(mFunction, -1, "请求失败")
        }
    }

}
