package com.yiyue.user.module.mine.settings.security.password;

import com.yiyue.user.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/10/12.
 */
public interface IUpdatePasswordView extends IBaseView {
    void upDatePasswordSuccess();
    void upDatePasswordFail(String s);
}
