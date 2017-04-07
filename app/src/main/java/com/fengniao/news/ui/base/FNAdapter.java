package com.fengniao.news.ui.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FNAdapter extends RecyclerView.Adapter<FNAdapter.MyViewHolder> {
    private ViewProvider mViewProvider;

    public void setViewProvider(ViewProvider mViewProvider) {
        this.mViewProvider = mViewProvider;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewProvider.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        mViewProvider.bindDataToView(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewProvider.onItemClick(holder, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mViewProvider.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mViewProvider.getItemCount();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews = new SparseArray<>();

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int resId) {
            T t = (T) mViews.get(resId);
            if (t == null) {
                t = (T) itemView.findViewById(resId);
                if (t != null) {
                    mViews.put(resId, t);
                }
            }
            return t;
        }

        public void setText(int resId, CharSequence text) {
            TextView textView = (TextView) itemView.findViewById(resId);
            textView.setText(text);
        }
    }

    public interface ViewProvider {
        MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void bindDataToView(MyViewHolder holder, int position);

        int getItemViewType(int position);

        int getItemCount();

        void onItemClick(MyViewHolder holder, int position);
    }
}
