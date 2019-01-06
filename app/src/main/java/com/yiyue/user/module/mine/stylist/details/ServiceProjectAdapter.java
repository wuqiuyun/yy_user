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
import com.yiyue.user.databinding.ItemServiceProjectBinding;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;
import com.yl.core.component.log.DLog;

/**
 * 服务项目适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceProjectAdapter extends BaseRecycleViewAdapter<StylistDetailsBean.CardServerItemsBean> {
    private Context context;

    public ServiceProjectAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceProjectViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_service_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistDetailsBean.CardServerItemsBean bean = getDatas().get(position);
        ItemServiceProjectBinding mBinding = ((ServiceProjectViewHolder) holder).binding;
        mBinding.tvProjectName.setText(bean.getName());
        mBinding.tvProjectPrice.setText("￥" + bean.getPrice());
        // 是否显示右侧箭头
        int type = bean.getType();
        if (2 == type) {
            mBinding.ivArrow.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivArrow.setVisibility(View.INVISIBLE);
        }
    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceProjectBinding binding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
