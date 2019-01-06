package com.yiyue.user.module.login.invitecode;

import android.text.TextUtils;

import com.yiyue.user.api.UserApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.helper.AccountManager;

/**
 * Created by lvlong on 2018/9/27.
 */
public class InviteCodePresenter extends BasePresenter<InviteCodeView> {


    /**
     * 提交邀请码
     * @param inviteCode 邀请码
     */
    public void submitInviteCode(String inviteCode) {
        if (TextUtils.isEmpty(inviteCode)) {
            getMvpView().showToast("邀请码不能为空");
            return;
        }

        new UserApi().inviteFriends(AccountManager.getInstance().getAccount().getId(),
                inviteCode, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {

                getMvpView().onInviteCodeSuccess();
            }
        });
    }
}
