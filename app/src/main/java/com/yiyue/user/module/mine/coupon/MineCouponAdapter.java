package com.yiyue.user.module.mine.coupon;

import android.content.Context;
import android.os.Bundle;
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
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.home.stylist.StylistActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.module.mine.stylist.selectstore.SelectStoreActivity;
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

public class MineCouponAdapter extends BaseRecycleViewAdapter<CouponBean> {

    private LayoutInflater inflater;
    private int type; // 券类型
    private Context context;

    public MineCouponAdapter(Context context, int type) {
        this.type = type;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_coupon_common, parent, false);
        return new CouponCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CouponCommonViewHolder couponCommonViewHolder = (CouponCommonViewHolder) holder;
        CouponBean couponBean = mDatas.get(position);

        String deduction = FormatUtil.Formatstring(couponBean.getDeduction());//金额
        String direction = couponBean.getDirection() == 1 ? "平台" : couponBean.getStylistName();
        direction = "仅限" + direction + "可用";

        String validstart = "";//开始日期
        String validend = "";//结束日期
        if (couponBean.getValidstart() != null) {
            String[] split = couponBean.getValidstart().split(" ");
            validstart = split[0];
        }
        if (couponBean.getValidend() != null) {
            String[] split = couponBean.getValidend().split(" ");
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
                switch (type) {
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
                        couponCommonViewHolder.tv_amount_sum.setText(String.format(context.getString(R.string.item_coupon_deduction)));
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

    public void setButtonClick(View buttonClick, String stylistId, int drection, CouponBean couponBean) {
        buttonClick.setOnClickListener(view -> {
            if (drection == 1){//优惠卷所属平台 美发师列表
                StylistActivity.startActivity(context, StylistActivity.class);
            }else {//优惠卷所属美发师 美发师详情
                Bundle bundle = new Bundle();
                if (type==COUPON_PACKAGE){
                    //套餐卷
                    CreateOrderBody createOrderBody = new CreateOrderBody();
                    createOrderBody.setOptionId(null);
                    //此页面需提交数据
                    createOrderBody.setUserId(String.valueOf(couponBean.getUserId()));
                    createOrderBody.setServiceId(String.valueOf(couponBean.getServicePackage().getServiceId()));
                    createOrderBody.setOrderamount(couponBean.getServicePackage().getPrice());
                    createOrderBody.setServiceName(couponBean.getServiceName());
                    createOrderBody.setServicemodel("2");
                    createOrderBody.setStylistId(couponBean.getStylistId());
                    createOrderBody.setPackageId(String.valueOf(couponBean.getId()));
                    createOrderBody.setTimes(couponBean.getStocktimes());
                    bundle.putParcelable(Constants.STYLIST_ORDER_BODY,createOrderBody);
                    SelectStoreActivity.startActivity(context, SelectStoreActivity.class,bundle);
                }else {
                    bundle.putString(Constants.STYLISTId, stylistId);
                    StylistDetailsActivity.startActivity(context, StylistDetailsActivity.class, bundle);
                }
            }

        });
    }


    private class CouponCommonViewHolder extends BaseViewHolder {
        private TextView tv_validtime;
        private TextView tv_deduction;
        private TextView tv_direction;
        private TextView tv_amount_sum;
        private ImageView iv_photo;
        private TableRow tr_price;
        private TextView tv_rmb;
        private TextView tv_package_price;
        private TextView tv_original_price;

        public CouponCommonViewHolder(View itemView) {
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
