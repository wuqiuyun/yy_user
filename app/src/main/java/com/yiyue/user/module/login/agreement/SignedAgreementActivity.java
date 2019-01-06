package com.yiyue.user.module.login.agreement;

import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivitySignedAgreementBinding;
import com.yl.core.util.StatusBarUtil;

/**
 * Created by lvlong on 2018/9/25.
 *
 * 签署协议页
 */
public class SignedAgreementActivity extends BaseMvpAppCompatActivity<SignedAgreementView,SignedAgreementPresenter> implements SignedAgreementView ,ClickHandler {

    private ActivitySignedAgreementBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_signed_agreement;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivitySignedAgreementBinding) viewDataBinding;
        mBinding.setClick(this);

        setTitles();
    }

    private void setTitles() {

        mBinding.titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_agree:        //同意协议
                finish();
                break;
        }
    }

    @Override
    public void showToast(String message) {

    }

}
