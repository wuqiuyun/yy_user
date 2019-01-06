package com.yiyue.user.module.mine.settings.security.applystatus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityApplyStatusBinding;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.module.mine.settings.security.certification.CertificationActivity;
import com.yl.core.util.StatusBarUtil;

/**
 * 认证结果页
 */
public class ApplyStatusActivity extends BaseMvpAppCompatActivity<IApplyStatusView, ApplyStatusPresenter>
        implements IApplyStatusView, ClickHandler {

    ActivityApplyStatusBinding mBinding;
    private int status;//审核状态

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_status;
    }

    @Override
    protected void init() {
        mBinding = (ActivityApplyStatusBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(view -> finish());
        StatusBarUtil.setLightMode(this);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            GetApplyStatusBean applyStatusBean = (GetApplyStatusBean) bundle.getSerializable("status");
            if (null != applyStatusBean) {
                status = applyStatusBean.getApplyStatus();
                mBinding.tvCardNo.setText(hideId(applyStatusBean.getCardNo()));
                mBinding.tvUserName.setText(hideName(applyStatusBean.getRealName()));
                switch (status) {
                    case 0:
                        mBinding.llTip.setVisibility(View.VISIBLE);
                        mBinding.btnAuth.setText(getString(R.string.apply_status_0));
                        
                        break;
                    case 1:
                        mBinding.llTip.setVisibility(View.GONE);
                        mBinding.btnAuth.setText(getString(R.string.apply_status_1));
                        mBinding.btnAuth.setOnClickListener(view -> finish());
                        break;
                    case -1:
                        mBinding.llTip.setVisibility(View.GONE);
                        mBinding.btnAuth.setText(getString(R.string.apply_status_x));
                        mBinding.btnAuth.setOnClickListener(view -> {
                            CertificationActivity.startActivity(ApplyStatusActivity.this, CertificationActivity.class);
                            finish();
                        });
                        break;
                }
                
                mBinding.setClick(this);
            } else {
                showToast("获取认证信息失败");
                finish();
            }
        } else {
            showToast("获取认证信息失败");
            finish();
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    private String hideId(String id) {
        if (!TextUtils.isEmpty(id) && id.length() > 14) {
            StringBuilder stringBuilder = new StringBuilder(id);
            stringBuilder.replace(4, 14, "********");
            return stringBuilder.toString();
        } else {
            return id;
        }
    }

    private String hideName(String name) {
        if (!TextUtils.isEmpty(name) && name.length() > 1) {
            StringBuilder stringBuilder = new StringBuilder(name);
            stringBuilder.replace(0, 1, "*");
            return stringBuilder.toString();
        } else {
            return name;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_auth:
                switch (status) {
                    case 0:
                    case 1:
                        finish();
                        break;
                    case -1:
                        CertificationActivity.startActivity(ApplyStatusActivity.this, CertificationActivity.class);
                        finish();
                        break;
                }
        }
    }
}
