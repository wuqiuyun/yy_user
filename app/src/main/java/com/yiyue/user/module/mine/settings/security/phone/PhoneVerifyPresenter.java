package com.yiyue.user.module.mine.settings.security.phone;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.api.UserSmsApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.util.AccountValidatorUtil;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by lvlong on 2018/10/12.
 */
public class PhoneVerifyPresenter extends BasePresenter<PhoneVerifyView> {
    private static final long TOTAL_TIME = 60_000L;
    private static final long ONECE_TIME = 1000L;

    private CountDownTimer mCountDownTimer;
    private String code;

    /**
     * 检验手机号码
     * @param mobile
     * @param phoneCode
     * @param
     */
    public void checkCode(String mobile, String phoneCode) {
        if (TextUtils.isEmpty(mobile)) {
            getMvpView().showToast("手机号码不能为空");
            return;
        }
        if (mobile.length() != 11) {
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            getMvpView().showToast("验证码不能为空");
            return;
        }
        new UserSmsApi().checkCode(mobile,"5", phoneCode, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast(error.message);
            }
        });
    }

    /**
     * 获取验证码
     * @param mobile
     */
    public void getPhoneCode(String mobile) {
        if (TextUtils.isEmpty(mobile)){
            getMvpView().showToast("手机号码不能为空");
            return;
        }
        // 限制验证码获取
        startCountdownTime();
        new UserSmsApi().getPhoneCode(mobile,"5", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("验证码获取成功");
                code = (String) baseResponse.getData();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast(error.message);
            }
        });
    }

    /**
     * 开启倒计时
     */
    private void startCountdownTime() {
        if (null == mCountDownTimer) {
            mCountDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
                @Override
                public void onTick(long millisUntilFinished) {
                    getMvpView().updateCountdownTime(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    getMvpView().onCountdownFinish();
                }
            };
        }
        mCountDownTimer.start();
    }

    /**
     * 关闭倒计时
     */
    public void stopCountdownTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void onDetachMvpView() {
        super.onDetachMvpView();
        stopCountdownTime();
    }
}
