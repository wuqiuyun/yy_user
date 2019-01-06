package com.yiyue.user.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemFilterSubBinding;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.log.DLog;


/**
 * Created by lvlong on 2018/10/12.
 */
public class FilterSubAdapter extends BaseRecycleViewAdapter<ServiceSettingBean> {

    private Context mContext;

    public FilterSubAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FilterSubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_sub, parent, false);
        return new FilterSubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterSubViewHolder viewHolder = (FilterSubViewHolder) holder;
        ServiceSettingBean bean = mDatas.get(position);
        viewHolder.mBinding.cbLabel.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mBinding.cbLabel.setChecked(bean.isChecked());
    }

    public class FilterSubViewHolder extends BaseViewHolder {
        private ItemFilterSubBinding mBinding;

        public FilterSubViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
