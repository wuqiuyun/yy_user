package com.yiyue.user.module.login.invitecode.two;

import android.annotation.SuppressLint;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityInviteCode2Binding;
import com.yiyue.user.model.local.preferences.CommonSharedPreferences;
import com.yiyue.user.module.login.information.PerfectInformationActivity;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.util.StatusBarUtil;

public class InviteCode2Activity extends BaseAppCompatActivity implements ClickHandler{

    ActivityInviteCode2Binding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_code2;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void init() {

        mBinding = (ActivityInviteCode2Binding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.tvRegisterReward.setText(FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getInviteReward()));
        mBinding.tvDaibi.setText(FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getRegisterReward()));

        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_invite_ok:    //确定
                startActivity(this , PerfectInformationActivity.class);
                finish();
                break;

        }
    }
}
