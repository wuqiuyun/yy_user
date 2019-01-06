package com.yiyue.user.module.mine.settings.security.cashaccount.unbankcard;

import android.content.Context;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by lyj on 2018/11/8.
 */
public class UnBankCardPresenter extends BasePresenter<UnBankCardView>{
    //解除绑定账户
    public void unbindAccount(String bindId, Context context) {
        new SettingsApi().unBind( bindId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().onUnBankSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }

        });
    }
}
