package com.yiyue.user.module.home.evaluation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemEvaluationPhotoBinding;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

import java.util.ArrayList;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/18 10:32
 *  @描述：    评价管理图片适配器
 */

class PhotoAdapter extends BaseRecycleViewAdapter<String> {

    private Context mContext;


    public PhotoAdapter(Context context) {
        mContext = context;

    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder viewHolder= (PhotoViewHolder) holder;
        ItemEvaluationPhotoBinding mBinding = viewHolder.mBinding;
        String picPath = getDatas().get(position);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(mBinding.ivPictrue, picPath, config, null);
    }

    public class PhotoViewHolder extends BaseViewHolder {

        ItemEvaluationPhotoBinding mBinding;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
