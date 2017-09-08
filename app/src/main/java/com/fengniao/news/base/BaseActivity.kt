package com.fengniao.news.base

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast

import com.fengniao.news.R
import com.fengniao.news.util.UIUtils

import butterknife.ButterKnife

open class BaseActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UIUtils.translucentBar(this, Color.TRANSPARENT)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        ButterKnife.bind(this)
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
        }
        setTitle(R.string.app_name)
    }


    override fun setTitle(title: CharSequence) {
        super.setTitle("")
        if (mToolbar != null) {
            val textTitle = mToolbar!!.findViewById(R.id.title) as TextView
            if (textTitle != null)
                textTitle.text = title
        }
    }

    fun showToast(msg: String) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }


    val activity: Activity
        get() = this
}
