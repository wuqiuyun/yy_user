package com.yiyue.user.module.mine.stylist.selectstore;

import com.yiyue.user.api.StylistServerApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.model.vo.result.ServiceDetaillsResult;
import com.yiyue.user.model.vo.result.StoreListResult;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/6.
 */

public class SelectStorePresenter extends BasePresenter<ISelectStoreView>{
    //获取美发师入驻的门店
    public void getStylistStore(String stylistId, String userId) {
        new StylistServerApi().getStylistStore(stylistId, userId, new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().onSuccess((ArrayList<StoreBean>) result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
