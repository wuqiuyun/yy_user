package com.yiyue.user.module.mine.settings.security.paypassword;

import com.yiyue.user.base.mvp.IBaseView;

/**
 * Created by lyj on 2018/11/9.
 */
public interface PayPasswordView extends IBaseView {
    void upDatePasswordSuccess();
    void upDatePasswordFail(String s);
    void checkPasswordSuccess();
}
