package com.yiyue.user.api;

import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.BannerResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by wqy on 2018/11/27.
 */

public class UserBannerApi {

    public interface Api {
        // 用户banner信息
        @GET("/user-api/userBanner/getBanner")
        Observable<BannerResult> getBanner();
    }

    private Api mApi;

    public UserBannerApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 用户banner信息
     *
     * @param subscriberHelper
     */
    public void getBanner(YLRxSubscriberHelper<BannerResult> subscriberHelper) {
        mApi.getBanner()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
