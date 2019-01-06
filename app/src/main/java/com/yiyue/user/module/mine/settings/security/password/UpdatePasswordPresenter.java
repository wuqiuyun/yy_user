package com.yiyue.user.module.mine.settings.security.password;

import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by lvlong on 2018/10/12.
 */
public class UpdatePasswordPresenter extends BasePresenter<IUpdatePasswordView> {
    //变更账户密码（知道原密码）
    public void upDatePwd(String userId, String oldPassword ,String password ,String nextPassword , Context context) {
        if (TextUtils.isEmpty(oldPassword)) {
            getMvpView().showToast("旧密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().showToast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(nextPassword)) {
            getMvpView().showToast("重复密码项不能为空");
            return;
        }

        if (!password.equals(nextPassword)) {
            getMvpView().showToast("两次填写的密码不一致");
            return;
        }

        new SettingsApi().upDatePwd(userId, oldPassword,password,nextPassword, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().upDatePasswordSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().upDatePasswordFail(error.code+"");
            }
        });
    }
}
