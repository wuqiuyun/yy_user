package com.yiyue.user.module.mine.settings.about;

import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetAppInfoResult;

/**
 * Created by lvlong on 2018/10/8.
 */
public class AboutPresenter extends BasePresenter<IAboutView> {
    /**
     * 获取更新相关信息
     */
    public void getAppInfos(){
        new SettingsApi().getAppInfos(new YLRxSubscriberHelper<GetAppInfoResult>() {
            @Override
            public void _onNext(GetAppInfoResult getAppInfoResult) {
                if (null != getAppInfoResult.getData()) getMvpView().getAppInfoSuc(getAppInfoResult.getData());
            }
        });
    }
}
