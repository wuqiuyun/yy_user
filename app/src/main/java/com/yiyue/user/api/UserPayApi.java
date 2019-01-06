package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.AliPaysResult;
import com.yiyue.user.model.vo.result.CouponDetailsResult;
import com.yiyue.user.model.vo.result.CreateOrderResult;
import com.yiyue.user.model.vo.result.OpusDetailsResult;
import com.yiyue.user.model.vo.result.OpusResult;
import com.yiyue.user.model.vo.result.StylistDetailsResult;
import com.yiyue.user.model.vo.result.WeiXinPayResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 美发师名片
 * Created by lyj on 2018/11/9.
 */

public class UserPayApi {
    public interface Api {
        @POST("/user-api/order/alipay")
        Observable<AliPaysResult> postAlipay(@Body RequestBody requestBody);

        @POST("/user-api/order/wxpay")
        Observable<WeiXinPayResult> postWxpay(@Body RequestBody requestBody);

        @POST("/user-api/order/yiyueOrderPay")
        Observable<BaseResponse> yiyueOrderPay(@Body RequestBody requestBody);

        @GET("/user-api/order/ali/query")
        Observable<BaseResponse> alipayQuery(@Query("orderNo") String orderNo);


        @POST("/user-api/packagePay/alipay3")
        Observable<AliPaysResult> postPackagePayAlipay(@Body RequestBody requestBody);

        @POST("/user-api/packagePay/wxpay3")
        Observable<WeiXinPayResult> postPackagePayWxpay(@Body RequestBody requestBody);

        @POST("/user-api/packagePay/yiyuePackagePay")
        Observable<BaseResponse> packagePayYiyueOrderPay(@Body RequestBody requestBody);

        @POST("/user-api/tradePayorder/createTradePayOrder")
        Observable<CreateOrderResult> createTradePayOrder(@Body RequestBody requestBody);

        @GET("/user-api/order/findPackage")
        Observable<CouponDetailsResult> findPackage(@Query("userId") String userId,@Query("packageId") String packageId);

    }

    private Api mApi;

    public UserPayApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 支付宝支付
     *
     * orderno (string, optional),
     * type (integer, optional)
     */
    public void postAlipay(String orderno, String type, YLRxSubscriberHelper<AliPaysResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderno", orderno);
        params.put("type", type);
        mApi.postAlipay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 微信支付
     *
     * orderno (string, optional),
     * type (integer, optional)
     */
    public void postWxpay(String orderno, String type, YLRxSubscriberHelper<WeiXinPayResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderno", orderno);
        params.put("type", type);
        mApi.postWxpay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 意约钱包支付
     *
     * orderno (string, optional),
     * type (integer, optional)
     */
    public void yiyueOrderPay(String orderno, String type, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderno", orderno);
        params.put("type", type);
        mApi.yiyueOrderPay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 支付宝支付状态查询
     *
     * orderno (string, optional),
     */
    public void alipayQuery(String orderno, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.alipayQuery(orderno)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 套餐 支付宝支付
     *
     * money (number, optional): 支付金额 ,
     * packageId (integer, optional): 套餐ID ,
     * serviceId (integer, optional): 服务ID ,
     * userId (integer, optional): 用户ID
     */
    public void postPackagePayAlipay(String money, String packageId,String serviceId, YLRxSubscriberHelper<AliPaysResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("money", money);
        params.put("packageId", packageId);
        params.put("serviceId", serviceId);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.postPackagePayAlipay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 套餐 微信支付
     *
     * money (number, optional): 支付金额 ,
     * packageId (integer, optional): 套餐ID ,
     * serviceId (integer, optional): 服务ID ,
     * userId (integer, optional): 用户ID
     */
    public void postPackagePayWxpay(String money, String packageId,String serviceId, YLRxSubscriberHelper<WeiXinPayResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("money", money);
        params.put("packageId", packageId);
        params.put("serviceId", serviceId);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.postPackagePayWxpay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 套餐 意约支付
     *
     * money (number, optional): 支付金额 ,
     * packageId (integer, optional): 套餐ID ,
     * serviceId (integer, optional): 服务ID ,
     * userId (integer, optional): 用户ID
     */
    public void packagePayYiyueOrderPay(String money, String packageId,String serviceId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("money", money);
        params.put("packageId", packageId);
        params.put("serviceId", serviceId);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.packagePayYiyueOrderPay(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 创建用戶当面付订单
     *
     * "storeId": 0,//二维码传过来
     * "stylistId": 0,//二维码传过来
     * "tradePayAmount": 0,//二维码传过来
     * "userId": 0//自己的id
     */
    public void createTradePayOrder(String storeId, String stylistId,String tradePayAmount, YLRxSubscriberHelper<CreateOrderResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("stylistId", stylistId);
        params.put("tradePayAmount", tradePayAmount);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.createTradePayOrder(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取套餐劵详情
     *
     * packageId 套餐券Id
     */
    public void findPackage(String packageId, YLRxSubscriberHelper<CouponDetailsResult> subscriberHelper) {
        mApi.findPackage(AccountManager.getInstance().getUserId(),packageId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
