package com.yiyue.user.module.mine.settings.about;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AppInfoBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface IAboutView extends IBaseView {
    void getAppInfoSuc(AppInfoBean appInfoBean);
}
