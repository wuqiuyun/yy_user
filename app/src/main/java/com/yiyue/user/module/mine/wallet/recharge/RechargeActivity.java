package com.yiyue.user.module.mine.wallet.recharge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityRechargeBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.module.mine.wallet.recharge.pay.PayActivity;
import com.yiyue.user.util.ColorUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

/**
 * 充值
 * <p>
 * Create by zm on 2018/10/8
 */
@CreatePresenter(RechargePresenter.class)
public class RechargeActivity extends BaseMvpAppCompatActivity<IRechargeView, RechargePresenter>
    implements IRechargeView, ClickHandler{

    private ActivityRechargeBinding rechargeBinding;
    private double mMoney = 0.00;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        rechargeBinding = (ActivityRechargeBinding) viewDataBinding;
        rechargeBinding.setClick(this);
        // black
        rechargeBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ColorUtil.getColor(R.color.white));
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok: // 确定
                String stMoney = rechargeBinding.etAmount.getText().toString().trim();
                if (stMoney!=null&&!TextUtils.isEmpty(stMoney)){
                    mMoney = Double.parseDouble(stMoney);
                    if (mMoney<=0){
                        ToastUtils.shortToast("请输入大于0的金额");
                        return;
                    }else if (mMoney>2000){
                        ToastUtils.shortToast("请输入小于2000的金额");
                        return;
                    }
                }else {
                    ToastUtils.shortToast("金额输入不能为空");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.RECHARGE_MONEY,mMoney);
                PayActivity.startActivity(this, PayActivity.class, bundle);
                break;
        }
    }
}
