package com.fengniao.news.net

import com.fengniao.news.app.AppContext
import com.fengniao.news.net.api.NewsService
import com.fengniao.news.net.api.ReadService
import com.fengniao.news.net.api.WebService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class HttpClient private constructor() {

    private var mOkHttpClient: OkHttpClient? = null


    companion object {
        private var INSTANCE: HttpClient? = null

        fun getInstance(): HttpClient? {
            if (INSTANCE == null) {
                synchronized(HttpClient::class) {
                    if (INSTANCE == null)
                        INSTANCE = HttpClient()
                }
            }
            return INSTANCE
        }
    }


    fun getZhiHuService(): NewsService =
            createApi(NewsService::class.java, ApiConstants.URL_NEWS_BEFORE_ZHIHU)

    fun getYueArticle(): ReadService = createApi(ReadService::class.java, ApiConstants.URL_YUE_ARTICLE)


    fun getWebCode(url: String): WebService = createApi(WebService::class.java, url)


    private fun <T> createApi(clazz: Class<T>, baseUrl: String): T {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }


    //初始化Okhttp
    private fun initOkHttpClient() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (mOkHttpClient == null) {
            //设置HTTP缓存
            val cache: Cache = Cache(File(AppContext.appContext.cacheDir, "HttpCache"), 1024 * 1024 * 10)
            mOkHttpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()
        }

    }

    init {
        initOkHttpClient()
    }


}


