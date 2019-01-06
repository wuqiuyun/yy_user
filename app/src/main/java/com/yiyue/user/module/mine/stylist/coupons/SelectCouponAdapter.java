package com.yiyue.user.module.mine.stylist.coupons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

import static com.yiyue.user.model.constant.MineCouponStatus.COUPON_DEDUCTION;
import static com.yiyue.user.model.constant.MineCouponStatus.COUPON_DISCOUNT;
import static com.yiyue.user.model.constant.MineCouponStatus.COUPON_FULL_REDUCTION;
import static com.yiyue.user.model.constant.MineCouponStatus.COUPON_PACKAGE;

/**
 * Created by Administrator on 2018/11/4.
 */

public class SelectCouponAdapter extends BaseRecycleViewAdapter<CouponBean> {

    private LayoutInflater inflater;
    private Context context;

    public SelectCouponAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_coupon_common, parent, false);
        return new SelectCouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectCouponViewHolder couponCommonViewHolder = (SelectCouponViewHolder) holder;
        CouponBean couponBean = mDatas.get(position);

        String deduction = FormatUtil.Formatstring(couponBean.getDeduction());//金额
        String direction = couponBean.getDirection() == 1 ? "平台" : couponBean.getStylistName();
        direction = "仅限" + direction + "可用";

        String validstart = "";//开始日期
        String validend = "";//结束日期
        if (couponBean.getValidstart() != null) {
            String[] split = couponBean.getValidstart().split("T");
            validstart = split[0];
        }
        if (couponBean.getValidend() != null) {
            String[] split = couponBean.getValidend().split("T");
            validend = split[0];
        }
        couponCommonViewHolder.tv_deduction.setText(deduction);
        couponCommonViewHolder.tv_direction.setText(direction);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.FIT_CENTER)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.logo_02)
                .setErrorResId(R.drawable.logo_02)
                .build();
        ImageLoader.loadImage(couponCommonViewHolder.iv_photo, couponBean.getStylistPhoto(), config, null);
                switch (couponBean.getType()-1) {
                    case COUPON_FULL_REDUCTION:
                        couponCommonViewHolder.tv_rmb.setVisibility(View.VISIBLE);
                        couponCommonViewHolder.tv_validtime.setText(String.format(context.getString(R.string.coupon_reduction_date),validstart,validend));
                        String amountSum = String.format(context.getString(R.string.coupon_amount2), String.valueOf(couponBean.getAmount()), deduction);
                        couponCommonViewHolder.tv_amount_sum.setText(amountSum);
                        break;
                    case COUPON_DISCOUNT:
                        couponCommonViewHolder.tv_amount_sum.setText(context.getString(R.string.item_discount));
                        if (!TextUtils.isEmpty(couponBean.getUsestart()) && !TextUtils.isEmpty(couponBean.getUseend())) {
//                            couponBean.setUsestart(couponBean.getUsestart().substring(0, 4));
//                            couponBean.setUseend(couponBean.getUseend().substring(0, 4));
                            couponCommonViewHolder.tv_validtime.setText(String.format(context.getString(R.string.item_discount_date),validend,couponBean.getUsestart(),couponBean.getUseend()));
                        }
                        break;
                    case COUPON_DEDUCTION:
                        couponCommonViewHolder.tv_rmb.setVisibility(View.VISIBLE);
                        couponCommonViewHolder.tv_validtime.setText(String.format(context.getString(R.string.item_deduction_date),validend));
                        couponCommonViewHolder.tv_direction.setText("平台通用");
                        couponCommonViewHolder.tv_amount_sum.setText(context.getString(R.string.item_coupon_deduction));
                        break;
                    case COUPON_PACKAGE:
                        if (couponBean.getServicePackage()!=null){
                            String type = couponBean.getServicePackage().getType() == 1 ? "单项套餐" : "多项套餐";//类型
                            couponCommonViewHolder.tv_amount_sum.setText("次"+type);
                        }
                        String content = couponBean.getServiceName();//内容
                        String times = couponBean.getStocktimes();//次数
                        couponCommonViewHolder.tv_deduction.setText(times);
                        couponCommonViewHolder.tv_validtime.setText(String.format(context.getString(R.string.coupon_package_content),content));
                        break;
                }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class SelectCouponViewHolder extends BaseViewHolder {
        private TextView tv_validtime;
        private TextView tv_deduction;
        private TextView tv_direction;
        private TextView tv_amount_sum;
        private ImageView iv_photo;
        private TableRow tr_price;
        private TextView tv_rmb;
        private TextView tv_package_price;
        private TextView tv_original_price;

        public SelectCouponViewHolder(View itemView) {
            super(itemView);
            tv_validtime = itemView.findViewById(R.id.tv_validtime);
            tv_deduction = itemView.findViewById(R.id.tv_deduction);
            tv_direction = itemView.findViewById(R.id.tv_direction);
            tv_amount_sum = itemView.findViewById(R.id.tv_amount_sum);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tr_price = itemView.findViewById(R.id.tr_price);
            tv_rmb = itemView.findViewById(R.id.tv_rmb);
            tv_package_price = itemView.findViewById(R.id.tv_package_price);
            tv_original_price = itemView.findViewById(R.id.tv_original_price);

        }
    }
}
