package com.yiyue.user.module.mine.settings;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.ReCodeBean;

/**
 * Created by zm on 2018/9/29.
 */
public interface ISettingsView extends IBaseView{
    void changeNoticeSuc();

    void findReCodeSuc(ReCodeBean recode);
}
