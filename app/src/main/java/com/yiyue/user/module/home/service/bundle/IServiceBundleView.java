package com.yiyue.user.module.home.service.bundle;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.bean.PackageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 2018/11/2.
 */

public interface IServiceBundleView extends IBaseView {

    void getBundleSuccess(ArrayList<CouponBean> packageBeans);

    void getBundleFail();
}
