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
import com.yiyue.user.databinding.ItemSynthesisBinding;
import com.yiyue.user.model.vo.bean.SortBean;




/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/15 16:20
 *  @描述：    综合排序选择器
 */

public class SynthesisAdapter extends BaseRecycleViewAdapter<SortBean> {

    private Context mContext;
    public SynthesisAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_synthesis, parent, false);
            return new SynthesisViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SynthesisViewHolder viewHolder = (SynthesisViewHolder) holder;
            viewHolder.mBinding.tvType.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).isChoose()){
                viewHolder.mBinding.tvType.setTextColor(mContext.getResources().getColor(R.color.color_FFA200));
            }else {
                viewHolder.mBinding.tvType.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class SynthesisViewHolder extends BaseViewHolder {
        private ItemSynthesisBinding mBinding;

        public SynthesisViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
