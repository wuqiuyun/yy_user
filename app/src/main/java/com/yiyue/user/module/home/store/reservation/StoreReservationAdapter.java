package com.yiyue.user.module.home.store.reservation;

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
import com.yiyue.user.databinding.ItemStoreReservationBinding;
import com.yiyue.user.model.vo.bean.ChooseStylistBean;
import com.yl.core.component.image.ImageLoader;

/**
 * 预约
 * Created by wqy on 2018/11/9.
 */

public class StoreReservationAdapter extends BaseRecycleViewAdapter<ChooseStylistBean> {
    private LayoutInflater inflater;

    public StoreReservationAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReservationViewHolder(inflater.inflate(R.layout.item_store_reservation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemStoreReservationBinding mBinding = ((ReservationViewHolder) holder).reservationBinding;
        ChooseStylistBean bean = getDatas().get(position);
        // 理发师封面
        if (!TextUtils.isEmpty(bean.getStylistCover())) {
            ImageLoader.loadImage(mBinding.ivPic, bean.getStylistCover());
        }
        // 昵称
        mBinding.tvNickname.setText( bean.getNickName());
        // 门店名+职称
        mBinding.tvPosition.setText(bean.getStoreName() + bean.getPosition());
        // 评分 0-5
        mBinding.ratingBar.setOnTouchListener((v, event) -> true);
        mBinding.ratingBar.setRating(bean.getGrade());
        mBinding.tvGrade.setText(bean.getGrade() + "分");
        mBinding.cbStatus.setChecked(bean.isStatus());

    }

    public class ReservationViewHolder extends BaseViewHolder {
        ItemStoreReservationBinding reservationBinding;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            reservationBinding = DataBindingUtil.bind(itemView);
        }
    }
}
