package com.yiyue.user.module.home.stores;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemStoresBinding;
import com.yiyue.user.model.vo.bean.StoreBean;

/**
 * 门店
 * Created by wqy on 2018/11/51.
 */
public class StoreAdapter extends BaseRecycleViewAdapter<StoreBean> {

    private LayoutInflater mInflater;

    public StoreAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoresViewHolder(mInflater.inflate(R.layout.item_stores, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StoresViewHolder viewHolder = (StoresViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class StoresViewHolder extends BaseViewHolder {

        ItemStoresBinding mBinding;

        public StoresViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
