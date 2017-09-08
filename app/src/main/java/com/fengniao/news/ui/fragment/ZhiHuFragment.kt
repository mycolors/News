package com.fengniao.news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fengniao.news.R
import com.fengniao.news.bean.ZhiHuResult
import com.fengniao.news.net.HttpClient
import com.fengniao.news.ui.activity.DetailsActivity
import com.fengniao.news.ui.adapter.ZhiHuArticleListAdapter
import com.fengniao.news.util.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ZhiHuFragment : Fragment() {

    internal var newsList: RecyclerView? = null
    internal var fabCalendar: FloatingActionButton? = null
    internal var swipeRefresh: SwipeRefreshLayout? = null
    private var mAdapter: ZhiHuArticleListAdapter? = null
    private var mList: MutableList<ZhiHuResult.Story>? = null
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater!!.inflate(R.layout.fragment_zhi_hu_article, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    private fun initView() {
        newsList = view!!.findViewById(R.id.news_list) as RecyclerView?
        fabCalendar = view!!.findViewById(R.id.fab_calendar) as FloatingActionButton?
        swipeRefresh = view!!.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout?
        mList = mutableListOf()
        mAdapter = ZhiHuArticleListAdapter(context, mList!!)
        newsList!!.layoutManager = LinearLayoutManager(context)
        newsList!!.adapter = mAdapter
        newsList!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                    fabCalendar!!.hide()
                else
                    fabCalendar!!.show()
                if (!newsList!!.canScrollVertically(1)) {
                    loadMore()
                }
            }
        })
        mAdapter!!.setmOnItemClickListener(object : ZhiHuArticleListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", mList!![position].id)
                startActivity(intent)
            }
        })

        fabCalendar!!.setOnClickListener { showPickDialog() }
        swipeRefresh!!.setOnRefreshListener({ loadData() })
    }

    private fun loadData() {
        val calendar = Calendar.getInstance()
        initDate(calendar)
        getZhiHuData(calendar.time, true)
    }

    fun loadMore() {
        val yesterday = Calendar.getInstance()
        yesterday.set(mYear, mMonth, mDay)
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1)
        initDate(yesterday)
        getZhiHuData(yesterday.time, false)
    }

    private fun getZhiHuData(date: Date, isClean: Boolean) {
        val time = DateUtils.dateToString("yyyyMMdd", date)
        HttpClient.getInstance()!!
                .getZhiHuService()
                .getZhiHuArticle(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { zhiHuResult ->
                    if (zhiHuResult.date == "20130519") {
                        mAdapter!!.setIsEnd(true)
                    } else {
                        mAdapter!!.setIsEnd(false)
                    }
                    if (isClean)
                        mList!!.clear()
                    mList!!.addAll(zhiHuResult.stories)
                    mAdapter!!.notifyDataSetChanged()
                    if (swipeRefresh!!.isRefreshing) {
                        swipeRefresh!!.isRefreshing = false
                    }
                }
    }

    private fun initDate(calendar: Calendar) {
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun showPickDialog() {
        val now: Calendar = Calendar.getInstance()
        now.set(mYear, mMonth, mDay)
        val dialog: com.wdullaer.materialdatetimepicker.date.DatePickerDialog? = com.wdullaer.materialdatetimepicker.date.DatePickerDialog
                .newInstance({ _, year, monthOfYear, dayOfMonth ->
                    mYear = year
                    mMonth = monthOfYear
                    mDay = dayOfMonth
                    val temp = Calendar.getInstance()
                    temp.clear()
                    temp.set(year, monthOfYear, dayOfMonth)
                    getZhiHuData(temp.time, true)
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        if (dialog != null) {
            dialog.maxDate = Calendar.getInstance()
            val minDate = Calendar.getInstance()
            //知乎日报首次上线是在2013.5.20   在Calendar中设置月份要比实际月份小1，同时从Calender中拿到月份也比实际月份小1
            minDate.set(2013, 4, 20)
            dialog.minDate = minDate
            dialog.vibrate(false)
            dialog.show(activity.fragmentManager, "DatePickerDialog")
        }
    }

}