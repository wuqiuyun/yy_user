package com.yiyue.user.module.mine.wallet.recharge.pay;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityPayBinding;
import com.yiyue.user.util.ColorUtil;

/**
 * 支付
 * <p>
 * Create by zm on 2018/10/9.
 */
@CreatePresenter(PayPresenter.class)
public class PayActivity extends BaseMvpAppCompatActivity<IPayView, PayPresenter>
        implements IPayView, ClickHandler {
    private static final int PAY_WECHAT = 0;
    private static final int ALIPAY = 1;

    ActivityPayBinding payBinding;

    private int payType = PAY_WECHAT; // 0微信支付 1支付宝

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        payBinding = (ActivityPayBinding) viewDataBinding;
        payBinding.setClick(this);
        // back
        payBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        // 默认选择微信支付
        paySelect(PAY_WECHAT);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ColorUtil.getColor(R.color.white));
    }

    private void paySelect(int payType) {
        this.payType = payType;
        payBinding.wechatpay.setRightIcon(ContextCompat.getDrawable(this,
                payType == PAY_WECHAT ? R.drawable.icon_selected : R.drawable.icon_normal));
        payBinding.alipay.setRightIcon(ContextCompat.getDrawable(this,
                payType == ALIPAY ? R.drawable.icon_selected : R.drawable.icon_normal));
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: // 提交
                break;
            case R.id.wechatpay: // 微信支付
                paySelect(PAY_WECHAT);
                break;
            case R.id.alipay: // 支付宝
                paySelect(ALIPAY);
                break;
        }
    }
}
