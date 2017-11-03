package com.fengniao.news.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.fengniao.news.R
import com.fengniao.news.base.BaseActivity

class InnerBrowserActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_inner_browser

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = findViewById(R.id.web_view) as WebView
        progressBar = findViewById(R.id.progress_bar) as ProgressBar
        initWebView()
        initView()
    }


    private fun initView() {
        val url = intent.getStringExtra("url")
        webView.loadUrl(url)
    }

    private fun initWebView() {
        //能够和js交互
        webView.settings.javaScriptEnabled = true
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.settings.builtInZoomControls = false
        //缓存
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //开启DOM storage API功能
        webView.settings.domStorageEnabled = true
        //开启application Cache功能
        webView.settings.setAppCacheEnabled(false)

        webView.setWebViewClient(WebViewClient())

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        })

        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack()
                    return@OnKeyListener true
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }
}
