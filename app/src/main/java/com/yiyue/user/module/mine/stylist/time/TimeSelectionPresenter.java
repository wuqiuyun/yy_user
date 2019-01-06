package com.yiyue.user.module.mine.stylist.time;

import android.text.TextUtils;

import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.TimeManagerResult;

/**
 * Created by wqy on 2018/11/6.
 */

public class TimeSelectionPresenter extends BasePresenter<ITimeSelectionView> {
    public void getStylistDateTime(String stylistId, String storeId, String date) {
        if (TextUtils.isEmpty(stylistId) || TextUtils.isEmpty(storeId) || TextUtils.isEmpty(date)) {
            return;
        }
        new OrderApi().getStylistDateTime(stylistId, storeId, date, new YLRxSubscriberHelper<TimeManagerResult>() {
            @Override
            public void _onNext(TimeManagerResult baseResponse) {
                getMvpView().setDatas(baseResponse.getData());
            }
        });
    }
}
