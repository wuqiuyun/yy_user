package com.yiyue.user.module.mine.settings.security.phone.update;

import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityUpdatePhoneBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.module.mine.settings.security.SecurityActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

/**
 * Created by lvlong on 2018/10/12.
 * 更换手机号
 */
@CreatePresenter(UpdatePhonePresenter.class)
public class UpdatePhoneActivity extends BaseMvpAppCompatActivity<UpdatePhoneView , UpdatePhonePresenter> 
        implements ClickHandler , UpdatePhoneView {

    ActivityUpdatePhoneBinding mBinding;
    private String oldMobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void init() {

        mBinding = (ActivityUpdatePhoneBinding) viewDataBinding;
        mBinding.setClick(this);
        oldMobile = AccountManager.getInstance().getMobile();
        mBinding.tvCurrentPhone.setText(oldMobile);
        initView();

    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> finish());

        StatusBarUtil.setLightMode(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.tv_get_code :     //获取验证码

                getMvpPresenter().getPhoneCode(mBinding.etPhone.getText().toString().trim());

                break;

            case R.id.btn_complete :    //完成
                getMvpPresenter().updataPhone(oldMobile,
                        mBinding.etPhone.getText().toString().trim(),
                        mBinding.etCode.getText().toString().trim()
                );
                break;

        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
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

    @Override
    public void onUpdataPhoneSuccess() {
        AccountManager.getInstance().insertingLogout();
        finish();
    }
}
