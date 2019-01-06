package com.yiyue.user.module.mine.stylist.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemStylistCouponBinding;
import com.yiyue.user.databinding.ItemStylistCouponEndBinding;
import com.yiyue.user.databinding.ItemStylistCouponType1Binding;
import com.yiyue.user.databinding.ItemStylistCouponType1NoBinding;
import com.yiyue.user.databinding.ItemStylistCouponType1ReceivedBinding;
import com.yiyue.user.databinding.ItemStylistCouponType2Binding;
import com.yiyue.user.databinding.ItemStylistCouponType2NoBinding;
import com.yiyue.user.databinding.ItemStylistCouponType2ReceivedBinding;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;
import com.yiyue.user.util.DeviceUtils;
import com.yiyue.user.util.ScreenUtils;

import java.text.DecimalFormat;

/**
 * 优惠券适配器（美发师详情）
 * Created by wqy on 2018/11/5.
 */

public class StylistCouponAdapter extends BaseRecycleViewAdapter<StylistDetailsBean.CardCouponDTOs> {
    private Context mContext;
    private final int TYPE_1 = 1;//满减券
    private final int TYPE_1_NO = 2;//满减券（不可抢）
    private final int TYPE_2 = 3;//折扣券
    private final int TYPE_2_NO = 4;//折扣券（不可抢）
    private final int TYPE_1_received = 5;//满减券（已抢）
    private final int TYPE_2_received = 6;//折扣券（已抢）

    private LayoutInflater inflater;
    private int width;

    public StylistCouponAdapter(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.width = DeviceUtils.getWindowWidth(context);
    }

    @Override
    public int getItemViewType(int position) {
        StylistDetailsBean.CardCouponDTOs data = getDatas().get(position);
        // type 优惠券类型 1 满减 2 折扣  isDraw 是否已领取
        if ("1".equals(data.getType())) {
            if (data.getUsePercent() == 1) {
                return TYPE_1_NO;
            } else if (data.isDraw()) {
                return TYPE_1_received;
            } else {
                return TYPE_1;
            }

        } else if ("2".equals(data.getType())) {
            if (data.getUsePercent() == 1) {
                return TYPE_2_NO;
            } else if (data.isDraw()) {
                return TYPE_2_received;
            } else {
                return TYPE_2;
            }
        } else {
            return TYPE_2_NO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_1:
                viewHolder = new CardCouponViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_1, parent, false));
                break;
            case TYPE_1_NO:
                viewHolder = new CardCouponViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_1_no, parent, false));
                break;
            case TYPE_2:
                viewHolder = new CardCouponViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_2, parent, false));
                break;
            case TYPE_2_NO:
                viewHolder = new CardCouponViewHolder4(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_2_no, parent, false));
                break;
            case TYPE_1_received:
                viewHolder = new CardCouponViewHolder5(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_1_received, parent, false));
                break;
            case TYPE_2_received:
                viewHolder = new CardCouponViewHolder6(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_2_received, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getDatas().size() == 0) {
            return;
        }
        StylistDetailsBean.CardCouponDTOs data = getDatas().get(position);
        DecimalFormat df = new DecimalFormat("0.0");
        String percent = df.format(data.getUsePercent() * 100);
        switch (getItemViewType(position)) {
            case TYPE_1: // 待抢满减券
                CardCouponViewHolder1 viewHolder1 = (CardCouponViewHolder1) holder;
                viewHolder1.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder1.mBinding.tvLimited.setText(String.format(mContext.getString(R.string.coupon_amount), data.getAmount() + ""));
                viewHolder1.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent) + "%");
                viewHolder1.mBinding.pbDeduction.setProgress((int) (data.getUsePercent() * 100));
                break;

            case TYPE_1_NO: // 已抢光满减券
                CardCouponViewHolder2 viewHolder2 = (CardCouponViewHolder2) holder;
                viewHolder2.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder2.mBinding.tvLimited.setText(String.format(mContext.getString(R.string.coupon_amount), data.getAmount() + ""));
                viewHolder2.mBinding.pbDeduction.setProgress(100);
                break;

            case TYPE_2://待抢折扣券
                CardCouponViewHolder3 viewHolder3 = (CardCouponViewHolder3) holder;
                viewHolder3.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder3.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent + "%"));
                viewHolder3.mBinding.pbDeduction.setProgress((int) (data.getUsePercent() * 100));
                break;

            case TYPE_2_NO://已抢光折扣券
                CardCouponViewHolder4 viewHolder4 = (CardCouponViewHolder4) holder;
                viewHolder4.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder4.mBinding.pbDeduction.setProgress(100);
                break;

            case TYPE_1_received: // 已抢满减券
                CardCouponViewHolder5 viewHolder5 = (CardCouponViewHolder5) holder;
                viewHolder5.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder5.mBinding.tvLimited.setText(String.format(mContext.getString(R.string.coupon_amount), data.getAmount() + ""));
                viewHolder5.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent) + "%");
                viewHolder5.mBinding.pbDeduction.setProgress((int) (data.getUsePercent() * 100));
                break;

            case TYPE_2_received://已抢折扣券
                CardCouponViewHolder6 viewHolder6 = (CardCouponViewHolder6) holder;
                viewHolder6.mBinding.tvAmount.setText(data.getDeduction() + "");
                viewHolder6.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent + "%"));
                viewHolder6.mBinding.pbDeduction.setProgress((int) (data.getUsePercent() * 100));
                break;
        }
    }

    public class CardCouponViewHolder1 extends BaseViewHolder {

        private ItemStylistCouponType1Binding mBinding;

        public CardCouponViewHolder1(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder2 extends BaseViewHolder {

        private ItemStylistCouponType1NoBinding mBinding;

        public CardCouponViewHolder2(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder3 extends BaseViewHolder {

        private ItemStylistCouponType2Binding mBinding;

        public CardCouponViewHolder3(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder4 extends BaseViewHolder {

        private ItemStylistCouponType2NoBinding mBinding;

        public CardCouponViewHolder4(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder5 extends BaseViewHolder {

        private ItemStylistCouponType1ReceivedBinding mBinding;

        public CardCouponViewHolder5(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder6 extends BaseViewHolder {

        private ItemStylistCouponType2ReceivedBinding mBinding;

        public CardCouponViewHolder6(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
