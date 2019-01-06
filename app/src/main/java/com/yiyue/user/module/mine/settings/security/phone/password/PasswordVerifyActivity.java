package com.yiyue.user.module.mine.settings.security.phone.password;

import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityPasswordVerifyBinding;
import com.yiyue.user.module.mine.settings.security.phone.update.UpdatePhoneActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

/**
 * 密码验证
 * Created by lvlong on 2018/10/12.
 */
@CreatePresenter(PasswordVerifyPresenter.class)
public class PasswordVerifyActivity extends BaseMvpAppCompatActivity<PasswordVerifyView , PasswordVerifyPresenter> 
        implements ClickHandler , PasswordVerifyView {

    ActivityPasswordVerifyBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_password_verify;
    }

    @Override
    protected void init() {

        mBinding = (ActivityPasswordVerifyBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();

    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> finish());

        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_next :        //下一步
                getMvpPresenter().checkPassword(mBinding.etPassword.getText().toString().trim());
                break;

        }

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void checkPasswordSuc() {
        startActivity(this , UpdatePhoneActivity.class);
        finish();
    }
}
