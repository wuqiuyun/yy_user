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
import com.yiyue.user.databinding.ItemStoreStylistBinding;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * 入驻美发师适配器
 * <p>
 * Created by zm on 2018/10/13.
 */
public class StylistAdapter extends BaseRecycleViewAdapter<StylistBean> {
    private Context mContext;

    public StylistAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_stylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistBean bean = getDatas().get(position);
        ItemStoreStylistBinding stylistBinding = ((StylistViewHolder) holder).itemStoreStylistBinding;
        // 昵称
        stylistBinding.tvStylistName.setText(FormatUtil.Formatstring(bean.getStylistName()));
        // 头像
        ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        ImageLoader.loadImage(stylistBinding.ivCoverImage, bean.getCoverImg(), headConfig, null);
    }

    public class StylistViewHolder extends BaseViewHolder {
        ItemStoreStylistBinding itemStoreStylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            itemStoreStylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
