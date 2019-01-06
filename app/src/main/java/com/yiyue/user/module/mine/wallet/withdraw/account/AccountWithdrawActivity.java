package com.yiyue.user.module.mine.wallet.withdraw.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.pay.PayHelper;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityAccountwithdrawBinding;
import com.yiyue.user.model.local.SharePreferencesUtils;
import com.yiyue.user.model.vo.bean.CashAliBean;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.module.mine.settings.security.cashaccount.CashAccountManageActivity;
import com.yiyue.user.module.mine.wallet.withdraw.WithdrawActivity;
import com.yiyue.user.util.ColorUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.component.toast.ToastUtil;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 提现账户选择
 * <p>
 * Create by zm on 2018/10/30.
 */
@CreatePresenter(AccountWithdrawPresenter.class)
public class AccountWithdrawActivity extends BaseMvpAppCompatActivity<IAccountWithdrawView, AccountWithdrawPresenter>
        implements IAccountWithdrawView, ClickHandler {
    ActivityAccountwithdrawBinding payBinding;


    private AccountWithdrawSelectionAdapter accountWithdrawSelectionAdapter;
    private ArrayList<CashAliBean> cashAliBeans = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_accountwithdraw;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setLightMode(AccountWithdrawActivity.this);
        payBinding = (ActivityAccountwithdrawBinding) viewDataBinding;
        payBinding.setClick(this);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
//            mMoney = bundle.getDouble(Constants.RECHARGE_MONEY);
//            payBinding.tvRechargeBalance.setText("￥"+mMoney);
        }

        // back
        payBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        payBinding.titleView.setRightImgClickListener(v -> {
            //添加银行卡等
            getMvpPresenter().getUserAuth(this);
        });

        payBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        payBinding.recycleView.addItemDecoration(new SpaceItemHorizontalDecoration(10));

        accountWithdrawSelectionAdapter = new AccountWithdrawSelectionAdapter(this);
        payBinding.recycleView.setAdapter(accountWithdrawSelectionAdapter);

        accountWithdrawSelectionAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < accountWithdrawSelectionAdapter.getDatas().size(); i++) {
                    CashAliBean storeBean = accountWithdrawSelectionAdapter.getDatas().get(i);
                    if (i == position) {
                        storeBean.setStatus(true);
                    } else {
                        storeBean.setStatus(false);
                    }
                }
                accountWithdrawSelectionAdapter.notifyDataSetChanged();

                Bundle bundle = new Bundle();
                bundle.putSerializable("selectAccount", accountWithdrawSelectionAdapter.getDatas().get(position));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
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
        }
    }

    @Override
    public void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean) {
        if (cashAliBean!=null&&cashAliBean.size()>0){
            cashAliBeans.clear();
            cashAliBeans.addAll(cashAliBean);
            accountWithdrawSelectionAdapter.getDatas().clear();
//            for (int i = 0; i < cashAliBean.size(); i++) {
//                cashAliBeans.add(new CashAliBean(false));
//                DLog.e("onextractBankAccountSuccess "+cashAliBean.size());
//            }
            accountWithdrawSelectionAdapter.setDatas(cashAliBeans, true);
//            accountWithdrawSelectionAdapter.getDatas().addAll(cashAliBean);
            accountWithdrawSelectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getUserInfoSuccess(GetApplyStatusBean getApplyStatusBean) {
        if (getApplyStatusBean!=null){
            String realName = getApplyStatusBean.getRealName();
            if (realName!=null&&!TextUtils.isEmpty(realName)){
                SharePreferencesUtils.getSharePreferencesUtils().setRealName(realName);
            }
            startActivity(this,CashAccountManageActivity.class);
        }else {
            ToastUtils.shortToast("实名认证未通过,无法添加");
        }
    }

    @Override
    public void getUserInfoFail() {
        ToastUtils.shortToast("获取实名认证状态失败");
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpPresenter().extractAccount("ALL",this);
    }
}
