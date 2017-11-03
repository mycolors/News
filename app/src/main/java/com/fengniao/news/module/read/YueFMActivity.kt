package com.fengniao.news.module.read

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.fengniao.news.R
import com.fengniao.news.bean.YueArticle
import com.fengniao.news.net.FNCallback
import com.fengniao.news.net.HttpClient
import com.fengniao.news.net.HttpUtils
import com.fengniao.news.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_yue_fm.*

class YueFMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yue_fm)
        initView()
        getYueArticle()
    }

    private fun initView() {
        //能够和js交互
        web_view.settings.javaScriptEnabled = true
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        web_view.settings.builtInZoomControls = false
        //缓存
        web_view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //开启DOM storage API功能
        web_view.settings.domStorageEnabled = true
        //开启application Cache功能
        web_view.settings.setAppCacheEnabled(false)

        web_view.setWebViewClient(WebViewClient())
    }


    private fun getYueArticle() {
        HttpClient.getInstance()!!
                .getYueArticle()
                .yueArticle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: YueArticle? ->
                    if (t != null) {
//                        web_view.loadData(t.data.content, "text/html; charset=UTF-8", null)
//                        web_view.loadUrl()
                        getWebCode(t.data.url)
                    }
                }
    }

    //获取web页面源码
    private fun getWebCode(url: String) {
        HttpUtils.instance.getWebCode(url, object : FNCallback {
            override fun onReceiveData(function: String, data: String, msg: String) {
                web_view.loadData(Utils.formatYueArticlePageCode(data)
                        , "text/html; charset=UTF-8", null)
            }

            override fun onReceiveError(function: String, errorCode: Int, msg: String) {
            }
        })
    }
}
