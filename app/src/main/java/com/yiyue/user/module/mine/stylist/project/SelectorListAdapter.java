package com.yiyue.user.module.mine.stylist.project;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemSelectProjectBinding;
import com.yiyue.user.model.vo.bean.StylistServerBean;
import com.yiyue.user.util.FormatUtil;

/**
 * Created by wqy on 2018/11/6.
 */

public class SelectorListAdapter extends BaseRecycleViewAdapter<StylistServerBean.ServerItemsBean> {

    private LayoutInflater inflater;
    private Context context;

    public SelectorListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectListViewHolder(inflater.inflate(R.layout.item_select_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectListViewHolder mHolder = (ProjectListViewHolder) holder;
        StylistServerBean.ServerItemsBean serverItemsBean = mDatas.get(position);
        if (serverItemsBean.isChecked()){
            mHolder.mBinding.tvServerName.setText(FormatUtil.Formatstring(serverItemsBean.getSelectedTitle()));
            mHolder.mBinding.tvPrice.setText(String.format(context.getString(R.string.RMB),serverItemsBean.getSelectedPrice()));
            mHolder.mBinding.tvServerName.setTextColor(context.getResources().getColor(R.color.color_FF0000));
            mHolder.mBinding.tvPrice.setTextColor(context.getResources().getColor(R.color.color_FF0000));
        }else {
            mHolder.mBinding.tvServerName.setText(FormatUtil.Formatstring(serverItemsBean.getServicename()));
            mHolder.mBinding.tvPrice.setText(FormatUtil.Formatstring("￥"+serverItemsBean.getPrice()+"起"));
            mHolder.mBinding.tvServerName.setTextColor(context.getResources().getColor(R.color.color_666666));
            mHolder.mBinding.tvPrice.setTextColor(context.getResources().getColor(R.color.color_666666));
        }
    }

    public class ProjectListViewHolder extends BaseViewHolder {
        ItemSelectProjectBinding mBinding;

        public ProjectListViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
