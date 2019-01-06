package com.yiyue.user.module.mine.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemOrderBinding;
import com.yiyue.user.model.constant.OrderStatus;
import com.yiyue.user.model.vo.bean.OrderAddMoneyBean;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.util.ColorUtil;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.util.DateUtil;

import static com.yiyue.user.model.constant.OrderStatus.ORDER_COMPLETE;
import static com.yiyue.user.model.constant.OrderStatus.ORDER_PAYMENT;
import static com.yiyue.user.model.constant.OrderStatus.ORDER_PASS;
import static com.yiyue.user.model.constant.OrderStatus.ORDER_UNFINISHED;
import static com.yiyue.user.model.constant.OrderStatus.ORDER_UNPROCESSED;

/**
 * Created by zm on 2018/9/20.
 */
public class OrderAdapter extends BaseRecycleViewAdapter<OrderBean> {
    private Context context;
    @OrderStatus.OrderType
    private int orderType; // 订单类型
    private OnOrderOperaListener mListener;

    public OrderAdapter(Context context, @OrderStatus.OrderType int orderType) {
        this.context = context;
        this.orderType = orderType;
    }

    public void setOrderOperaListener(OnOrderOperaListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemOrderBinding orderBinding = ((OrderViewHolder) holder).mOrderBinding;

        OrderBean orderBean = mDatas.get(position);
        // 头像
        ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(orderBinding.ivPhoto, orderBean.getStylistPath(), headConfig, null);

        // 订单编号
        orderBinding.orderId.setContentText(orderBean.getOrderno());
        // 用户昵称
        orderBinding.tvName.setText(FormatUtil.Formatstring(orderBean.getStylistNickname()));
        // 服务名店
        orderBinding.orderStore.setContentText(orderBean.getStoreName());
        // 服务项目
        orderBinding.orderName.setText(FormatUtil.Formatstring(orderBean.getOrdername()));
        // 优惠券
        switch (orderBean.getCoupontype()) {
            case 0:
                orderBinding.orderCoupon.setVisibility(View.GONE);
                break;
            case 1:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("满减券");
                break;
            case 2:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("折扣券");
                break;
            case 3:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("抵扣券");
                break;
        }
        // 订单金额
        if (orderBean.getServicemodel() == 2){
            orderBinding.orderAmount.setText(String.format(context.getString(R.string.RMB), orderBean.getOrderamount()+""));
        }else{
            orderBinding.orderAmount.setText(String.format(context.getString(R.string.RMB), orderBean.getPayamount() + ""));
        }

        // 加价
        OrderAddMoneyBean addMoneyBean = orderBean.getOrderAddMoney();
        orderBinding.orderPriceAdd.setText(addMoneyBean == null ? "" :  "+" + addMoneyBean.getAddmoney());

        switch (orderType) {
            case ORDER_PAYMENT:
            case ORDER_UNFINISHED:
                // 预约时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_reservation));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                orderBinding.orderDate.setSubContentText(orderBean.getDatename());
                break;

            case ORDER_UNPROCESSED:
                if (addMoneyBean != null) {
                    orderBinding.orderDate.setTitleText("加价原因：");
                    orderBinding.orderDate.setContentText(addMoneyBean.getAdddesc());
                }else {
                    // 预约时间
                    orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_reservation));
                    orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                    orderBinding.orderDate.setSubContentText(orderBean.getDatename());
                }

                break;

            case ORDER_PASS:
                // 预约时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_reservation));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                orderBinding.orderDate.setSubContentText(orderBean.getDatename());
                break;

            case ORDER_COMPLETE:
                // 预约时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_reservation));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                orderBinding.orderDate.setSubContentText(orderBean.getDatename());
                break;
        }

        // 订单状态
        switch (orderBean.getStatus()) {
            // ORDER_PAYMENT
            case 1:
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("待付款");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.red));
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn0.setText("取消订单");
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onCancelOrderClick(v, orderBean);
                });
                orderBinding.btn1.setVisibility(View.VISIBLE);
                orderBinding.btn1.setText("去付款");
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.goPayment(v, orderBean);
                });
                break;
            // ORDER_UNFINISHED
            case 4: // 待服务
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("待服务");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_FE5A19));

                orderBinding.btn0.setText("更改时间");
                orderBinding.btn1.setText("预约凭证");
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onUpdateDateClick(v, orderBean);
                });
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onOrderCertificateClick(v, orderBean);
                });
                break;

            case 6: // 修改预约时间
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.btn0.setVisibility(View.GONE);

                orderBinding.tvStatus.setText("待服务");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_FE5A19));

                orderBinding.btn1.setText("预约凭证");
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onOrderCertificateClick(v, orderBean);
                    }
                });
                break;
            case 7: // 服务中
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("服务中");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_FE5A19));

                orderBinding.btn0.setVisibility(View.GONE);
                orderBinding.btn1.setText("确认完成");
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onCompleteClick(v, orderBean);
                });
                break;
            case 9:
            case 10:
                orderBinding.llOrderDetails.setVisibility(View.GONE);
                break;
            // ORDER_UNPROCESSED
            case 8: // 申请加价
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("申请加价");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_28C8B5));

                orderBinding.btn0.setText("拒绝");
                orderBinding.btn1.setText("同意");
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onRefuseAddPriceClick(v, orderBean);
                });
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onAgreeAddPriceClick(v, orderBean);
                });
                break;
            // ORDER_PASS
            case 3: // 付款完成
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("待美发师接单");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_FE5A19));

                orderBinding.btn0.setText("取消订单");
                orderBinding.btn1.setText("发送提醒");
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onCancelOrderClick(v, orderBean);
                });
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onSendRemindClick(v, orderBean);
                });
                break;
            case 14: // 用户取消订单申请中
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.tvStatus.setText("取消订单待通过");
                orderBinding.tvStatus.setTextColor(ColorUtil.getColor(R.color.color_28C8B5));

                orderBinding.btn0.setText("取消申请");
                orderBinding.btn1.setText("发送提醒");
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn1.setVisibility(View.VISIBLE);
                // click
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onCancelApplyClick(v, orderBean);
                });
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onSendRemindClick(v, orderBean);
                });
                break;
            // ORDER_COMPLET
            case 11: // 服务完成
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.btn0.setVisibility(View.GONE);
                orderBinding.btn1.setText("评价");
                orderBinding.btn1.setVisibility(View.VISIBLE);
                orderBinding.btn1.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onCommentClick(v, orderBean);
                });
                break;
            case 18: // 已评价
                orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
                orderBinding.btn1.setVisibility(View.GONE);
                orderBinding.btn0.setText("查看评价");
                orderBinding.btn0.setVisibility(View.VISIBLE);
                orderBinding.btn0.setOnClickListener(v -> {
                    if (mListener != null)
                        mListener.onLookCommentClick(v, orderBean);
                });
                break;
            case  5: // 订单已取消
            case 12:
            case 15:
                orderBinding.llOrderDetails.setVisibility(View.GONE);
                orderBinding.orderAmount.setTextColor(ColorUtil.getColor(R.color.color_343434));
                orderBinding.orderDate.setContentText("已取消订单，扣除手续费：￥" + orderBean.getHandlingfee());
                break;

        }
    }

    public class OrderViewHolder extends BaseViewHolder {
        private ItemOrderBinding mOrderBinding;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mOrderBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface OnOrderOperaListener {
        // 确认完成
        void onCompleteClick(View view, OrderBean orderBean);

        // 更新时间
        void onUpdateDateClick(View view, OrderBean orderBean);

        // 拒绝
        void onRefuseAddPriceClick(View view, OrderBean orderBean);

        // 同意
        void onAgreeAddPriceClick(View view, OrderBean orderBean);

        // 预约凭证
        void onOrderCertificateClick(View view, OrderBean orderBean);

        // 取消订单
        void onCancelOrderClick(View view, OrderBean orderBean);

        // 取消申请
        void onCancelApplyClick(View view, OrderBean orderBean);

        // 发送提醒
        void onSendRemindClick(View view, OrderBean orderBean);

        // 评价
        void onCommentClick(View view, OrderBean orderBean);

        // 查看评价
        void onLookCommentClick(View view, OrderBean orderBean);

        // 支付
        void goPayment(View view, OrderBean orderBean);
    }

}
