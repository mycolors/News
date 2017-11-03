package com.fengniao.news.widget.konfetti

import android.content.res.Resources

data class Size(val size: Int, val mass: Float = 5f)

val Size.sizeDp: Int
    get() = (this.size * Resources.getSystem().displayMetrics.density).toInt()

