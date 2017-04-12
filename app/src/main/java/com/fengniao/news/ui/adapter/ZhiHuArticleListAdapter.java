package com.fengniao.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fengniao.news.R;
import com.fengniao.news.bean.ZhiHuArticle;
import com.fengniao.news.ui.activity.MainActivity;

import java.util.List;


public class ZhiHuArticleListAdapter extends RecyclerView.Adapter<ZhiHuArticleListAdapter.MyViewHolder> {
    private Context mContext;
    private List<ZhiHuArticle> mList;
    private OnItemClickListener mOnItemClickListener;
    private boolean isEnd = false;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ZhiHuArticleListAdapter(Context mContext, List<ZhiHuArticle> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_footer, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_zhihu, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (position == mList.size()) {
            if (isEnd) {
                holder.getView(R.id.progress_bar).setVisibility(View.GONE);
                ((TextView) holder.getView(R.id.tv_end)).setText(R.string.not_have_more);
            } else {
                holder.getView(R.id.progress_bar).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.tv_end)).setText(R.string.loading);
            }
        } else {
            holder.title.setText(mList.get(position).title);
            if (mList.get(position).images.size() > 0)
                Glide.with(mContext).load(mList.get(position).images.get(0)).into(holder.imgNews);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imgNews;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_news);
            imgNews = (ImageView) itemView.findViewById(R.id.img_news);
        }

        View getView(int resId) {
            return itemView.findViewById(resId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
