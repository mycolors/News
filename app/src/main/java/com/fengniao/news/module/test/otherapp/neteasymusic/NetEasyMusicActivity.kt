package com.fengniao.news.module.test.otherapp.neteasymusic

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.view.View
import com.fengniao.news.R
import com.fengniao.news.base.BaseActivity
import kotlinx.android.synthetic.main.activity_net_easy_music.*

class NetEasyMusicActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_net_easy_music

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv_music_one.setOnClickListener {
            //Shared Element效果 单个view
//            startActivity(Intent(this, NetEasySongListActivity::class.java),
//                    ActivityOptions.makeSceneTransitionAnimation(this,
//                            iv_music_one, ViewCompat.getTransitionName(iv_music_one)).toBundle())
            //多个view
            jump()
        }

    }

    fun jump() {
        val first = Pair<View, String>(iv_music_one, ViewCompat.getTransitionName(iv_music_one))
        val second = Pair<View, String>(iv_music_two, ViewCompat.getTransitionName(iv_music_two))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, first, second)
        ActivityCompat.startActivity(this, Intent(this, NetEasySongListActivity::class.java),
                options.toBundle())
    }

}
