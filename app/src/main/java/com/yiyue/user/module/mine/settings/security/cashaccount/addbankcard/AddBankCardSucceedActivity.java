package com.yiyue.user.module.mine.settings.security.cashaccount.addbankcard;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.databinding.ActivityAddcashaccountSucceedBinding;
import com.yl.core.util.StatusBarUtil;

/**
 * 绑定银行卡成功
 * <p>
 * Create by lyj on 2019/1/4
 */
public class AddBankCardSucceedActivity extends BaseAppCompatActivity {

    ActivityAddcashaccountSucceedBinding mBinding;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_addcashaccount_succeed;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityAddcashaccountSucceedBinding) viewDataBinding;
        mBinding.titleView.setRightTextClickListener(view -> {
            finish();
        });

    }

}
