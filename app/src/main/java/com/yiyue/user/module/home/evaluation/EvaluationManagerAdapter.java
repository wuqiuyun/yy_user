package com.yiyue.user.module.home.evaluation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.databinding.ItemEvaluationManagerBinding;
import com.yiyue.user.model.vo.bean.EvaluationBean;
import com.yiyue.user.module.mine.order.comment.CommentActivity;
import com.yiyue.user.widget.PhotoView.PhotoViewActivity;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * 评价管理适配器
 * Created by lvlong on 2018/10/11.
 */
public class EvaluationManagerAdapter extends BaseRecycleViewAdapter<EvaluationBean> {

    private Context mContext;

    public EvaluationManagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public EvaluationManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_manager, parent, false);
        return new EvaluationManagerAdapter.EvaluationManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EvaluationManagerViewHolder viewHolder = (EvaluationManagerViewHolder) holder;
        ItemEvaluationManagerBinding mBinding = viewHolder.mBinding;
        if (getDatas().size() == 0) {
            return;
        }
        EvaluationBean bean = getDatas().get(position);
        RecyclerView photoRecycle = viewHolder.mBinding.photoRecycle;
        PhotoAdapter photoAdapter = new PhotoAdapter(mContext);
        photoRecycle.setLayoutManager(new GridLayoutManager(mContext, 3));
        if (photoRecycle.getItemDecorationCount() <= 0) {
            photoRecycle.addItemDecoration(new GridSpacingItemDecoration(3, 15, false));
        }
        photoRecycle.setAdapter(photoAdapter);
        if (null != bean.getImgPaths() && bean.getImgPaths().size() > 0) {
            photoAdapter.setDatas(bean.getImgPaths(), true);
        }

        photoAdapter.setItemListener(new SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, photoAdapter.getDatas());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(mContext, PhotoViewActivity.class, bundle);
            }
        });

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(mBinding.civHeadPhoto, bean.getHeadImg(), config, null);
        mBinding.materialRatingBar.setOnTouchListener((v, event) -> true);
        mBinding.materialRatingBar.setRating(bean.getLevel());
        mBinding.tvName.setText(bean.getNickname());
        mBinding.tvTime.setText(bean.getCreatetime());
        if (null != bean.getComment() && !TextUtils.isEmpty(bean.getComment().trim())) {
            mBinding.tvUserReply.setText(bean.getComment());
        } else {
            mBinding.tvUserReply.setText("这家伙很懒，什么也没有留下~");
        }

        if (!TextUtils.isEmpty(bean.getReply().trim())) {
            mBinding.llReply.setVisibility(View.VISIBLE);
            mBinding.tvReply.setText(bean.getReply());
        } else {
            mBinding.llReply.setVisibility(View.GONE);
        }
    }

    public class EvaluationManagerViewHolder extends BaseViewHolder {

        ItemEvaluationManagerBinding mBinding;

        public EvaluationManagerViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);

        }

    }


}
