package com.yiyue.user.module.main;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AppInfoBean;

/**
 * Created by zm on 2018/9/10.
 */
public interface IMainView extends IBaseView{
    void showToast(String message);

    /**
     * 更新版本信息回调
     * @param infoBean
     */
    void onUpdateAppInfo(AppInfoBean infoBean);
}
