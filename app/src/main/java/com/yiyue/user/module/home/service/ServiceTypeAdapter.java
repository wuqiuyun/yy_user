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
import com.yiyue.user.databinding.ItemServiceTypeBinding;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.util.FormatUtil;
/**
 *服务类型
 * Create by lvlong on  2018/10/23
 */
public class ServiceTypeAdapter extends BaseRecycleViewAdapter<ServiceSettingBean> {

    private Context mContext;

    public ServiceTypeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_type, parent, false);
        return new AddServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddServiceViewHolder viewHolder = (AddServiceViewHolder) holder;
        ServiceSettingBean bean = mDatas.get(position);
        viewHolder.mBinding.cbLabel.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mBinding.cbLabel.setChecked(bean.isChecked());
    }

    public class AddServiceViewHolder extends BaseViewHolder{

        private ItemServiceTypeBinding mBinding;

        public AddServiceViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
