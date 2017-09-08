package com.fengniao.news.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.fengniao.news.R
import com.fengniao.news.bean.TouTiaoNews


class TouTiaoNewsAdapter(private val mContext: Context, private val mList: List<TouTiaoNews>) : RecyclerView.Adapter<TouTiaoNewsAdapter.MyViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(l: OnItemClickListener) {
        mOnItemClickListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_list_news, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.date.text = mList[position].date
        holder.author.text = mList[position].author


        if (!TextUtils.isEmpty(mList[position].picOne))
            Glide.with(mContext).load(mList[position].picOne).into(holder.imgOne)
        if (!TextUtils.isEmpty(mList[position].picTwo))
            Glide.with(mContext).load(mList[position].picTwo).into(holder.imgTwo)
        if (!TextUtils.isEmpty(mList[position].picThree))
            Glide.with(mContext).load(mList[position].picThree).into(holder.imgThree)
        holder.itemView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var date: TextView
        var author: TextView
        var imgOne: ImageView
        var imgTwo: ImageView
        var imgThree: ImageView

        init {
            title = itemView.findViewById(R.id.text_title) as TextView
            date = itemView.findViewById(R.id.text_date) as TextView
            author = itemView.findViewById(R.id.text_from) as TextView
            imgOne = itemView.findViewById(R.id.img_one) as ImageView
            imgTwo = itemView.findViewById(R.id.img_two) as ImageView
            imgThree = itemView.findViewById(R.id.img_three) as ImageView
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
