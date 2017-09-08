package com.fengniao.news.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.fengniao.news.R
import java.util.*

abstract class BaseListActivity<T> : BaseActivity(), FNAdapter.ViewProvider {
    @BindView(R.id.recycler_view)
    internal var mRecyclerView: RecyclerView? = null
    @BindView(R.id.swipe_refresh)
    internal var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected lateinit var mList: MutableList<T>
    internal var mAdapter: FNAdapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    internal var mPage: Int = 0
    internal var mCount = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(0)
        initView()
    }

    //刷新控件开关
    fun enableRefresh(status: Boolean) {
        //禁止下拉刷新
        swipeRefreshLayout!!.isEnabled = status
    }

    //是否显示refreshBar
    fun showRefreshBar(isShow: Boolean) {
        swipeRefreshLayout!!.isRefreshing = isShow
    }

    fun setListAdapter() {
        if (activity == null) return

        if (mAdapter == null) {
            initAdapter()
            mRecyclerView!!.adapter = mAdapter
        } else
            notifyDataSetChanged()

        if (swipeRefreshLayout!!.isRefreshing)
            showRefreshBar(false)

        mAdapter!!.loadMoreStatus = false

    }

    abstract fun loadData()

    override fun loadMore() {
        mPage++
        loadData()
    }

    fun initView() {
        mList = ArrayList()
        showRefreshBar(true)
        initAdapter()
        mLayoutManager = onCreateLayoutManager()
        if (mLayoutManager == null)
            mLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = mAdapter
        swipeRefreshLayout!!.setOnRefreshListener { this@BaseListActivity.onRefresh() }
        loadData()
    }


    fun onRefresh() {
        mPage = 0
        mList.clear()
        loadData()
    }

    fun initAdapter() {
        mAdapter = FNAdapter(this, mList)
        mAdapter!!.setViewProvider(this)
    }

    fun enableLoadMore(isLoadMore: Boolean) {
        mAdapter!!.enableLoadMore(isLoadMore)
    }

    fun onCreateLayoutManager(): RecyclerView.LayoutManager? {
        return null
    }

    fun setLayoutManager(manager: RecyclerView.LayoutManager) {
        mLayoutManager = manager
    }

    fun notifyDataSetChanged() {
        mAdapter!!.notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override val itemCount: Int
        get() = mList.size
}
