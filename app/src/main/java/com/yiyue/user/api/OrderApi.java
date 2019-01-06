package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.model.vo.result.CreateOrderResult;
import com.yiyue.user.model.vo.result.GetOrderListResult;
import com.yiyue.user.model.vo.result.GetOrderResult;
import com.yiyue.user.model.vo.result.TimeManagerResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 订单Api
 * Created by zm on 2018/10/27.
 */
public class OrderApi {

    private interface Api {
        // 门店订单查询
        @GET("/user-api/order/findOrderPage")
        Observable<GetOrderListResult> getOrderPage(@QueryMap Map<String, Object> params);

        // 订单详情查询
        @GET("/user-api/order/findOrder")
        Observable<GetOrderResult> getOrder(@Query("orderId") String orderId);

        // 获取订单凭证
        @GET("/user-api/order/getOrderCertificate")
        Observable<BaseResponse> getOrderCertificate(@Query("orderId") String orderId, @Query("userId") String userId);

        // 获取美发师日期时间的可预约时间
        @GET("/user-api/order/getStylistDateTime")
        Observable<TimeManagerResult> getStylistDateTime(@Query("stylistId") String stylistId,
                                                    @Query("storeId") String storeId,
                                                    @Query("date") String date);

        // 取消订单
        @POST("/user-api/order/cancelOrder")
        Observable<BaseResponse> cancelOrder(@Body RequestBody requestBody);

        // 完成订单
        @POST("/user-api/order/completeOrder")
        Observable<BaseResponse> completeOrder(@Body RequestBody requestBody);

        // 创建订单
        @POST("/user-api/order/createOrder")
        Observable<CreateOrderResult> createOrder(@Body RequestBody requestBody);

        // 支付订单
        @POST("/user-api/order/payOrder")
        Observable<BaseResponse> payOrder(@Body RequestBody requestBody);

        // 取消申请取消订单
        @POST("/user-api/order/cancelRequestOrder")
        Observable<BaseResponse> cancelRequestOrder(@Body RequestBody requestBody);

        // 同意加价
         @POST("/user-api/order/addMoneyAgree")
         Observable<BaseResponse> addMoneyAgree(@Body RequestBody requestBody);

        // 拒绝加价
         @POST("/user-api/order/addMoneyRefuse")
         Observable<BaseResponse> addMoneyRefuse(@Body RequestBody requestBody);

        // 更新预约时间
         @POST("/user-api/order/updateOrderTime")
         Observable<BaseResponse> updateOrderTime(@Body RequestBody requestBody);

         // 发送提醒
        @GET("/user-api/order/remindStylist")
        Observable<BaseResponse> remindStylist(@Query("orderId") String orderId, @Query("status") String status);
        // 提交套餐订单
        @POST("/user-api/order/commitPackage")
        Observable<BaseResponse> commitPackage(@Body RequestBody requestBody);
    }

    private Api mApi;

    public OrderApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 发送提醒
     * @param orderId
     * @param status
     */
    public void remindStylist(String orderId, String status, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.remindStylist(orderId, status)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取订详情
     * @param orderId
     */
    public void getOrder(String orderId, YLRxSubscriberHelper<GetOrderResult> subscriberHelper) {
        mApi.getOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取订单列表
     * @param status
     * @param page
     * @param size
     * @param subscriberHelper
     */
    public void getOrderPage(int status, int page, int size, YLRxSubscriberHelper<GetOrderListResult> subscriberHelper) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("pageNo", page);
        params.put("pageSize", size);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getOrderPage(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 完成订单
     * @param orderId
     * @param subscriberHelper
     */
    public void completeOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        mApi.completeOrder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 取消订单
     * @param orderId
     * @param subscriberHelper
     */
    public void cancelOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        mApi.cancelOrder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 同意加价
     * @param orderId
     * @param subscriberHelper
     */
    public void addMoneyAgree(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        mApi.addMoneyAgree(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 拒绝加价
     * @param orderId
     * @param subscriberHelper
     */
    public void addMoneyRefuse(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        mApi.addMoneyRefuse(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 创建订单
     * @param requestBody
     * @param subscriberHelper
     */
    public void createOrder(CreateOrderBody requestBody, YLRxSubscriberHelper<CreateOrderResult> subscriberHelper) {
        requestBody.setIsResult(null);
        mApi.createOrder(new BaseRequestBody<>(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 申请取消申请取消订单
     * @param orderId
     * @param subscriberHelper
     */
    public void cancelRequestOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        mApi.cancelRequestOrder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 更新订单时间
     * @param day
     * @param orderId
     * @param usetime
     * @param subscriberHelper
     */
    public void updateOrderTime(String day, String orderId, String usetime, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("day", day);
        params.put("usetime", usetime);
        mApi.updateOrderTime(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 支付订单
     * @param orderno 订单编号
     * @param subscriberHelper
     */
    public void payOrder(String orderno, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderno", orderno);
        mApi.payOrder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 获取订单凭证
     * @param orderId
     * @param subscriberHelper
     */
    public void getOrderCertificate(String orderId, String userId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.getOrderCertificate(orderId, userId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 获取美发师日期时间的可预约时间
     *
     * @param stylistId
     * @param storeId
     * @param date
     * @param subscriberHelper
     */
    public void getStylistDateTime(String stylistId, String storeId, String date, YLRxSubscriberHelper<TimeManagerResult> subscriberHelper) {
        mApi.getStylistDateTime(stylistId, storeId, date)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 申请取消申请取消订单
     * @param orderno
     * @param subscriberHelper
     */
    public void commitPackage(String orderno, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderno", orderno);
        mApi.commitPackage(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }
}
