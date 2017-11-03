package com.fengniao.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.fengniao.news.R
import com.fengniao.news.base.BaseActivity
import com.fengniao.news.module.NewsFragment
import com.fengniao.news.module.read.YueFMActivity
import com.fengniao.news.module.test.MyTestActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    lateinit var tabLayout: TabLayout
    var newsFragment: NewsFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
//        } else
//            Log.i("test", Utils.getDeviceId(this))
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1 && permissions.isNotEmpty()) {
//            Log.i("test", Utils.getDeviceId(this))
//        }
//    }


    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        initHomePageFragment()
//        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
//            //drawer滑动时多次调用
//            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
//            }
//            //drawer彻底关闭后调用一次
//            override fun onDrawerClosed(drawerView: View?) {
//            }
//            //drawer彻底打开后调用一次
//            override fun onDrawerOpened(drawerView: View?) {
//            }
//            //drawer状态发生变化时，和viewpager类似，state为1时表示drawer正在拖动，
//            //state为2是表示drawer滑动到最终位置
//            //state为0时表示drawer完成动作处于空闲
//            //操作顺序为1——》2——》0
//            override fun onDrawerStateChanged(newState: Int) {
//            }
//
//        })
        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_news -> initHomePageFragment()
                R.id.item_read -> {
                    startActivity(Intent(this, YueFMActivity::class.java))
                }
                R.id.item_test -> {
                    startActivity(Intent(this, MyTestActivity::class.java))
                }
            }
            drawer_layout.closeDrawers()
            false
        }
        app_bar_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset -> }
    }

    private fun initHomePageFragment() {
        newsFragment = supportFragmentManager.findFragmentByTag("home") as NewsFragment?
        if (newsFragment == null)
            newsFragment = NewsFragment()
        if (newsFragment!!.isVisible) return
        if (newsFragment!!.isAdded) {
            supportFragmentManager.beginTransaction().show(newsFragment).commit()
        } else
            supportFragmentManager.beginTransaction()
                    .add(R.id.content, newsFragment, "home")
                    .show(newsFragment)
                    .commit()
    }

}
