package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.bean.ShowtimeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zm on 2018/12/17.
 */
public class StylistManagerApi {

    private interface Api {
        @GET("/user-api/stylistManage/getShowTime")
        Observable<BaseResponse<ShowtimeBean>> getShowTime();
    }

    private Api api;

    public StylistManagerApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    public void getShowTime(YLRxSubscriberHelper<BaseResponse<ShowtimeBean>> subscriberHelper) {
        api.getShowTime()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
