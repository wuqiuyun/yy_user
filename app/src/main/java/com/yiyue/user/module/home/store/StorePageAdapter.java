package com.yiyue.user.module.home.store;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.databinding.ItemEnvironmentListBinding;
import com.yiyue.user.databinding.ItemEvaluationPhotoBinding;
import com.yiyue.user.databinding.ItemWorksListBinding;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/11.
 */
public class StorePageAdapter extends PagerAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mStorePhotos;

    public StorePageAdapter(Context context, ArrayList<String> storePhotos) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mStorePhotos = storePhotos;
    }


    @Override
    public int getCount() {
        return mStorePhotos.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_environment_list, container, false);
        ItemEnvironmentListBinding binding = DataBindingUtil.bind(view);
        ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.FIT_CENTER)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        ImageLoader.loadImage(binding.ivWorks, mStorePhotos.get(position), headConfig, null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
