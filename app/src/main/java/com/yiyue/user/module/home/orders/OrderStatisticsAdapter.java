package com.yiyue.user.module.home.orders;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemOrderStatisticsBinding;


/*
 *  @文件名:   OrderStatisticsAdapter
 *  @创建者:   27407
 *  @创建时间:  2018/10/14 14:06
 *  @描述：    订单统计适配器
 */

public class OrderStatisticsAdapter extends BaseRecycleViewAdapter {

    private Context mContext;

    public OrderStatisticsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_statistics, parent, false);
        return new OrderStatisticsAdapter.OrderStatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class OrderStatisticsViewHolder extends BaseViewHolder {
        private ItemOrderStatisticsBinding mBinding;

        public OrderStatisticsViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
