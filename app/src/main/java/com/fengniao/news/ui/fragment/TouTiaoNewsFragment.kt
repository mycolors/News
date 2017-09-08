package com.fengniao.news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import com.bumptech.glide.Glide
import com.fengniao.news.R
import com.fengniao.news.app.Constant.UNKNOWN_ERROR
import com.fengniao.news.base.BaseListFragment
import com.fengniao.news.base.FNAdapter
import com.fengniao.news.bean.TouTiaoNews
import com.fengniao.news.net.FNCallback
import com.fengniao.news.net.HttpUtils
import com.fengniao.news.ui.activity.InnerBrowserActivity
import com.fengniao.news.util.JsonUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class TouTiaoNewsFragment : BaseListFragment<TouTiaoNews>() {
    @BindView(R.id.swipe_refresh)
    internal var swipeRefresh: SwipeRefreshLayout? = null

    override fun setLayoutId(): Int = R.layout.fragment_tou_tiao_news

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
    }

    override fun loadData(isClear: Boolean) {
        getNews(isClear)
    }


    private fun initList() {
        enableRefresh(false)
        enableLoadMore(true)
    }

    override fun enableLazyLoad(): Boolean {
        return true
    }


    fun getNews(isClear: Boolean) {

        HttpUtils.instance.getTouTiaoNews(object : FNCallback {
            override fun onReceiveData(function: String, data: String, msg: String) {
                try {
                    val responseJson = JSONObject(data)
                    val dataArray = responseJson.getJSONObject("result")
                            .getJSONArray("data")
                    val list = JsonUtils.jsonToList(dataArray.toString(), TouTiaoNews::class.java)
                    if (isClear) mList.clear()
                    mList.addAll(list)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                activity.runOnUiThread { setListAdapter() }
            }

            override fun onReceiveError(function: String, errorCode: Int, msg: String) {
                Toast.makeText(context, UNKNOWN_ERROR, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_list_news, parent, false)
    }

    override fun bindDataToView(holder: FNAdapter.MyViewHolder, position: Int) {
        holder.setText(R.id.text_title, mList[position].title!!)
        holder.setText(R.id.text_date, mList[position].date!!)
        holder.setText(R.id.text_from, mList[position].author!!)

        if (!TextUtils.isEmpty(mList[position].picOne))
            Glide.with(context).load(mList[position].picOne).into(holder.getView<View>(R.id.img_one) as ImageView)
        if (!TextUtils.isEmpty(mList[position].picTwo))
            Glide.with(context).load(mList[position].picTwo).into(holder.getView<View>(R.id.img_two) as ImageView)
        if (!TextUtils.isEmpty(mList[position].picThree))
            Glide.with(context).load(mList[position].picThree).into(holder.getView<View>(R.id.img_three) as ImageView)
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder, position: Int) {
        val intent = Intent(activity, InnerBrowserActivity::class.java)
        intent.putExtra("url", mList[position].url)
        startActivity(intent)
    }

}// Required empty public constructor
