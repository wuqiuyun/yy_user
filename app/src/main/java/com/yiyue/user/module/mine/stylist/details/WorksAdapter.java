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
import com.yiyue.user.databinding.ItemStylistWorksBinding;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * Created by zm on 2018/10/11.
 */
public class WorksAdapter extends BaseRecycleViewAdapter<StylistDetailsBean.CardOpusDTOsBean> {
    private Context context;

    public WorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistDetailsBean.CardOpusDTOsBean bean = getDatas().get(position);
        ItemStylistWorksBinding mBinding = ((WorksViewHolder) holder).itemStylistWorksBinding;
        if (!TextUtils.isEmpty( bean.getStylistOpusCovers())){
            ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                    .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                    .setAsBitmap(true)
                    .setPlaceHolderResId(R.drawable.meizi)
                    .setErrorResId(R.drawable.meizi)
                    .setBlur(true)
                    .build();
            ImageLoader.loadImage(mBinding.ivWorks, bean.getStylistOpusCovers(), config, null);
        }
    }

    private class WorksViewHolder extends BaseViewHolder {
        ItemStylistWorksBinding itemStylistWorksBinding;

        public WorksViewHolder(View itemView) {
            super(itemView);
            itemStylistWorksBinding = DataBindingUtil.bind(itemView);
        }
    }
}
