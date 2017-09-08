package com.fengniao.news.util


import android.text.TextUtils

import com.google.gson.Gson

import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList

object JsonUtils {

    fun <T> jsonToList(data: String, tClass: Class<T>): List<T> {
        val mList = ArrayList<T>()
        if (TextUtils.isEmpty(data)) return mList
        try {
            val mArray = JSONArray(data)
            for (i in 0..mArray.length() - 1) {
                val t = jsonToBean(mArray.get(i).toString(), tClass)
                mList.add(t)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return mList
    }

    fun <T> jsonToBean(data: String, tClass: Class<T>): T {
        return Gson().fromJson(data, tClass)
    }

}
