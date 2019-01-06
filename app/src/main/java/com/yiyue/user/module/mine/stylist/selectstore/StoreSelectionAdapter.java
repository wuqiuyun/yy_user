package com.yiyue.user.module.mine.stylist.selectstore;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemSelectStoreBinding;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.util.FormatKmUtil;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * 选择门店适配器
 * Created by wqy on 2018/11/6.
 */

class StoreSelectionAdapter extends BaseRecycleViewAdapter<StoreBean> {
    private LayoutInflater inflater;

    public StoreSelectionAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreSelectionViewHolder(inflater.inflate(R.layout.item_select_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StoreSelectionViewHolder viewHolder = (StoreSelectionViewHolder) holder;
        StoreBean bean = getDatas().get(position);
        viewHolder.selectStoreBinding.cbStatus.setChecked(bean.isStatus());
        viewHolder.selectStoreBinding.tvStorename.setText(FormatUtil.Formatstring(bean.getStorename()));
        if (bean.getDistance()!=null){
            double d=Double.valueOf(bean.getDistance());
            viewHolder.selectStoreBinding.tvDistance.setText(FormatKmUtil.FormatKmStr(d));
        }
        viewHolder.selectStoreBinding.tvLocation.setText(FormatUtil.Formatstring(bean.getLocation()));

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.meizi)
                .setErrorResId(R.drawable.meizi)
                .build();
        ImageLoader.loadImage( viewHolder.selectStoreBinding.ivPictrue, bean.getPictrue(), config, null);
    }

    private class StoreSelectionViewHolder extends BaseViewHolder {
        ItemSelectStoreBinding selectStoreBinding;

        public StoreSelectionViewHolder(View itemView) {
            super(itemView);
            selectStoreBinding = DataBindingUtil.bind(itemView);
        }
    }
}
