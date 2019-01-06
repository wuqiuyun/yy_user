package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.ServiceCouponResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/10/15.
 */
public class UserCouponApi {

    public interface Api {
        @GET("/user-api/userCoupon/getUserCouponByType")
        Observable<CouponResult> getUserCouponByType(@Query("type") String type, @Query("userId") String userId);

        @GET("/user-api/userCoupon/getUserPackageByUserId")
        Observable<CouponResult> getUserPackageByUserId(@Query("userId") String userId);

        // 获取平台优惠券 (首页-- 优惠券)
        @POST("/user-api/coupon/getCouponByType")
        Observable<CouponResult> getCouponByType(@Body RequestBody requestBody);

        // 领优惠券
        @POST("/user-api/coupon/drawCoupon")
        Observable<BaseResponse> drawCoupon(@Body RequestBody requestBody);
    }

    private Api mApi;

    public UserCouponApi() {
        mApi = YLRequestManager.getRequest(UserCouponApi.Api.class);
    }

    /**
     * 根据状态查询用户的优惠券列表
     *
     * @param type 优惠券类型 1 满减 2 折扣 3 抵扣券
     */
    public void getUserCouponByType(String type, String userId, YLRxSubscriberHelper<CouponResult> subscriberelper) {
        mApi.getUserCouponByType(type, userId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 查询优惠券列表
     */
    public void getUserPackageByUserId(String userId, YLRxSubscriberHelper<CouponResult> subscriberelper) {
        mApi.getUserPackageByUserId(userId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 获取平台优惠券接口
     *
     * @param couponId
     * @param type
     * @param userId
     * @param subscriberelper
     */
    public void getCouponByType(String couponId, String type, String userId, YLRxSubscriberHelper<CouponResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("couponId", couponId);
        params.put("type", type);
        params.put("userId", userId);
        mApi.getCouponByType(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 领优惠券
     *
     * @param couponId
     * @param type     优惠券类型1 满减 2 折扣
     */
    public void drawCoupon(String couponId, String type, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("couponId", couponId);
        params.put("type", type);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.drawCoupon(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }


}
