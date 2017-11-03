package com.fengniao.news.module.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fengniao.news.R
import com.fengniao.news.base.BaseListActivity
import com.fengniao.news.base.FNAdapter
import kotlinx.android.synthetic.main.item_list_test.view.*

class MyTestActivity : BaseListActivity<String>() {

    override fun loadData() {
        enableRefresh(false)
        mList.add("属性动画")
        mList.add("自定义view")
        mList.add("三方app特效")
        mList.add(null)
        setListAdapter()
    }

    override fun getLayoutId(): Int = R.layout.activity_my_test

    override fun getView(parent: ViewGroup?, viewType: Int): View =
            LayoutInflater.from(this).inflate(R.layout.item_list_test, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder?.itemView?.tv_name?.text = mList[position]
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {

        when (position) {
            0 ->
                jumpToActivity(PropertyAnimationActivity::class.java)
            1 ->
                jumpToActivity(CustomizeViewActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
