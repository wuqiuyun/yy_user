package com.yiyue.user.module.home.store;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemServiceScopeBinding;
import com.yiyue.user.model.vo.bean.StoreServerScopeBean;

/**
 * 服务范围适配器
 * <p>
 * Created by zm on 2018/10/13.
 */
public class ServiceScopeAdapter extends BaseRecycleViewAdapter<String> {
    private Context mContext;

    public ServiceScopeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_service_scope, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceViewHolder viewHolder = (ServiceViewHolder) holder;
//        String bean = mDatas.get(position);
        viewHolder.mBinding.tvName.setText(mDatas.get(position));
    }

    public class ServiceViewHolder extends BaseViewHolder {
        ItemServiceScopeBinding mBinding;
        public ServiceViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
