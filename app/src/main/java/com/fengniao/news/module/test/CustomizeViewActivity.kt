package com.fengniao.news.module.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fengniao.news.R
import com.fengniao.news.base.BaseListActivity
import com.fengniao.news.base.FNAdapter
import kotlinx.android.synthetic.main.item_list_test.view.*

class CustomizeViewActivity : BaseListActivity<String>() {
    override fun loadData() {
        mList.add("")
        mList.add("")
        mList.add("")
        mList.add("")
        mList.add("")
        enableRefresh(false)
        setListAdapter()
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View =
            LayoutInflater.from( this).inflate(R.layout.item_list_test, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder?.itemView?.tv_name?.text = mList[position]
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
    }

    override fun getLayoutId(): Int = R.layout.activity_customize_view

}
