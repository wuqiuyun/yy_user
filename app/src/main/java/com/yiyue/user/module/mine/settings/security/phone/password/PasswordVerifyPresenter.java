package com.yiyue.user.module.mine.settings.security.phone.password;

import android.text.TextUtils;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;

/**
 * Created by lvlong on 2018/10/12.
 */
public class PasswordVerifyPresenter extends BasePresenter<PasswordVerifyView> {
    public void checkPassword(String pwd){
        if (TextUtils.isEmpty(pwd)){
            getMvpView().showToast("密码不可为空");
            return;
        }
        new SettingsApi().checkPassword(pwd, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().checkPasswordSuc();
            }
        });
    }
}
