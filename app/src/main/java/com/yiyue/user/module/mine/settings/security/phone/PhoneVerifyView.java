package com.yiyue.user.module.mine.settings.security.phone;

import com.yiyue.user.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/10/12.
 */
public interface PhoneVerifyView extends IBaseView {

    /**
     * 更新倒计时
     *
     * @param time
     */
    void updateCountdownTime(long time);

    /**
     * 倒计时结束
     */
    void onCountdownFinish();

    /**
     * 校验手机成功
     */
    void onSuccess();

}
