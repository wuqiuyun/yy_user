package com.yiyue.user.module.mine.order.time;

import android.text.TextUtils;

import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.TimeManagerResult;

/**
 * Created by zm on 2018/11/12.
 */
public class TimeManagerPresenter extends BasePresenter<ITimeManagerView>{

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

    /**
     * 更新订单时间
     * @param orderId
     * @param day
     * @param usetime
     */
    public void updateOrderTime(String orderId, String day, String usetime) {
        new OrderApi().updateOrderTime(day, orderId, usetime, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateOrderTimeSuccess();
            }
        });
    }
}
