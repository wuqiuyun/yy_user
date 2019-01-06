package com.yiyue.user.module.home.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemServiceStylistBinding;
import com.yiyue.user.model.vo.bean.StylistBean;

/**
 * 服务-> 洗剪吹、烫染拉、接发、护理列表适配器
 * Created by wqy on 2018/11/1.
 */

public class ServiceStylistAdapter extends BaseRecycleViewAdapter<StylistBean> {

    private LayoutInflater mInflater;

    public ServiceStylistAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistViewHolder(mInflater.inflate(R.layout.item_service_stylist, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistViewHolder viewHolder = (StylistViewHolder) holder;
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
    }

    public class StylistViewHolder extends BaseViewHolder {
        private ItemServiceStylistBinding stylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            stylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
