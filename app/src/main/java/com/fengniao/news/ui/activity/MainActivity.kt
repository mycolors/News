package com.fengniao.news.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.fengniao.news.R
import com.fengniao.news.base.BaseActivity
import com.fengniao.news.ui.fragment.TouTiaoNewsFragment
import com.fengniao.news.ui.fragment.ZhiHuFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val tabLayout: TabLayout = (findViewById(R.id.tab_layout) as TabLayout?)!!
        val viewPager: ViewPager = (findViewById(R.id.view_pager) as ViewPager?)!!
        viewPager.adapter = MainPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private inner class MainPageAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val titles = arrayOf("知乎日报", "今日头条")

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> ZhiHuFragment()
            else -> TouTiaoNewsFragment()
        }

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}
