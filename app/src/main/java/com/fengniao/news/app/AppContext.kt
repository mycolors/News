package com.fengniao.news.app

import android.app.Application
import android.content.Context


class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }

}
