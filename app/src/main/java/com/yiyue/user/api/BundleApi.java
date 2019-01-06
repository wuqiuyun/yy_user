package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.BundleDetailsResult;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.PackageListResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 套餐相关api
 * Created by wqy on 2018/11/12.
 */

public class BundleApi {

    private interface Api {
        // 获取套餐列表
        @POST("/user-api/package/getPackageByType")
        Observable<CouponResult> getPackageByType(@Body RequestBody body);

        // 套餐详情
        @POST("/user-api/package/getPackageByServiceId")
        Observable<BundleDetailsResult> getPackageByServiceId(@Body RequestBody body);
    }

    private Api mApi;

    public BundleApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取套餐列表
     *
     * @param page
     * @param type             套餐类型1 单项 2 多项
     * @param subscriberHelper
     */
    public void getPackageByType(int page, String type, YLRxSubscriberHelper<CouponResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("type", type);
        params.put("userId", AccountManager.getInstance().getUserId());

        mApi.getPackageByType(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 套餐详情
     *
     * @param serviceId
     */
    public void getPackageByServiceId(String page, String type, String serviceId, YLRxSubscriberHelper<BundleDetailsResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("type", type);
        params.put("serviceId", serviceId );
        mApi.getPackageByServiceId(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
