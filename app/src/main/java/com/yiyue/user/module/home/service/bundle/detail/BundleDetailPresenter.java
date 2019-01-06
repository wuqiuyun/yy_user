package com.yiyue.user.module.home.service.bundle.detail;

import com.yiyue.user.api.BundleApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.BundleDetailsResult;

/**
 * 套餐详情
 * Created by wqy on 2018/11/5.
 */

public class BundleDetailPresenter extends BasePresenter<IBundleDetailView> {

    /**
     * 套餐详情
     *
     * @param serviceId
     */
    public void getPackageByServiceId(String page, String type, String serviceId) {
        new BundleApi().getPackageByServiceId(page,type,serviceId, new YLRxSubscriberHelper<BundleDetailsResult>() {
            @Override
            public void _onNext(BundleDetailsResult bundleDetailsResult) {
                getMvpView().getDetailsSuccess(bundleDetailsResult.getData());
            }
        });
    }
}
