package com.yiyue.user.module.home.service.leaderboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemServiceLeaderBoardBinding;
import com.yiyue.user.model.vo.bean.daobean.RankBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * Created by wqy on 2018/11/2
 */

public class ServiceLeaderBoardAdapter extends BaseRecycleViewAdapter<RankBean> {

    private LayoutInflater mInflater;
    private Context context;

    public ServiceLeaderBoardAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeaderBoardViewHolder(mInflater.inflate(R.layout.item_service_leader_board, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RankBean bean = getDatas().get(position);
        LeaderBoardViewHolder viewHolder = (LeaderBoardViewHolder) holder;

        viewHolder.mBinding.tvPrice.setText(String.format(context.getString(R.string.price), bean.getPrice() + ""));
        viewHolder.mBinding.ratingBar.setRating(bean.getStarLevel());

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(viewHolder.mBinding.ivAvatar, bean.getHeadImg(), config, null);
        viewHolder.mBinding.tvNickname.setText(bean.getStylistName());
        viewHolder.mBinding.tvStoreName.setText(bean.getStoreName());
        viewHolder.mBinding.tvScore.setText(String.format(
                context.getString(R.string.score_value), bean.getStarLevel() + ""));
        // 排行前三设置成红色
        if (position < 3) {
            viewHolder.mBinding.tvRank.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            viewHolder.mBinding.tvRank.setTextColor(context.getResources().getColor(R.color.color_999999));
        }
        viewHolder.mBinding.tvRank.setText(position + 1 + "");

    }

    public class LeaderBoardViewHolder extends BaseViewHolder {
        private ItemServiceLeaderBoardBinding mBinding;

        public LeaderBoardViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
