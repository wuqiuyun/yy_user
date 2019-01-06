package com.yiyue.user.module.mine.order.certificate;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.component.qrcode.QRCodeEncoder;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityReserCertificateBinding;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.module.mine.order.details.OrderDetailsActivity;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 预约凭证
 * <p>
 * Create by zm on 2018/11/09
 */
public class OrderCertificateActivity extends BaseAppCompatActivity {
    private static final String EXTRAS_ORDER_DATA = "order_data";

    private OrderBean mOrderBean;
    private ActivityReserCertificateBinding mBinding;

    /**
     * @param context
     * @param orderBean
     */
    public static void startActivity(Context context, OrderBean orderBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRAS_ORDER_DATA,  orderBean);
        startActivity(context, OrderCertificateActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reser_certificate;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        StatusBarUtil.setLightMode(this);
        hasExtras();
        initView();
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mOrderBean = bundle.getParcelable(EXTRAS_ORDER_DATA);
        }
    }

    private void initView() {
        mBinding = (ActivityReserCertificateBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
    }

    private void setData() {
        if (mOrderBean == null) return;
        // 服务项目
        mBinding.tvProjectName.setText(FormatUtil.Formatstring(mOrderBean.getOrdername()));
        // 订单金额
        if (mOrderBean.getServicemodel() == 2){
            mBinding.tvAmount.setText(String.format(getString(R.string.RMB), mOrderBean.getOrderamount()+""));
        }else{
            mBinding.tvAmount.setText(String.format(getString(R.string.RMB), mOrderBean.getPayamount()+""));
        }

        // 服务门店
        mBinding.orderStore.setContentText(mOrderBean.getStoreName());
        // 服务时间
        mBinding.orderDate.setContentText(DateUtil.date2Str(mOrderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
        // 生成订单编号二维码
        if (!TextUtils.isEmpty(mOrderBean.getId())) {
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(this);
            qrCodeEncoder.createQrCode2ImageView(mOrderBean.getId(), mBinding.ivQrCode);
        }else {
            ToastUtils.shortToast("二维码生成失败.");
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.ServiceComplete message) {
        OrderDetailsActivity.startActivity(this, mOrderBean.getId());
        finish();
    }
}
