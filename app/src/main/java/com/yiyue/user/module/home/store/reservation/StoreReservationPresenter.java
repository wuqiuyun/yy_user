package com.yiyue.user.module.home.store.reservation;

import com.yiyue.user.api.StoreManageApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.ChooseStylistResult;

/**
 * Created by wqy on 2018/11/9.
 */

public class StoreReservationPresenter extends BasePresenter<IStoreReservationView> {

    // 预约理发师列表
    public void chooseStylists(String storeId) {
        new StoreManageApi().chooseStylists(storeId, new YLRxSubscriberHelper<ChooseStylistResult>() {
            @Override
            public void _onNext(ChooseStylistResult chooseStylistResult) {
                getMvpView().getStylistsSuccess(chooseStylistResult);
            }
        });
    }
}
