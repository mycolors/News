package com.fengniao.news.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.fengniao.news.R


class FNAdapter<T>(private val mContext: Context, private val mList: List<T>) : RecyclerView.Adapter<FNAdapter.MyViewHolder>() {

    private var mViewProvider: ViewProvider? = null
    private var isLoadMore: Boolean = false  //是否加载更多
    //获取列表加载状态
    //设置加载更多状态
    var loadMoreStatus: Boolean = false   //标记记载状态

    fun setViewProvider(mViewProvider: ViewProvider) {
        this.mViewProvider = mViewProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == TYPE_LOAD_MORE) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_list_footer, parent, false)
            return MyViewHolder(view)
        }
        if (mViewProvider != null) {
            val view = mViewProvider!!.getView(parent, viewType)
            return MyViewHolder(view)
        }

        val view = View(mContext)
        return MyViewHolder(view)
    }


    fun enableLoadMore(isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (isLoadMore) {
            if (holder.itemViewType == TYPE_LOAD_MORE) {
                if (loadMoreStatus) {
                    return
                }
                mViewProvider!!.loadMore()
                loadMoreStatus = true
                return
            }
        }

        if (mViewProvider != null) {
            mViewProvider!!.bindDataToView(holder, position)
            holder.itemView.setOnClickListener(View.OnClickListener {
                if (position != mList.size)
                    mViewProvider!!.onItemClick(holder, holder.adapterPosition)
                else
                    return@OnClickListener
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoadMore) {
            if (position == itemCount - 1) {
                return 20
            }
        }
        return if (mViewProvider != null) {
            mViewProvider!!.getItemViewType(position)
        } else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mViewProvider!!.itemCount
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mViews = SparseArray<View>()

        fun <T : View> getView(resId: Int): T? {
            var t: T? = mViews.get(resId) as T
            if (t == null) {
                t = itemView.findViewById(resId) as T
                if (t != null) {
                    mViews.put(resId, t)
                }
            }
            return t
        }

        fun setText(resId: Int, text: CharSequence) {
            val textView = itemView.findViewById(resId) as TextView
            textView.text = text
        }
    }

    interface ViewProvider {
        fun getView(parent: ViewGroup, viewType: Int): View

        fun bindDataToView(holder: MyViewHolder, position: Int)

        fun getItemViewType(position: Int): Int

        val itemCount: Int

        fun onItemClick(holder: MyViewHolder, position: Int)

        fun loadMore()
    }

    companion object {
        val TYPE_LOAD_MORE = 20
    }
}
