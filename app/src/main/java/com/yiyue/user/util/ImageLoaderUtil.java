package com.yiyue.user.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yiyue.user.R;
import com.yl.core.component.image.ImageLoaderConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wqy on 2018/11/27.
 */

public class ImageLoaderUtil extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        Glide.with(context).load(path).into(imageView);

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        com.yl.core.component.image.ImageLoader.loadImage(imageView, path + "", config, null);
    }
}
