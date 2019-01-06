package com.yiyue.user.module.mine.order.details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.amap.LocationPresenter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityOrderDetailsBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.model.vo.bean.OrderDetailsBean;
import com.yiyue.user.model.vo.bean.OrderRefundBean;
import com.yiyue.user.module.im.chat.ChatActivity;
import com.yiyue.user.module.mine.order.comment.CommentActivity;
import com.yiyue.user.module.mine.order.time.UpdateTimeActivity;
import com.yiyue.user.module.mine.wallet.buy.PayActivity;
import com.yiyue.user.util.BigDecimalUtils;
import com.yiyue.user.util.ColorUtil;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.MapSelectView;
import com.yiyue.user.widget.dialog.YLDialog;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

@CreatePresenter(OrderDetailsPresenter.class)
public class OrderDetailsActivity extends BaseMvpAppCompatActivity<IDetailsView, OrderDetailsPresenter>
        implements IDetailsView, ClickHandler, AMapLocationListener {
    private static final String EXTRAS_ORDER_ID = "order_id";

    private ActivityOrderDetailsBinding detailsBinding;
    private String orderId;

    OrderDetailsBean data;
    private LocationPresenter locationPresenter;

    private double latitude;
    private double longitude;
    private String address;

    private long SEND_REMIND_SPACE_TIME = 60 * 1000L; // 订单提交间隔时间
    private long SEND_REMIND_TIME = 0L; // 记录订单提交时间

    /**
     * start orderId
     *
     * @param context
     * @param orderId 订单id
     */
    public static void startActivity(Context context, @NonNull String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_ORDER_ID, orderId);
        startActivity(context, OrderDetailsActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(EXTRAS_ORDER_ID);
        }
        // orderId不能为空

        if (TextUtils.isEmpty(orderId)) {
            finish();
        }
    }

    private void initView() {
        EventBus.getDefault().register(this);

        detailsBinding = (ActivityOrderDetailsBinding) viewDataBinding;
        detailsBinding.setClick(this);
        // 返回
        detailsBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        // 定位
        detailsBinding.orderStore.setIconRightClickListener(v -> {
            if (data.getStoreLocation() == null) {
                ToastUtils.shortToast("门店位置异常.");
                new RxPermissions(this)
                        .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                        .subscribe(grant -> {
                            locationPresenter.startLocation(true);// 传true定位一次,不传2秒一次
                        });
                return;
            }
            // 选择地图
            MapSelectView mapView = (MapSelectView) BottomViewFactory.create(this, BottomViewFactory.Type.Map);
            if (latitude > 0 && longitude > 0) {
                mapView.setStartLocation(latitude, longitude, address);
            }
            mapView.setEndLocation(data.getStoreLocation().getLatitude(), data.getStoreLocation().getLongitude(), data.getStoreLocation().getLocation());
            mapView.showBottomView(true);
        });
    }

    private void loadData() {
        locationPresenter = new LocationPresenter(this);
        locationPresenter.setMapLocationListener(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    locationPresenter.startLocation(true);// 传true定位一次,不传2秒一次
                });

        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    private void setDrawableRight(TextView textView, Drawable drawableRight) {
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpPresenter().stopCountdownTime();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 加价支付成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.AddMoneyPaySuccess event) {
        loadData();
        // TODO 加价成功之后通知美发师
    }

    /**
     * 订单消息通知
     *
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewMessage message) {
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tell_phone: // 电话
                new RxPermissions(OrderDetailsActivity.this)
                        .request(Manifest.permission.CALL_PHONE)
                        .subscribe(grant -> {
                            if (grant) {
                                if (!TextUtils.isEmpty(data.getStylistMobile())) {
                                    PhoneUtil.toCallPhone(this, data.getStylistMobile());
                                }
                            } else {
                                showToast("您拒绝了拨打电话权限.");
                            }
                        });
                break;
            case R.id.btn_send_msg: // 消息
                ChatActivity.startFromZIxunActivity(this, data.getStylistImusername(), data.getStylistId(), data.getStylistNickname(), data.getStylistPath());
                break;

        }
    }

    @Override
    public void onGetOrderListSuccess(ArrayList<OrderBean> orderBeans) {

    }

    @Override
    public void onGetOrderListFail() {

    }

    @Override
    public void onGetOrderDetailsSuccess(OrderDetailsBean data) {
        this.data = data;
        // 倒计时
        if (data.getPendingTime() <= 0) {
            detailsBinding.tvOrderHint.setVisibility(View.GONE);
            getMvpPresenter().stopCountdownTime();
        } else {
            getMvpPresenter().startCountdownTime(data.getPendingTime() * 1000);
        }
        // 头像
        ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(detailsBinding.ivPhoto, data.getStylistPath(), headConfig, null);
        // 标签
        detailsBinding.tvLabel.setText(FormatUtil.Formatstring(data.getStylistLabel()));
        // 姓名
        detailsBinding.tvName.setText(FormatUtil.Formatstring(data.getStylistNickname()));
        // 性别
        setDrawableRight(detailsBinding.tvName, ContextCompat.getDrawable(this,
                data.getStylistGender() == 1 ? R.drawable.icon_boy : R.drawable.icon_girl));
        // 评分
        detailsBinding.ratingBar.setOnTouchListener((v, event) -> true);
        detailsBinding.ratingBar.setRating(data.getStylistScore());
        detailsBinding.tvRatingNum.setText(String.valueOf(data.getStylistScore()));
        // 服务项目
        detailsBinding.orderProject.setContentText(data.getOrdername());
        // 服务门店
        detailsBinding.orderStore.setContentText(data.getStoreName());
        // 预约时间
        detailsBinding.orderDateRese.setContentText(DateUtil.date2Str(data.getOrdertime(), DateUtil.FORMAT_YMDHM));
        detailsBinding.orderDateRese.setSubContentText(data.getDatename());
        // 原预约时间
        if (data.getOldordertime() > 0L) {
            detailsBinding.orderDatePromise.setVisibility(View.VISIBLE);
            detailsBinding.orderDatePromise.setContentText(DateUtil.date2Str(data.getOldordertime(), DateUtil.FORMAT_YMDHM));
        }
        // 开始时间
        if (data.getStarttime() > 0L) {
            detailsBinding.orderDateStart.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStart.setContentText(DateUtil.date2Str(data.getStarttime(), DateUtil.FORMAT_YMDHM));
        } else {
            detailsBinding.orderDateStart.setVisibility(View.GONE);
        }
        // 完成时间
        if (data.getEndtime() > 0L) {
            detailsBinding.orderDateStop.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStop.setContentText(DateUtil.date2Str(data.getEndtime(), DateUtil.FORMAT_YMDHM));
        } else {
            detailsBinding.orderDateStop.setVisibility(View.GONE);
        }
        // 取消时间
        if (data.getCanceltime() > 0L) {
            detailsBinding.orderDateStart.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStart.setTitleText("取消时间：");
            detailsBinding.orderDateStart.setContentText(DateUtil.date2Str(data.getCanceltime(), DateUtil.FORMAT_YMDHM));
        }
        // 退款时间
        if (data.getRefundtime() > 0L) {
            detailsBinding.orderDateStop.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStop.setTitleText("退款时间：");
            detailsBinding.orderDateStop.setContentText(DateUtil.date2Str(data.getRefundtime(), DateUtil.FORMAT_YMDHM));
        }
        // 订单编号
        detailsBinding.orderId.setContentText(data.getOrderno());
        // 订单金额
        detailsBinding.orderAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getOrderamount())));
        // 支付金额
        detailsBinding.payAmount.setTitleText(data.getStatus() == 1 ? "待付金额：" : "支付金额：");
        detailsBinding.payAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getPayamount())));
        // 订单备注
        detailsBinding.orderRemarks.setContentText(data.getRemark());
        // 优惠金额
        if (data.getServicemodel() == 2) { // 套餐券
            detailsBinding.couponAmount.setTitleText("支付方式：");
            detailsBinding.couponAmount.setContentText("套餐券");
            detailsBinding.payAmount.setVisibility(View.GONE);
        }else { // 正常订单
            detailsBinding.couponAmount.setTitleText("优惠金额：");
            detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB),"0"));
            switch (data.getCoupontype()) {
                case 1:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getCouponamount())));
                    detailsBinding.couponAmount.setSubContentText("(满减券)");
                    break;
                case 2:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB),
                            String.valueOf(BigDecimalUtils.formatRoundUp(data.getOrderamount() * (1 - data.getCouponamount() / 10), BigDecimalUtils.MONEY_POINT))));
                    detailsBinding.couponAmount.setSubContentText("(折扣券)");
                    break;
                case 3:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getCouponamount())));
                    detailsBinding.couponAmount.setSubContentText("(抵扣券)");
                    break;
            }
        }
        // 退款
        OrderRefundBean orderRefundBean = data.getOrderRefund();
        if (orderRefundBean != null) {
            detailsBinding.refundAmount.setVisibility(View.VISIBLE);
            detailsBinding.refundAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(orderRefundBean.getRefundamount())));
            detailsBinding.orderHandlingFee.setVisibility(View.VISIBLE);
            detailsBinding.orderHandlingFee.setContentText(String.format(getString(R.string.RMB), String.valueOf(orderRefundBean.getHandlingfee())));
            detailsBinding.orderHandlingFee.setSubContentText("（项目价格*5%）");
        }

        // 订单状态
        switch (data.getStatus()) {
            // ORDER_PAYMENT
            case 1:
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setText("取消订单");
                detailsBinding.btn0.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("取消订单提示")
                            .setMessage("当前订单未完成付款，是否确定取消该订单？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelOrder(this, orderId);
                            })
                            .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("去付款");
                detailsBinding.btn1.setOnClickListener(v -> {
                    CreateOrderBean createOrderBean = new CreateOrderBean();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.ISORDER, 1);
                    createOrderBean.setOrdername(data.getOrdername());
                    createOrderBean.setPayamount(data.getPayamount());
                    createOrderBean.setOrderno(data.getOrderno());
                    createOrderBean.setId(Integer.valueOf(data.getId()));
                    createOrderBean.setPackageId(data.getPackageId());
                    createOrderBean.setTimes(30 * 60 - (int) ((System.currentTimeMillis() - data.getCreatetime()) / 1000));
                    createOrderBean.setServicemodel(data.getServicemodel());
                    bundle.putParcelable(Constants.STYLIST_ORDER_RESULT, createOrderBean);
                    PayActivity.startActivity(this, PayActivity.class, bundle);
                });
                break;
            case 2:
                detailsBinding.llBottom.setVisibility(View.GONE);
                detailsBinding.tvOrderHint.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderHint.setText("该订单已失效.");
                break;
            // ORDER_UNFINISHED
            case 4: // 待服务
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setText("更改时间");
                detailsBinding.btn0.setOnClickListener(v -> {
                    UpdateTimeActivity.startActivity(this, data.getStylistId(), data.getStoreId(), orderId);
                });
                detailsBinding.btn1.setText("取消订单");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("取消订单提示")
                            .setMessage("预约时间开始前2小时以外，可无责任取消订单，是否确定取消该订单？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelOrder(this, orderId);
                            })
                            .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            case 6: // 修改预约时间
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("取消订单");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("取消订单提示")
                            .setMessage("预约时间开始前2小时以外，可无责任取消订单，是否确定取消该订单？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelOrder(this, orderId);

                            })
                            .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            case 7: // 服务中
            case 10:
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("完成服务");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("完成订单提示")
                            .setMessage("该订单已服务" + calTimeSpacing(data.getStarttime(), System.currentTimeMillis()) + "，请确认是否已完成该服务？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().completeOrder(this, orderId);
                            })
                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            case 9:
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("完成服务");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("完成订单提示")
                            .setMessage("该订单已服务" + calTimeSpacing(data.getStarttime(), System.currentTimeMillis()) + "，请确认是否已完成该服务？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().completeOrder(this, orderId);
                            })
                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            // ORDER_UNPROCESSED
            case 8: // 服务加价
                break;
            // ORDER_PASS
            case 3: // 付款完成
                detailsBinding.btn1.setText("取消订单");
                detailsBinding.btn0.setText("发送提醒");
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                // click
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("取消订单提示")
                            .setMessage("是否确认取消该订单？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelOrder(this, orderId);
                            })
                            .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                detailsBinding.btn0.setOnClickListener(v -> {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - SEND_REMIND_TIME < SEND_REMIND_SPACE_TIME) {
                        ToastUtils.shortToast("提醒已发送.");
                        return;
                    }
                    SEND_REMIND_TIME = currentTime; // 更新提醒发送时间
                    getMvpPresenter().remindStylist(this, orderId, "1");
                });
                break;
            case 14: // 用户取消订单申请中
                detailsBinding.btn0.setText("取消申请");
                detailsBinding.btn1.setText("发送提醒");
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                // click
                detailsBinding.btn1.setOnClickListener(v -> {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - SEND_REMIND_TIME < SEND_REMIND_SPACE_TIME) {
                        ToastUtils.shortToast("已通知美发师，请耐心等待！");
                        return;
                    }
                    SEND_REMIND_TIME = currentTime; // 更新提醒发送时间
                    getMvpPresenter().remindStylist(this, orderId, "2");
                });
                detailsBinding.btn0.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("取消申请提示")
                            .setMessage("请确认是否取消本次申请")
                            .setPositiveButton("确认", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelRequestOrder(this, orderId);
                            })
                            .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            // ORDER_COMPLET
            case 11: // 服务完成
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("评价");
                detailsBinding.btn1.setOnClickListener(v -> {
                    OrderBean orderBean = new OrderBean();
                    orderBean.setId(data.getId());
                    orderBean.setStylistNickname(data.getStylistNickname());
                    orderBean.setStylistPath(data.getStylistPath());
                    orderBean.setStoreName(data.getStoreName());
                    orderBean.setStorePath(data.getStorePath());
                    CommentActivity.startActivity(this, CommentActivity.COMMENT_EDIT, orderBean);
                });
                break;
            case 18: // 已评价
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setText("查看评价");
                detailsBinding.btn1.setOnClickListener(v -> {
                    OrderBean orderBean = new OrderBean();
                    orderBean.setId(data.getId());
                    orderBean.setStylistNickname(data.getStylistNickname());
                    orderBean.setStylistPath(data.getStylistPath());
                    orderBean.setStoreName(data.getStoreName());
                    orderBean.setStorePath(data.getStorePath());
                    CommentActivity.startActivity(this, CommentActivity.COMMENT_LOOK, orderBean);
                });
                break;
            case 5: // 订单已取消
            case 12:
            case 13:
            case 15:
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setBackgroundColor(ColorUtil.getColor(R.color.color_CCCCCC));
                detailsBinding.btn1.setEnabled(false);
                detailsBinding.btn1.setText("评价");
                break;
        }
    }

    /**
     * 计算时间间隔
     */
    private String calTimeSpacing(long startTime, long endTime) {
        long spacingTime = (endTime - startTime) / 1000;
        if (spacingTime <= 0) {
            return "0小时0分";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(spacingTime / 3600);
        sb.append("小时");
        sb.append(spacingTime / 60 - (spacingTime / 3600 * 60));
        sb.append("分");
        sb.append(spacingTime % 60);
        sb.append("秒");
        return sb.toString();
    }

    /**
     * 时间换算
     */
    private String pendingTimeStr(long pendingTime) {
        if (pendingTime <= 0) {
            return "0小时0分";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(pendingTime / 3600);
        sb.append("小时");
        sb.append(pendingTime / 60 - (pendingTime / 3600 * 60));
        sb.append("分");
        sb.append(pendingTime % 60);
        sb.append("秒");
        return sb.toString();
    }

    @Override
    public void onCompleteOrderSuccess() {
        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void updateCountdownTime(long time) {
        if (detailsBinding.tvOrderHint.getVisibility() != View.VISIBLE) {
            detailsBinding.tvOrderHint.setVisibility(View.VISIBLE);
            detailsBinding.tvOrderHint.bringToFront();
        }
        detailsBinding.tvOrderHint.setText("您需在 " + pendingTimeStr(time) + " 内处理该订单，超时将默认拒绝...");
    }

    @Override
    public void onCountdownFinish() {
        detailsBinding.tvOrderHint.setVisibility(View.GONE);
    }

    @Override
    public void onCancelOrderSuccess() {
        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void onAddMoneyAgreeSuccess() {
    }

    @Override
    public void onAddMoneyRefuseSuccess() {
        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void onRemindStylistSuccess() {
        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        latitude = aMapLocation.getLatitude();
        longitude = aMapLocation.getLongitude();
        address = aMapLocation.getAddress();
    }
}
