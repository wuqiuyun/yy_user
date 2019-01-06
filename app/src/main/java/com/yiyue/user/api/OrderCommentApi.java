package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.requestbody.OrderCommentRequestBody;
import com.yiyue.user.model.vo.result.GetOrderCommentResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/11/10.
 */
public class OrderCommentApi {
    private interface Api {
        @GET("/user-api/orderComment/getOrderComment")
        Observable<GetOrderCommentResult> getAllComment(@Query("orderId") String orderId);

        @POST("/user-api/orderComment/addOrderComment")
        Observable<BaseResponse> submitComment(@Body RequestBody requestBody);
    }

    private Api mApi;

    public OrderCommentApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取订单评价
     * @param orderId
     */
    public void getAllComment(String orderId, YLRxSubscriberHelper<GetOrderCommentResult> subscriberHelper) {
        mApi.getAllComment(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 提交评价
     * @param requestBody
     * @param subscriberHelper
     */
    public void submitComment(OrderCommentRequestBody requestBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.submitComment(new BaseRequestBody<>(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
