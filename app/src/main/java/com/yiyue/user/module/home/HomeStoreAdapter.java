package com.yiyue.user.module.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemHomeStoreBinding;
import com.yiyue.user.model.vo.bean.StoreRecommendBean;
import com.yiyue.user.util.FormatKmUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * 首页口碑理发店推荐适配器
 * Created by wqy on 2018/10/31.
 */
public class HomeStoreAdapter extends BaseRecycleViewAdapter<StoreRecommendBean> {

    private LayoutInflater mInflater;
    private Context context;

    public HomeStoreAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeStoreViewHolder(mInflater.inflate(R.layout.item_home_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StoreRecommendBean storeRecommendBean = mDatas.get(position);
        HomeStoreViewHolder viewHolder = (HomeStoreViewHolder) holder;
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        ImageLoader.loadImage(viewHolder.mBinding.ivStore, storeRecommendBean.getPicture(), config, null);

        if (storeRecommendBean.getDistance() != null) {
            double d = Double.valueOf(storeRecommendBean.getDistance());
            viewHolder.mBinding.tvLocationDistance.setText(FormatKmUtil.FormatKmStr(d));
        }

        viewHolder.mBinding.tvName.setText(storeRecommendBean.getStoreName());
        viewHolder.mBinding.tvAddress.setText(storeRecommendBean.getLocation());
        viewHolder.mBinding.tvServiceType.setText(storeRecommendBean.getServerType());
        viewHolder.mBinding.ratingBar.setOnTouchListener((v, event) -> true);
        viewHolder.mBinding.ratingBar.setRating(storeRecommendBean.getPoint());
        viewHolder.mBinding.tvGrade.setText(String.valueOf(storeRecommendBean.getPoint()));
        viewHolder.mBinding.tvOrderNumber.setText(String.format(context.getString(R.string.month_order_number),
                String.valueOf(storeRecommendBean.getMonthOrder())));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class HomeStoreViewHolder extends BaseViewHolder {

        ItemHomeStoreBinding mBinding;

        public HomeStoreViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
