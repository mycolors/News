package com.fengniao.news.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {


    /**
     * 日期转换为字符串
     *
     * @param format 日期格式  比如：yyyyMMdd
     * @param date   日期
     * @return
     */
    public static String dateToString(String format, Date date) {
        SimpleDateFormat mFormat = new SimpleDateFormat(format);
        return mFormat.format(date);
    }


}
