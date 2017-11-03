package com.fengniao.news.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fengniao.news.R
import com.fengniao.news.ui.activity.MainActivity
import com.fengniao.news.ui.fragment.ZhiHuFragment

//新闻页面
class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater!!.inflate(R.layout.fragment_news, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val viewPager = view!!.findViewById(R.id.view_pager) as ViewPager
        viewPager.adapter = NewsPageAdapter(childFragmentManager)
        if (activity is MainActivity)
            (activity as MainActivity).tabLayout.setupWithViewPager(viewPager)
    }

    private inner class NewsPageAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val titles = arrayOf("知乎日报")

        override fun getItem(position: Int): Fragment =
                ZhiHuFragment()

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}