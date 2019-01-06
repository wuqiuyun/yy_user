package com.yiyue.user.module.main;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetAppInfoResult;

/**
 * Created by zm on 2018/9/10.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    /**
     * 获取应用更新信息
     */
    public void getAppInfos() {
        new SettingsApi().getAppInfos(new YLRxSubscriberHelper<GetAppInfoResult>() {
            @Override
            public void _onNext(GetAppInfoResult getAppInfoResult) {
                getMvpView().onUpdateAppInfo(getAppInfoResult.getData());
            }
        });
    }
}
