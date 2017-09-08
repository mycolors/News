package com.fengniao.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.fengniao.news.R
import com.fengniao.news.bean.ZhiHuArticleDetail
import com.fengniao.news.base.BaseActivity
import com.fengniao.news.util.JsonUtils

import java.io.IOException

import butterknife.BindView
import butterknife.OnClick
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import com.fengniao.news.app.Constant.UNKNOWN_ERROR
import com.fengniao.news.net.ApiConstants.URL_NEWS_DETAIL

class DetailsActivity : BaseActivity() {
    @BindView(R.id.web_view)
    internal var webView: WebView? = null
    @BindView(R.id.swipe_refresh)
    internal var swipeRefresh: SwipeRefreshLayout? = null
    @BindView(R.id.scroll_view)
    internal var scrollView: NestedScrollView? = null
    @BindView(R.id.toolbar_layout)
    internal var toolbarLayout: CollapsingToolbarLayout? = null
    @BindView(R.id.img)
    internal var img: ImageView? = null
    //    @BindView(R.id.toolbar)
    //    Toolbar toolbar;
    private var ariticleId: Int = 0
    private var detail: ZhiHuArticleDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initWebView()
        initView()
    }

    private fun initView() {
        swipeRefresh!!.isRefreshing = true
        swipeRefresh!!.setOnRefreshListener { getDetail() }
        ariticleId = intent.getIntExtra("id", -1)
        if (ariticleId < 0) return
        getDetail()
    }

    @OnClick(R.id.toolbar)
    fun toolBar(view: View) {
        scrollView!!.smoothScrollTo(0, 0)
    }


    private fun getDetail() {
        val client = OkHttpClient()
        val mBuilder = Request.Builder().url(URL_NEWS_DETAIL + ariticleId)
        mBuilder.method("GET", null)
        val request = mBuilder.build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { showToast(UNKNOWN_ERROR) }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                detail = JsonUtils.jsonToBean(response.body()!!.string(), ZhiHuArticleDetail::class.java)
                val html = getHtml(detail!!.body)
                runOnUiThread {
                    //加载本地html源码
                    val encoding = "UTF-8"
                    val mimeType = "text/html"
                    webView!!.loadDataWithBaseURL("x-data://base", html, mimeType, encoding, null)
                    Glide.with(this@DetailsActivity).load(detail!!.image).into(img!!)
                    setCollapsingToolbarLayoutTitle(detail!!.title)
                    if (swipeRefresh!!.isRefreshing)
                        swipeRefresh!!.isRefreshing = false
                }

            }
        })
    }

    // to change the title's font size of toolbar layout
    private fun setCollapsingToolbarLayoutTitle(title: String?) {
        toolbarLayout!!.title = title
        toolbarLayout!!.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        toolbarLayout!!.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        toolbarLayout!!.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1)
        toolbarLayout!!.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1)
    }


    fun getHtml(body: String?): String {
        var body = body
        body = body!!.replace("<div class=\"img-place-holder\">", "")
        body = body.replace("<div class=\"headline\">", "")

        val css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_news.css\" type=\"text/css\">"
        val builder = StringBuilder()
        builder.append("<!DOCTYPE html>\n")
        builder.append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
        builder.append("<head>\n")
        builder.append("\t<meta charset=\"utf-8\" />")
        builder.append(css)
        builder.append("\n</head>\n")
        builder.append("<body>\n")
        builder.append(body)
        builder.append("</body></html>")
        return builder.toString()
    }

    private fun getCss(cssUrl: String) {
        val client = OkHttpClient()
        val mBuilder = Request.Builder().url(cssUrl)
        mBuilder.method("GET", null)
        val request = mBuilder.build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    private fun initWebView() {
        //能够和js交互
        webView!!.settings.javaScriptEnabled = true
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView!!.settings.builtInZoomControls = false
        //缓存
        webView!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //开启DOM storage API功能
        webView!!.settings.domStorageEnabled = true
        //开启application Cache功能
        webView!!.settings.setAppCacheEnabled(false)

        webView!!.settings.setAppCacheEnabled(true)

        webView!!.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                val intent = Intent(this@DetailsActivity, InnerBrowserActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        webView!!.destroy()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }
}
