package com.fengniao.news.module.test.otherapp

import android.view.View
import android.view.ViewGroup
import com.fengniao.news.R
import com.fengniao.news.base.BaseListActivity
import com.fengniao.news.base.FNAdapter
import com.fengniao.news.module.test.otherapp.neteasymusic.NetEasyMusicActivity
import kotlinx.android.synthetic.main.item_list_test.view.*

//三方app效果
class OtherAppFeaturesActivity : BaseListActivity<String>() {
    override fun loadData() {
        mList.add("网易云音乐歌单")
        mList.add("")
        mList.add("")
        mList.add("")
        mList.add("")
        setListAdapter()
    }

    override fun getLayoutId(): Int = R.layout.activity_other_app_features

    override fun getView(parent: ViewGroup?, viewType: Int): View =
            layoutInflater.inflate(R.layout.item_list_test, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder?.itemView?.tv_name?.text = mList[position]
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        when (position) {
            0 -> jumpToActivity(NetEasyMusicActivity::class.java)
        }
    }


}
