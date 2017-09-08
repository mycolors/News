package com.fengniao.news.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


object DateUtils {


    /**
     * 日期转换为字符串
     *
     * @param format 日期格式  比如：yyyyMMdd
     * @param date   日期
     * @return
     */
    fun dateToString(format: String, date: Date): String {
        val mFormat = SimpleDateFormat(format)
        return mFormat.format(date)
    }


}
