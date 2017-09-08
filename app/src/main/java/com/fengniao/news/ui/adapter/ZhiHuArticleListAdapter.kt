package com.fengniao.news.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.fengniao.news.R
import com.fengniao.news.bean.ZhiHuResult


class ZhiHuArticleListAdapter(private val mContext: Context, private val mList: List<ZhiHuResult.Story>) : RecyclerView.Adapter<ZhiHuArticleListAdapter.MyViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null
    private var isEnd = false

    fun setmOnItemClickListener(mOnItemClickListener: (OnItemClickListener)) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    fun setIsEnd(isEnd: Boolean) {
        this.isEnd = isEnd
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_list_footer, parent, false)
            MyViewHolder(view)
        } else {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_list_zhihu, parent, false)
            MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == mList.size) {
            if (isEnd) {
                holder.getView(R.id.progress_bar).visibility = View.GONE
                (holder.getView(R.id.tv_end) as TextView).setText(R.string.not_have_more)
            } else {
                holder.getView(R.id.progress_bar).visibility = View.VISIBLE
                (holder.getView(R.id.tv_end) as TextView).setText(R.string.loading)
            }
        } else {
            holder.title.text = mList[position].title
            if (mList[position].images.isNotEmpty())
                Glide.with(mContext).load(mList[position].images[0]).into(holder.imgNews)
            holder.itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(holder.adapterPosition)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mList.size) {
            1
        } else 0
    }

    override fun getItemCount(): Int = mList.size + 1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_news) as TextView
        val imgNews: ImageView = itemView.findViewById(R.id.img_news) as ImageView

        fun getView(resId: Int): View = itemView.findViewById(resId)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
