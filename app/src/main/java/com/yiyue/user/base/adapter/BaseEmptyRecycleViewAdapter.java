package com.yiyue.user.base.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.databinding.LayoutEmptyBinding;

import java.util.ArrayList;

/**
 * Created by wqy on 2019/1/5.
 */
public abstract class BaseEmptyRecycleViewAdapter<T> extends RecyclerView.Adapter {

    public Context mContext;
    private final int EMPTY_VIEW = 0;
    private final int ITEM_VIEW = 1;
    private LayoutInflater mInflater;
    private RecycleViewItemListener mItemListener;
    private RecycleViewEmptyListener mEmptyListener;
    protected ArrayList<T> mDataList = new ArrayList<T>();

    public void setItemListener(RecycleViewItemListener listener) {
        this.mItemListener = listener;
    }

    public void setEmptyListener(RecycleViewEmptyListener listener) {
        this.mEmptyListener = listener;
    }

    public interface RecycleViewItemListener {

        void onItemClick(View view, int position);

        void OnItemLongClickListener(View view, int position);
    }

    public interface RecycleViewEmptyListener {

        void onEmptyClick();
    }

    public BaseEmptyRecycleViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseEmptyRecycleViewAdapter(ArrayList<T> dataList) {
        this.mDataList = dataList;
    }

    /**
     * 传入adapter的条目数，没有数据则返回 1
     */
    @Override
    public int getItemCount() {
        return (mDataList != null && mDataList.size() > 0) ? mDataList.size() : 1;
    }

    /**
     * 根据传入adapter数据来判断有无数据
     */
    @Override
    public int getItemViewType(int position) {
        if (mDataList != null && mDataList.size() > 0) {
            return ITEM_VIEW;
        }
        return EMPTY_VIEW;
    }

    public abstract RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType);

    public abstract void bindMyViewHolder(RecyclerView.ViewHolder holder, int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据不同的viewType引入不同的布局
        if (viewType == EMPTY_VIEW) {
            View emptyView = mInflater.inflate(R.layout.layout_empty, parent, false);
            return new EmptyViewHolder(emptyView);
        }
        return createMyViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == EMPTY_VIEW) {
            if (holder instanceof BaseEmptyRecycleViewAdapter.EmptyViewHolder) {
                EmptyViewHolder viewHolder = (EmptyViewHolder) holder;
                if (null != mEmptyListener) {
                    viewHolder.mBinding.tvEmpty.setOnClickListener(v -> {
                        mEmptyListener.onEmptyClick();
                    });
                }
            }
        } else {
            bindMyViewHolder(holder, position);
        }
    }

    /**
     * Add RecycleView Data
     *
     * @param dataList the dataList
     * @param notify   should notify adapter
     */
    public void addDataList(ArrayList<T> dataList, boolean notify) {
        if (null == dataList || dataList.size() < 0) return;
        mDataList.addAll(dataList);
        if (notify)
            notifyDataSetChanged();
    }

    /**
     * Set RecycleView Data
     *
     * @param dataList the dataList
     * @param notify   should notify adapter
     */
    public void setDataList(ArrayList<T> dataList, boolean notify) {
        if (null == dataList || dataList.size() < 0) return;
        mDataList.clear();
        mDataList.addAll(dataList);
        if (notify)
            notifyDataSetChanged();
    }

    /**
     * clear adapter data
     *
     * @param notify should notify adapter
     */
    public void clearDataList(boolean notify) {
        mDataList.clear();
        if (notify)
            notifyDataSetChanged();
    }

    public ArrayList<T> getDataList() {
        return mDataList;
    }

    public static class SimpleRecycleViewItemListener implements RecycleViewItemListener {
        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void OnItemLongClickListener(View view, int position) {

        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        private LayoutEmptyBinding mBinding;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    /**
     * wrap recycle view  ViewHolder
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
            if (null != mItemListener) {
                itemView.setOnClickListener(v -> {
                    mItemListener.onItemClick(itemView, getAdapterPosition());
                });
                itemView.setOnLongClickListener(v -> {
                    mItemListener.OnItemLongClickListener(itemView, getAdapterPosition());
                    return false;
                });
            }
        }
    }
}