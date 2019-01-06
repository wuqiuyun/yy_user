package com.yiyue.user.module.mine.wallet.buy;

import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.pay.PayHelper;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityBuyPayBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.MineCouponStatus;
import com.yiyue.user.model.vo.bean.AliPayBean;
import com.yiyue.user.model.vo.bean.BundleDetailsBean;
import com.yiyue.user.model.vo.bean.CashInfoBean;
import com.yiyue.user.model.vo.bean.CouponDetailsBean;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.model.vo.bean.OrderDetailsBean;
import com.yiyue.user.model.vo.bean.WeiXinPayBean;
import com.yiyue.user.module.mine.coupon.CouponActivity;
import com.yiyue.user.module.mine.order.comment.CommentActivity;
import com.yiyue.user.module.mine.order.details.OrderDetailsActivity;
import com.yiyue.user.module.mine.settings.security.paypassword.PayPasswordActivity;
import com.yiyue.user.module.mine.settings.security.paypassword.forgetpwd.ForgetPayPasswordActivity;
import com.yiyue.user.util.CacheActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.MathematicsUtils;
import com.yiyue.user.widget.dialog.BaseEasyDialog;
import com.yiyue.user.widget.dialog.EasyDialog;
import com.yiyue.user.widget.dialog.ViewConvertListener;
import com.yiyue.user.widget.dialog.ViewHolder;
import com.yiyue.user.widget.dialog.YLDialog;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.component.toast.ToastUtil;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zm on 2018/11/6.
 */
@CreatePresenter(PayPresenter.class)
public class PayActivity extends BaseMvpAppCompatActivity<IPayView, PayPresenter> implements IPayView, ClickHandler {
    private static final int PAY_WALLET = 0;
    private static final int PAY_WECHAT = 1;
    private static final int ALIPAY = 2;
    private static final int PACKAGEPAY = 3;

    private int payType = PAY_WECHAT; // 0 意约钱包 1微信支付 2支付宝
    private int payTypeOrder = 1;//1 订单支付, 2加价支付 ,3套餐支付,4当面付
    private String orderno = "";//订单编号
    private String goWebUrl;
    private boolean isGoAliPay = false;
    private ActivityBuyPayBinding payBinding;

    private CreateOrderBean createOrderBean;
    private BundleDetailsBean bundleDetailsBeans;
    private String money,  packageId, serviceId,ordernoStr;
    private String orderName;
    private String payAmount;
    private String orderNo;
    private String orderId;

    //倒计时
    private int recLen = 30 * 60;
    Timer timer = new Timer();
    private int mServicemodel;//普通/套餐

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_buy_pay;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            payTypeOrder = bundle.getInt(Constants.ISORDER);
            if (payTypeOrder==1){//订单支付
                this.createOrderBean = bundle.getParcelable(Constants.STYLIST_ORDER_RESULT);
                orderName = createOrderBean.getOrdername();
                payAmount = createOrderBean.getPayamount()+"";
                orderNo = createOrderBean.getOrderno();
                orderId = createOrderBean.getId()+"";
                mServicemodel = createOrderBean.getServicemodel();
                recLen = createOrderBean.getTimes();
                packageId = createOrderBean.getPackageId()+"";
            }else if (payTypeOrder==2){//2加价支付
                orderName = bundle.getString("orderName");
                payAmount = bundle.getString("payAmount");
                orderNo = bundle.getString("orderNo");
                orderId = bundle.getString("orderId");
            }else if (payTypeOrder==3){//购买套餐支付
                this.bundleDetailsBeans = (BundleDetailsBean) bundle.getSerializable(Constants.BUNDLEDETAIL);
                money = bundleDetailsBeans.getPrice()+"";
                packageId = bundleDetailsBeans.getPackageId()+"";
                serviceId = bundleDetailsBeans.getServiceId()+"";
            }else if (payTypeOrder==4){//当面付
                this.createOrderBean = bundle.getParcelable(Constants.STYLIST_ORDER_RESULT);
                orderName = createOrderBean.getOrdername();
                payAmount = createOrderBean.getPayamount()+"";
                orderNo = createOrderBean.getOrderno();
                orderId = createOrderBean.getId()+"";
                mServicemodel = createOrderBean.getServicemodel();
            }


        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        payBinding = (ActivityBuyPayBinding) viewDataBinding;
        payBinding.setClick(this);
        getMvpPresenter().getCashInfo(this);
        // back
        payBinding.titleView.setLeftClickListener(v -> {
            finish();
        });

        if (mServicemodel==2){
            getMvpPresenter().findPackage(packageId,this);
            //套餐卷支付,隐藏其他支付方式
            payBinding.llOrdinary.setVisibility(View.GONE);
            paySelect(PACKAGEPAY);
        }else {
            payBinding.packagepay.setVisibility(View.GONE);
            // 选择套餐支付
            paySelect(PAY_WALLET);
        }

        if (payTypeOrder==1||payTypeOrder==2||payTypeOrder==4){//订单支付
            payBinding.tvName.setText(orderName);
            payBinding.tvPrice.setText("￥"+payAmount+"");
        }else {
            payBinding.tvName.setText(bundleDetailsBeans.getContent());
            payBinding.tvPrice.setText("￥"+money);
        }
        if (payTypeOrder==1) {//订单支付
            payBinding.llPayTime.setVisibility(View.VISIBLE);
            timer.schedule(task, 1000, 1000);       // timeTask
        }else {
            payBinding.llPayTime.setVisibility(View.GONE);
        }

    }

    private void paySelect(int payType) {
        this.payType = payType;
        payBinding.walletPay.setRightIcon(ContextCompat.getDrawable(this,
                payType == PAY_WALLET ? R.drawable.icon_selected : R.drawable.icon_normal));
        payBinding.wechatpay.setRightIcon(ContextCompat.getDrawable(this,
                payType == PAY_WECHAT ? R.drawable.icon_selected : R.drawable.icon_normal));
        payBinding.alipay.setRightIcon(ContextCompat.getDrawable(this,
                payType == ALIPAY ? R.drawable.icon_selected : R.drawable.icon_normal));
        payBinding.packagepay.setRightIcon(ContextCompat.getDrawable(this,
                payType == PACKAGEPAY ? R.drawable.icon_selected : R.drawable.icon_normal));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: // 提交
                if (payType==PAY_WALLET){
                    getMvpPresenter().initPayWord(this);
                }else if (payType==PAY_WECHAT){
                    if (payTypeOrder==1||payTypeOrder==2||payTypeOrder==4){
                        if (payTypeOrder==4){
                            getMvpPresenter().postWxpay(orderNo,"1",this);
                        }else {
                            getMvpPresenter().postWxpay(orderNo,payTypeOrder+"",this);
                        }
                    }else {
                        getMvpPresenter().postPackagePayWxpay(money,  packageId, serviceId,this);
                    }

                }else if (payType==ALIPAY){
                    if (payTypeOrder==1||payTypeOrder==2||payTypeOrder==4){
                        if (payTypeOrder==4){
                            getMvpPresenter().postAlipay(orderNo,"1",this);
                        }else {
                            getMvpPresenter().postAlipay(orderNo,payTypeOrder+"",this);
                        }
                    }else {
                        getMvpPresenter().postPackagePayAlipay(money,  packageId, serviceId,this);
                    }
                }else  if(payType==PACKAGEPAY){
                    getMvpPresenter().initPayWord(this);
                }
                break;
            case R.id.wallet_pay: // 意约钱包
                paySelect(PAY_WALLET);
                break;
            case R.id.wechatpay: // 微信支付
                paySelect(PAY_WECHAT);
                break;
            case R.id.alipay: // 支付宝
                paySelect(ALIPAY);
                break;
            case R.id.packagepay: // 套餐支付
                paySelect(PACKAGEPAY);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onGetCashInfoSuccess(CashInfoBean bean) {
        try {
            if (!TextUtils.isEmpty(bean.getBalance())&&!TextUtils.isEmpty(bean.getFreezeBalance())){
                double mMoney = Double.parseDouble(bean.getBalance());
                double mMoney1 = Double.parseDouble(bean.getFreezeBalance());
                double mMoneyNow = MathematicsUtils.sub(mMoney, mMoney1);//可用余额
                payBinding.walletPay.setSubContentText("(￥"+ FormatUtil.FormatDouble(mMoneyNow)+")");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onAliPaySuccess(AliPayBean aliPayBean) {
        try {
            goWebUrl = aliPayBean.getPayurl();
            ordernoStr = aliPayBean.getOrderNo();
            if (!TextUtils.isEmpty(goWebUrl)){
                showGoPayDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAliPay1Success(AliPayBean aliPayBean) {
        try {
            goWebUrl = aliPayBean.getPayurl();
            ordernoStr = aliPayBean.getOrderNo();
            if (!TextUtils.isEmpty(goWebUrl)){
                showGoPayDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAliPayStatusSuccess() {//支付宝支付成功
        if (payTypeOrder==1) {
            if (timer!=null){
                timer.cancel();
            }
            OrderDetailsActivity.startActivity(PayActivity.this, String.valueOf(orderId));
            CacheActivity.finishActivity();
        }else if (payTypeOrder==2) {
            EventBus.getDefault().post(new EventBean.AddMoneyPaySuccess());
        }else if (payTypeOrder==3) {//支付优惠券成功
            CouponActivity.startActivity(this, MineCouponStatus.COUPON_PACKAGE);
        }else if (payTypeOrder==4) {//当面付支付成功
            getMvpPresenter().getOrderDetails(this,createOrderBean.getId()+"");
        }
        finish();
    }

    @Override
    public void onWxPaySuccess(WeiXinPayBean weiXinPay) {
        if(weiXinPay!=null){
            Gson gson = new Gson();
            Log.e("onWeChatCashSuccess","--------"+gson.toJson(weiXinPay));
            PayHelper.wxpay(this,gson.toJson(weiXinPay));
        }
    }

    @Override
    public void oninitPayWordInfoSuccess(String json) {
        //data=0(未设置)，data=1(已设置)。若未设置支付密码，弹窗跳转到设置支付密码页面
        Bundle bundle = new Bundle();
        Log.e("---------","-----"+json);
        if (json.equals("0")||json.equals("0.0")){//未设置支付密码
            bundle.putBoolean("isrepin", false);
            startActivity(this, PayPasswordActivity.class, bundle);
        }else {//已设置支付密码 直接支付
            showPayDialog();
        }
    }

    @Override
    public void onYiYuePaySuccess() {
        ToastUtils.shortToast("意约钱包支付成功");
        if (payTypeOrder==1) {
            if (timer!=null){
                timer.cancel();
            }
            OrderDetailsActivity.startActivity(PayActivity.this, String.valueOf(orderId));
            CacheActivity.finishActivity();
        }else if (payTypeOrder==2){
            EventBus.getDefault().post(new EventBean.AddMoneyPaySuccess());
        }else if (payTypeOrder==3) {//支付优惠券成功
            CouponActivity.startActivity(this, MineCouponStatus.COUPON_PACKAGE);
        }else if (payTypeOrder==4){//当面付支付成功
            getMvpPresenter().getOrderDetails(this,createOrderBean.getId()+"");
        }
        finish();
    }

    @Override
    public void checkPasswordSuccess() {//支付密码验证成功
        if (payTypeOrder==1||payTypeOrder==2||payTypeOrder==4) {
            if (mServicemodel==2){
                getMvpPresenter().commitPackage(orderNo,this);
            }else {
                if (payTypeOrder==4){
                    getMvpPresenter().yiyueOrderPay(orderNo,"1",this);
                }else {
                    getMvpPresenter().yiyueOrderPay(orderNo,payTypeOrder+"",this);
                }
            }

        }else {
            getMvpPresenter().packagePayYiyueOrderPay(money,  packageId, serviceId,this);
        }

    }

    @Override
    public void commitPackageSuccess() {
        OrderDetailsActivity.startActivity(PayActivity.this, String.valueOf(orderId));
        finish();
    }

    //获取订单详情,当面付后
    @Override
    public void onGetOrderDetailsSuccess(OrderDetailsBean data) {
        if (data!=null){
            //当面付成功后跳转到评论页面
            OrderBean orderBean = new OrderBean();
            orderBean.setId(data.getId());
            orderBean.setStylistNickname(data.getStylistNickname());
            orderBean.setStylistPath(data.getStylistPath());
            orderBean.setStoreName(data.getStoreName());
            orderBean.setStorePath(data.getStorePath());
            CommentActivity.startActivity(this, CommentActivity.COMMENT_EDIT, orderBean);
        }
        finish();
    }

    @Override
    public void onGetOrderDetailsFail() {
        finish();
    }

    //获取套餐券详情成功
    @Override
    public void onGetCouponDetailsSuccess(CouponDetailsBean data) {
        if (data!=null){
            payBinding.packagepay.setContentText(String.format(getString(R.string.package_umber) ,String.valueOf(data.getStocktimes())));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ToastUtils.shortToast("微信支付成功");
            if (payTypeOrder == 1) {
                if (timer!=null){
                    timer.cancel();
                }
                OrderDetailsActivity.startActivity(PayActivity.this,String.valueOf(orderId));
                CacheActivity.finishActivity();
                finish();
            }else if (payTypeOrder == 2) {// 加价支付成功
                EventBus.getDefault().post(new EventBean.AddMoneyPaySuccess());
            }else if (payTypeOrder == 3) {//支付优惠券成功
                CouponActivity.startActivity(this, MineCouponStatus.COUPON_PACKAGE);
            }else if (payTypeOrder == 4) {// 当面付支付成功
                getMvpPresenter().getOrderDetails(this,createOrderBean.getId()+"");
            }
            finish();
        }else {
            ToastUtils.shortToast("支付已取消");
        }
    }

    private void showGoPayDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("即将前往浏览器支付")
                .setPositiveButton("确定", (dialog, which) -> {
                    Uri uri = Uri.parse(goWebUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    isGoAliPay = true;
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGoAliPay){//支付宝支付成功后需要调接口查询是否支付成功,成功才能跳订单详情
            if (payTypeOrder==1||payTypeOrder==2||payTypeOrder==4){
                getMvpPresenter().alipayQuery(ordernoStr,this);
            }else {
                getMvpPresenter().alipayQuery(ordernoStr,this);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }

    private void showPayDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_input_paypassword)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.getView(R.id.ll_pay_cancle).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_pay_forget).setOnClickListener(v -> {
                            startActivity(PayActivity.this, ForgetPayPasswordActivity.class);
                            dialog.dismiss();
                        });
                        GridPasswordView pswView = (GridPasswordView) holder.getView(R.id.pswView);
                        pswView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
                            @Override
                            public void onTextChanged(String psw) {
                            }

                            @Override
                            public void onInputFinish(String psw) {
                                //输入法取消
                                Log.e("showPayDialog", "结束-输入的密码为: "+psw.toString().trim()+"");
                                String pwd = psw.toString().trim();
                                getMvpPresenter().checkPayWord(pwd,PayActivity.this);
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setPosition(Gravity.BOTTOM)
                .setMargin(0)
                .setOutCancel(true)
                .show(this.getSupportFragmentManager());
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    payBinding.tvPayTime.setText(ms2HMS(recLen)+"");
                    if(recLen < 0){
                        if (timer!=null){
                            timer.cancel();
                        }
                        payBinding.llPayTime.setVisibility(View.GONE);
                        ToastUtils.shortToast("订单超时请重新下单");
                        CacheActivity.finishActivity();
                        finish();
                    }
                }
            });
        }
    };

    public static String ms2HMS(int _ms){
        String HMStime;
        int mint=(_ms%3600)/60;
        int sed=_ms%60;

        String mintStr=String.valueOf(mint);
        if(mint<10){
            mintStr="0"+mintStr;
        }
        String sedStr=String.valueOf(sed);
        if(sed<10){
            sedStr="0"+sedStr;
        }
        HMStime=mintStr+"分"+sedStr+"秒";
        return HMStime;
    }

}
