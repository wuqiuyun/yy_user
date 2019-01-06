package com.yiyue.user.module.mine.stylist.project;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.ServiceDetailesBean;
import com.yiyue.user.model.vo.bean.StylistServerBean;

/**
 * Created by wqy on 2018/11/5.
 */

public interface IProjectListView extends IBaseView {
    void  onSuccess(StylistServerBean stylistServerBean);
    void  onGetStylistServerDetailSuccess(ServiceDetailesBean serviceDetailesBean);
}
