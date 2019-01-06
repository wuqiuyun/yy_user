package com.yiyue.user.api;

import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.CouponResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/10/15.
 */
public class CouponApi {

    public interface Api {
       //获取用户优惠劵
        @GET("/user-api/coupon/getCouponByUserId")
        Observable<CouponResult> getCouponByUserId(@Query("userId") String userId,@Query("stylistId") String stylistId,@Query("appointmentTime") String appointmentTime);
    }

    private Api mApi;

    public CouponApi() {
        mApi = YLRequestManager.getRequest(CouponApi.Api.class);
    }



    /**
     * 获取用户优惠劵
     */
    public void getCouponByUserId(String userId,String stylistId,String appointmentTime,YLRxSubscriberHelper<CouponResult> subscriberelper) {
        mApi.getCouponByUserId(userId,stylistId,appointmentTime)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }


}
