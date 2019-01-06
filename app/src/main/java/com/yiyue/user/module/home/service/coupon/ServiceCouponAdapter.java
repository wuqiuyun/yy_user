package com.yiyue.user.module.home.service.coupon;

import android.content.Context;
import android.graphics.Paint;
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

public class ServiceCouponAdapter extends BaseRecycleViewAdapter<CouponBean> {

    private LayoutInflater inflater;
    private int type; // 券类型
    private Context context;

    public ServiceCouponAdapter(Context context, int type) {
        this.type = type;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_coupon_common, parent, false);
        return new ServiceCouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceCouponViewHolder serviceCouponViewHolder = (ServiceCouponViewHolder) holder;
        CouponBean couponBean = mDatas.get(position);
        serviceCouponViewHolder.tv_rmb.setVisibility(View.GONE);
        String deduction = FormatUtil.Formatstring(couponBean.getDeduction());//金额
        String direction = couponBean.getDirection() == 1 ? "平台" : couponBean.getStylistName();
        direction = "仅限" + direction + "可用";

        String validstart = "";//开始日期
        String validend = "";//结束日期

        String usestart = "";//开始日期
        String useend = "";//结束日期

//        if (couponBean.getValidstart() != null) {
//            String[] split = couponBean.getValidstart().split("T");
//            validstart = split[0];
//        }
//        if (couponBean.getValidend() != null) {
//            String[] split = couponBean.getValidend().split("T");
//            validend = split[0];
//        }

        if (couponBean.getValidstart() != null) {
            String  validstarts = couponBean.getValidstart();
            validstart = validstarts.substring(0,10);
        }
        if (couponBean.getValidend() != null) {
            String  validends = couponBean.getValidend();
            validend = validends.substring(0,10);
        }

        usestart = couponBean.getUsestart();
        useend = couponBean.getUseend();

        serviceCouponViewHolder.tv_deduction.setText(deduction);
        serviceCouponViewHolder.tv_direction.setText(direction);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.FIT_CENTER)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(serviceCouponViewHolder.iv_photo, couponBean.getHeadportrait(), config, null);
                switch (type) {
                    case COUPON_FULL_REDUCTION:
                        serviceCouponViewHolder.tv_rmb.setVisibility(View.VISIBLE);
                        serviceCouponViewHolder.tv_validtime.setText(String.format(context.getString(R.string.coupon_reduction_date),validstart,validend));
                        String amountSum = String.format(context.getString(R.string.coupon_amount2), String.valueOf(couponBean.getAmount()), deduction);
                        serviceCouponViewHolder.tv_amount_sum.setText(amountSum);
                        break;
                    case COUPON_DISCOUNT:
                        serviceCouponViewHolder.tv_amount_sum.setText(context.getString(R.string.item_discount));
                        if (!TextUtils.isEmpty(usestart) && !TextUtils.isEmpty(useend)) {
//                            couponBean.setUsestart(couponBean.getUsestart().substring(0, 4));
//                            couponBean.setUseend(couponBean.getUseend().substring(0, 4));
                            serviceCouponViewHolder.tv_validtime.setText(String.format(context.getString(R.string.item_discount_date),validend,usestart,useend));
                        }
                        break;
                    case COUPON_DEDUCTION:
                        serviceCouponViewHolder.tv_rmb.setVisibility(View.VISIBLE);
                        serviceCouponViewHolder.tv_validtime.setText(String.format(context.getString(R.string.item_deduction_date),validend));
                        serviceCouponViewHolder.tv_direction.setText("平台通用");
                        serviceCouponViewHolder.tv_amount_sum.setText(context.getString(R.string.item_coupon_deduction));
                        break;
                    case COUPON_PACKAGE:
                        serviceCouponViewHolder.tr_price.setVisibility(View.VISIBLE);
                        serviceCouponViewHolder.tv_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                        String content = couponBean.getContent();//内容
                        String times = couponBean.getTimes();//次数
                        String price = couponBean.getPrice();
                        String costprice = couponBean.getCostprice();
                        serviceCouponViewHolder.tv_amount_sum.setText("次"+content);
                        serviceCouponViewHolder.tv_deduction.setText(times);
                        serviceCouponViewHolder.tv_direction.setText(String.format(context.getString(R.string.coupon_package_info),costprice,price,couponBean.getStylistName()));
                        serviceCouponViewHolder.tv_validtime.setText(String.format(context.getString(R.string.coupon_package_content2),couponBean.getCategoryname()));
                        serviceCouponViewHolder.tv_package_price.setText(String.format(context.getString(R.string.RMB),price));
                        serviceCouponViewHolder.tv_original_price.setText(String.format(context.getString(R.string.RMB),costprice));
                        break;
                }

    }

    public void setType(int type) {
        this.type = type;
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    private class ServiceCouponViewHolder extends BaseViewHolder {
        private TextView tv_validtime;
        private TextView tv_deduction;
        private TextView tv_direction;
        private TextView tv_amount_sum;
        private ImageView iv_photo;
        private TableRow tr_price;
        private TextView tv_rmb;
        private TextView tv_package_price;
        private TextView tv_original_price;

        public ServiceCouponViewHolder(View itemView) {
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
