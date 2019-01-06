package com.yiyue.user.module.mine.settings.security.phone.update;

import com.yiyue.user.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/10/12.
 */
public interface UpdatePhoneView extends IBaseView {
    /**
     *更新手机号成功
     */
    void onUpdataPhoneSuccess();


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
}
