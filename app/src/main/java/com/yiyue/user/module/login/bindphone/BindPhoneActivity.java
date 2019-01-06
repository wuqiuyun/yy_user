package com.yiyue.user.module.login.bindphone;

import android.os.Bundle;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityBindphoneBinding;
import com.yiyue.user.helper.AppManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.login.invitecode.InviteCodeActivity;
import com.yiyue.user.module.main.MainActivity;
import com.yiyue.user.util.ColorUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

/**
 * 绑定手机号
 * <p>
 * Create by lyj on 2018/10/30
 */
@CreatePresenter(BindPhonePresenter.class)
public class BindPhoneActivity extends BaseMvpAppCompatActivity<IBindPhoneView, BindPhonePresenter>
        implements IBindPhoneView, ClickHandler{

    private ActivityBindphoneBinding mBinding;
    private String openid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            openid = bundle.getString(Constants.WECHAT_OPENID);
        }
        mBinding = (ActivityBindphoneBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightTextClickListener(v -> finish());
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
            case R.id.tv_get_code: // 获取验证码
                getMvpPresenter().getPhoneCode(mBinding.etPhone.getText().toString().trim());
                break;
            case R.id.btn_bind: // 确定绑定
                getMvpPresenter().mobileLogin("android",
                        mBinding.etPhone.getText().toString().trim(),
                        openid,
                        mBinding.etCode.getText().toString().trim(),2);
                break;
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onBindPhoneSuccess(UserBean userBean) {
        switch (userBean.getUserStatus()) {
            case 0: // 跳转至首页
                MainActivity.startActivity(this, MainActivity.class);
                finish();
                break;
            case 1: // 跳转至邀请码
                InviteCodeActivity.startActivity(this, InviteCodeActivity.class);
                finish();
                break;
        }
        DLog.e("************ BindPhoneActivity **************");
        AppManager.getAppManager().finishActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void updateCountdownTime(long time) {
        mBinding.tvGetCode.setEnabled(false); // 禁止再次点击
        mBinding.tvGetCode.setText(String.format(getString(R.string.login_get_code_time), time + ""));
    }

    @Override
    public void onCountdownFinish() {
        mBinding.tvGetCode.setText(getString(R.string.login_get_again));
        mBinding.tvGetCode.setEnabled(true);
    }
}
