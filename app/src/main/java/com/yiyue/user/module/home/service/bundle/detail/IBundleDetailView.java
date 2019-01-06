package com.yiyue.user.module.home.service.bundle.detail;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.BundleDetailsBean;

/**
 * Created by wqy on 2018/11/5.
 */

public interface IBundleDetailView extends IBaseView {

    void getDetailsSuccess(BundleDetailsBean bundleDetailsBean);

}
