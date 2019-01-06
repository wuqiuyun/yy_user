package com.yiyue.user.module.mine.coupon.usage;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemUsageBinding;
import com.yiyue.user.model.vo.bean.UsageBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;


/*
 * Create by lvlong on  2018/12/4
 */

public class UsageAdapter extends BaseRecycleViewAdapter<UsageBean> {

    private Context mContext;

    public UsageAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsageAdapter.UsageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_usage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UsageViewHolder viewHolder = (UsageViewHolder) holder;
        UsageBean bean = getDatas().get(position);

        ImageLoaderConfig config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        ImageLoader.loadImage(viewHolder.mBinding.ivHead, bean.getHeadPortrait(), config, null);

        int type = bean.getPackageType();

        viewHolder.mBinding.tvStylistName.setText("美发师: " + bean.getStylistName());
        viewHolder.mBinding.tvStoreName.setText("门  店: " + bean.getStoreName());

        if (type == 1){
            viewHolder.mBinding.tvCouponsName.setText("套餐劵: +[单项套餐] " + bean.getPackageName());
        }else if (type == 2){
            viewHolder.mBinding.tvCouponsName.setText("套餐劵: +[多项套餐] " + bean.getPackageName());
        }


        if (!TextUtils.isEmpty(bean.getEndTime())){

            viewHolder.mBinding.tvEndTime.setText("完成时间: " + bean.getEndTime());
        }else {
            viewHolder.mBinding.tvEndTime.setText("完成时间: 当前未完成");
        }

        viewHolder.mBinding.tvAppointmentTime.setText("预约时间: "+bean.getAppointmentTime());


    }

    public class UsageViewHolder extends BaseViewHolder{

        private ItemUsageBinding mBinding;

        public UsageViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
