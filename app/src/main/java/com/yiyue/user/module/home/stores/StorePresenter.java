package com.yiyue.user.module.home.stores;

import com.yiyue.user.api.AreaApi;
import com.yiyue.user.api.CategoryApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.AreaResult;
import com.yiyue.user.model.vo.result.ServerTypeResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by Administrator on 2018/11/3.
 */

public class StorePresenter extends BasePresenter<IStoreView> {
    //获取所有类目
    public void getAll() {
        new CategoryApi().getAll(new YLRxSubscriberHelper<ServerTypeResult>() {
            @Override
            public void _onNext(ServerTypeResult result) {
                getMvpView().getAllServerType(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //获取所有地区
    public void getArea() {
        new AreaApi().getArea(new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                getMvpView().getArea(result.getData());
            }
        });
    }

    //通过用户ID获取用户区域
    public void getAreaByUserId( String userId){
        new AreaApi().getAreaByUserId(userId, new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                if (null != result.getData() && result.getData().size() > 0) {
                    getMvpView().getArea(result.getData());
                } else {
                    getMvpView().showToast("定位区域失败!");
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().showToast("定位区域失败!");
            }
        });
    }
}
