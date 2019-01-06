package com.yiyue.user.module.mine.stylist.details;

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
import com.yiyue.user.databinding.ItemStoreJoinBinding;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;
import com.yiyue.user.util.FormatKmUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * Created by zm on 2018/10/11.
 */
public class StoreAdapter extends BaseRecycleViewAdapter<StylistDetailsBean.CardStoreDTOsBean> {
    private Context context;

    public StoreAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_join, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistDetailsBean.CardStoreDTOsBean beans = getDatas().get(position);
        ItemStoreJoinBinding mBinding = ((StoreViewHolder) holder).itemStoreJoinBinding;
        // 图片
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        ImageLoader.loadImage(mBinding.ivStore, beans.getPicture(), config, null);

        // 门店名
        mBinding.tvName.setText(beans.getStorename());
        // 地址
        mBinding.tvAddress.setText(beans.getLocation());
        // 距离
        mBinding.tvDistance.setText(String.valueOf(FormatKmUtil.FormatKmStr(beans.getDistance())));
    }

    private class StoreViewHolder extends BaseViewHolder {
        ItemStoreJoinBinding itemStoreJoinBinding;

        public StoreViewHolder(View itemView) {
            super(itemView);
            itemStoreJoinBinding = DataBindingUtil.bind(itemView);
        }
    }
}
