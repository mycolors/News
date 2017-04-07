package com.fengniao.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fengniao.news.R;
import com.fengniao.news.bean.TouTiaoNews;

import java.util.List;

import butterknife.OnClick;


public class TouTiaoNewsAdapter extends RecyclerView.Adapter<TouTiaoNewsAdapter.MyViewHolder> {
    private Context mContext;
    private List<TouTiaoNews> mList;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public TouTiaoNewsAdapter(Context mContext, List<TouTiaoNews> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).title);
        holder.date.setText(mList.get(position).date);
        holder.author.setText(mList.get(position).author);



        if (!TextUtils.isEmpty(mList.get(position).picOne))
            Glide.with(mContext).load(mList.get(position).picOne).into(holder.imgOne);
        if (!TextUtils.isEmpty(mList.get(position).picTwo))
            Glide.with(mContext).load(mList.get(position).picTwo).into(holder.imgTwo);
        if (!TextUtils.isEmpty(mList.get(position).picThree))
            Glide.with(mContext).load(mList.get(position).picThree).into(holder.imgThree);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!= null){
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView author;
        ImageView imgOne;
        ImageView imgTwo;
        ImageView imgThree;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_title);
            date = (TextView) itemView.findViewById(R.id.text_date);
            author = (TextView) itemView.findViewById(R.id.text_from);
            imgOne = (ImageView) itemView.findViewById(R.id.img_one);
            imgTwo = (ImageView) itemView.findViewById(R.id.img_two);
            imgThree = (ImageView) itemView.findViewById(R.id.img_three);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
