package com.yiyue.user.module.home.service.bundle;

import com.yiyue.user.api.BundleApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.PackageListResult;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/13.
 */

public class ServiceBundlePresenter extends BasePresenter<IServiceBundleView> {

    // 获取套餐列表
    public void getPackageByType(int page, String type) {
        new BundleApi().getPackageByType(page, type, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                if (null != result.getData() && result.getData().size() > 0) {
                    getMvpView().getBundleSuccess(result.getData());
                } else {
                    getMvpView().getBundleFail();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView()!=null)getMvpView().getBundleFail();
            }
        });
    }


}
