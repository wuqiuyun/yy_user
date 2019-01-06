package com.yiyue.user.module.mine.coupon.usage;

import com.yiyue.user.api.PackageApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetPackageuseRecorderResult;
import com.yl.core.component.net.exception.ApiException;


/*
 * Create by lvlong on  2018/12/4
 */

public class UsagePresenter extends BasePresenter<UsageView> {

    public void getPackageUseRecorder(int page, int size) {
        new PackageApi().getPackageUseRecorder(page, size, new YLRxSubscriberHelper<GetPackageuseRecorderResult>() {
            @Override
            public void _onNext(GetPackageuseRecorderResult getPackageuseRecorderResult) {
                getMvpView().setData(getPackageuseRecorderResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onLoadFail();
            }
        });
    }
}
