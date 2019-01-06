package com.yiyue.user.module.mine.stylist.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemServiceBundleBinding;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;

/**
 * 服务套餐适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceBundleAdapter extends BaseRecycleViewAdapter<StylistDetailsBean.CardPackagesBean> {
    private Context context;

    public ServiceBundleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceProjectViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_service_bundle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistDetailsBean.CardPackagesBean bean = getDatas().get(position);
        ItemServiceBundleBinding mbinding = ((ServiceProjectViewHolder) holder).binding;
        mbinding.tvBundleName.setText(bean.getName());
        mbinding.tvProjectPrice.setText("￥" + bean.getPrice());
    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceBundleBinding binding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
