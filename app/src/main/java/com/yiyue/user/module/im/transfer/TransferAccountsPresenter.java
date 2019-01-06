package com.yiyue.user.module.im.transfer;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.RedBagApi;
import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.api.WalletInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.CashInfoResult;
import com.yiyue.user.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public class TransferAccountsPresenter extends BasePresenter<TransferAccountsView> {
    public void sendTransferReq(String receiveUserId, String remark, String sendUserId, String sendamount, Activity activity) {
        if (TextUtils.isEmpty(receiveUserId)) {
            getMvpView().showToast("receiveUserId不能为空");
            return;
        }

        if (TextUtils.isEmpty(sendUserId)) {
            getMvpView().showToast("sendUserId不能为空");
            return;
        }

        if (TextUtils.isEmpty(sendamount)) {
            getMvpView().showToast("金额不能为空");
            return;
        }
        new RedBagApi().sendTransfer(receiveUserId, remark, sendUserId, sendamount, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult baseResponse) {
                getMvpView().requestSuccess(baseResponse);
            }
        });
    }

    //验证支付密码
    public void checkPayWord(String pwd,Context context) {
        if (TextUtils.isEmpty(pwd)) {
            getMvpView().showToast("支付密码不能为空");
            return;
        }
        new SettingsApi().checkPayWord(pwd,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().checkPasswordSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                getMvpView().showToast("支付支付密码验证失败");
            }
        });
    }

    //获取钱包余额
    public void getCashInfo(Context context) {
        new WalletInfoApi().getCashInfo(new YLRxSubscriberHelper<CashInfoResult>(context,true) {
            @Override
            public void _onNext(CashInfoResult cashInfoResult) {
                getMvpView().onGetCashInfoSuccess(cashInfoResult.getData());
            }
        });
    }


}