package com.yiyue.user.module.mine.wallet.withdraw.account;

import android.content.Context;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.api.UserAuthApplyApi;
import com.yiyue.user.api.WalletInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.CashAliBean;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.model.vo.result.CashAliResult;
import com.yiyue.user.model.vo.result.GetApplyStatusResult;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;


/**
 * Created by lyj on 2018/10/30.
 */
public class AccountWithdrawPresenter extends BasePresenter<IAccountWithdrawView> {

    //当前支付宝绑定账户
    public void extractAccount(String bindType, Context context) {
        new SettingsApi().extractAccount(bindType, new YLRxSubscriberHelper<CashAliResult>(context, true) {
            @Override
            public void _onNext(CashAliResult result) {
                ArrayList<CashAliBean> cashAliBeans = result.getData();
                getMvpView().onextractBankAccountSuccess(cashAliBeans);
            }

        });
    }

    //当前支付宝绑定账户
    public void getUserAuth( Context context) {
        new UserAuthApplyApi().getUserAuth( new YLRxSubscriberHelper<GetApplyStatusResult>(context, true) {
            @Override
            public void _onNext(GetApplyStatusResult result) {
                GetApplyStatusBean getApplyStatusBean = result.getData();
                getMvpView().getUserInfoSuccess(getApplyStatusBean);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getUserInfoFail();
            }
        });
    }

}