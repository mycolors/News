package com.fengniao.news.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fengniao.news.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseListFragment<T> : BaseFragment(), FNAdapter.ViewProvider {
    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    protected lateinit var mList: MutableList<T>
    private var mAdapter: FNAdapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mPage: Int = 0
    internal var mCount = 10
    private var isViewCreated = false
    private var isFirst = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!enableLazyLoad()) return
        if (isViewCreated) lazyLoad()
    }

    fun lazyLoad() {
        //只有第一次进入才进行懒加载
        if (isFirst) {
            loadData(true)
            isFirst = false
        }
    }

    //是否开启懒加载
    abstract fun enableLazyLoad(): Boolean

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true
        initView()
    }

    //刷新控件开关
    fun enableRefresh(status: Boolean) {
        //禁止下拉刷新
        swipeRefreshLayout.isEnabled = status
    }

    //是否显示refreshBar
    fun showRefreshBar(isShow: Boolean) {
        swipeRefreshLayout.isRefreshing = isShow
    }

    fun setListAdapter() {
        if (activity == null) return

        if (mAdapter == null) {
            initAdapter()
            mRecyclerView.adapter = mAdapter
        } else
            notifyDataSetChanged()

        if (swipeRefreshLayout.isRefreshing)
            showRefreshBar(false)

        mAdapter!!.loadMoreStatus = false
    }

    abstract fun loadData(isClear: Boolean)

    override fun loadMore() {
        mPage++
        loadData(false)
    }

    private fun initView() {
        mRecyclerView = view!!.findViewById(R.id.recycler_view) as RecyclerView
        swipeRefreshLayout = view!!.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout

        mList = ArrayList()
        showRefreshBar(true)
        initAdapter()
        mLayoutManager = onCreateLayoutManager()
        if (mLayoutManager == null)
            mLayoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
        swipeRefreshLayout

                .setOnRefreshListener { this@BaseListFragment.onRefresh() }
        if (!enableLazyLoad()) loadData(false)
    }

    fun onRefresh() {
        mPage = 0
        loadData(true)
    }

    fun initAdapter() {
        mAdapter = FNAdapter(context, mList)
        mAdapter!!.setViewProvider(this)
    }

    fun enableLoadMore(isLoadMore: Boolean) {
        mAdapter!!.enableLoadMore(isLoadMore)
    }

    fun onCreateLayoutManager(): RecyclerView.LayoutManager? = null

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
}// Required empty public constructor
