package com.yiyue.user.module.mine.stylist.project;


import android.content.Context;

import com.yiyue.user.api.StylistServerApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.ServiceDetaillsResult;
import com.yiyue.user.model.vo.result.StylistServerResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/5.
 */

public class ProjectListPresenter extends BasePresenter<IProjectListView> {
    //美发师服务列表
    public void getStylistServer( String stylistId, String userId) {
        new StylistServerApi().getStylistServer(stylistId, userId, new YLRxSubscriberHelper<StylistServerResult>() {
            @Override
            public void _onNext(StylistServerResult result) {
                    getMvpView().onSuccess(result.getData());
            }
        });
    }
    //美发师服务列表-详情
    public void getStylistServerDetail(String serviceId, Context context) {
        new StylistServerApi().getStylistServerDetail(serviceId, new YLRxSubscriberHelper<ServiceDetaillsResult>() {
            @Override
            public void _onNext(ServiceDetaillsResult result) {
                    getMvpView().onGetStylistServerDetailSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
