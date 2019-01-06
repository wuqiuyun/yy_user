package com.yiyue.user.module.mine.settings.security;

import android.content.Context;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.api.UserAuthApplyApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetApplyStatusResult;
import com.yiyue.user.model.vo.result.SecurityInfoResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by lvlong on 2018/10/8.
 */
public class SecurityPresenter extends BasePresenter<ISecurityView> {
    //获取账户安全详情
    public void getAccountInfoAccount() {
        new SettingsApi().getAccountSafeInfo(new YLRxSubscriberHelper<SecurityInfoResult>() {
            @Override
            public void _onNext(SecurityInfoResult CoinInfoResult) {
                getMvpView().onAccountInfoSuccess(CoinInfoResult.getData());
            }
        });
    }

    //微信绑定
    public void bindWXAccount(String code, Context context) {
        new SettingsApi().bindWXAccount(code, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().onBindWxSuccess();
            }
        });
    }
    
    //获取用户实名认证状态
    public void getApplyStatus(Context context){
        new UserAuthApplyApi().getApplyStatus(new YLRxSubscriberHelper<GetApplyStatusResult>(context,true) {
            @Override
            public void _onNext(GetApplyStatusResult getApplyStatusResult) {
                if (getApplyStatusResult.getData() != null){
                    getMvpView().getApplyStatusSuc(getApplyStatusResult.getData());
                }
                else {
                    getMvpView().getApplyStatusFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().getApplyStatusFail();
            }
        });
    }

    //支付密码状态
    public void initPayWord(Context context) {
        new SettingsApi().initPayWord(new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().oninitPayWordInfoSuccess(CoinInfoResult.getData()+"");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
