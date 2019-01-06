package com.yiyue.user.module.mine.order.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.util.BitmapUtils;
import com.yl.core.component.image.ImageLoader;

/**
 * Created by zm on 2018/11/9.
 */
public class CommentPicAdapter extends BaseRecycleViewAdapter<String>{
    private LayoutInflater mLayoutInflater;

    public CommentPicAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PictureViewHolder(mLayoutInflater.inflate(R.layout.item_order_comment_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PictureViewHolder viewHolder = (PictureViewHolder) holder;
        String picPath = mDatas.get(position);
        if (picPath.startsWith("http")){
            ImageLoader.loadImage(viewHolder.mImageView, picPath);
        }else {
            viewHolder.mImageView.post(() ->
                    viewHolder.mImageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(picPath,
                        viewHolder.mImageView.getWidth(), viewHolder.mImageView.getHeight())));

        }
    }

    private class PictureViewHolder extends BaseViewHolder {
        private ImageView mImageView;

        public PictureViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pic);
        }
    }
}
