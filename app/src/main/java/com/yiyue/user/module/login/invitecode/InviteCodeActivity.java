package com.yiyue.user.module.login.invitecode;


import android.annotation.SuppressLint;
import android.view.View;

import com.yiyue.user.model.local.SharePreferencesUtils;
import com.yiyue.user.model.local.preferences.CommonSharedPreferences;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityInviteCodeBinding;
import com.yiyue.user.module.login.information.PerfectInformationActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.module.login.invitecode.two.InviteCode2Activity;
import com.yl.core.util.StatusBarUtil;

/**
 * 邀请码（选填）
 */
@CreatePresenter(InviteCodePresenter.class)
public class InviteCodeActivity extends BaseMvpAppCompatActivity<InviteCodeView, InviteCodePresenter> implements ClickHandler, InviteCodeView {

    ActivityInviteCodeBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_code;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void init() {

        mBinding = (ActivityInviteCodeBinding) viewDataBinding;
        mBinding.setClick(this);

        setTitles();
        mBinding.tvInviteReward.setText(String.format(getString(R.string.reward_tips),
                FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getInviteReward()),
                FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getRegisterReward())));

        StatusBarUtil.setLightMode(this);
    }

    private void setTitles() {

        //跳过
        mBinding.titleView.setRightTextClickListener(view -> {

            startActivity(InviteCodeActivity.this, PerfectInformationActivity.class);
            finish();
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_invite_commit:    //提交 

                getMvpPresenter().submitInviteCode(mBinding.etInviteCode.getText().toString().trim());
                break;

        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onInviteCodeSuccess() {
        CommonSharedPreferences.getInstance().saveShowReward(true);
        InviteCode2Activity.startActivity(InviteCodeActivity.this, InviteCode2Activity.class);
        finish();
    }


}
