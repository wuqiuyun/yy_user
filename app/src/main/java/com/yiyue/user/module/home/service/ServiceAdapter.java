package com.yiyue.user.module.home.service;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemStylistBinding;
import com.yiyue.user.model.vo.bean.StylistBean;

/**
 * Created by wqy on 2018/11/1.
 */

public class ServiceAdapter extends BaseRecycleViewAdapter<StylistBean> {
    private Context context;

    public ServiceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceAdapter.StylistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceAdapter.StylistViewHolder viewHolder = (ServiceAdapter.StylistViewHolder) holder;
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
    }

    private class StylistViewHolder extends RecyclerView.ViewHolder {
        ItemStylistBinding stylistBinding;

        public StylistViewHolder(View inflate) {
            super(inflate);
            stylistBinding = DataBindingUtil.bind(inflate);
        }
    }
}
