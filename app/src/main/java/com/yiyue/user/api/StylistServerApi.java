package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.ServiceDetaillsResult;
import com.yiyue.user.model.vo.result.StoreListResult;
import com.yiyue.user.model.vo.result.StylistServerResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 美发师服务相关
 * Created by wqy on 2018/11/9.
 */

public class StylistServerApi {
    public interface Api {
        //查询美发师服务
        @POST("/user-api/stylistServer/getStylistServer")
        Observable<StylistServerResult> getStylistServer(@Body RequestBody requestBody);
        //查询美发师服务详情
        @POST("/user-api/stylistServer/getStylistServerDetail")
        Observable<ServiceDetaillsResult> getStylistServerDetail(@Body RequestBody requestBody);
        //查询美发师入驻门店
        @POST("/user-api/stylistServer/getStylistStore")
        Observable<StoreListResult> getStylistStore(@Body RequestBody requestBody);
    }

    private Api mApi;

    public StylistServerApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    // 查询美发师服务
    public void getStylistServer(String stylistId, String userId, YLRxSubscriberHelper<StylistServerResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        mApi.getStylistServer(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
    // 查询美发师服务详情
    public void getStylistServerDetail(String serviceId, YLRxSubscriberHelper<ServiceDetaillsResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceId",serviceId);
        mApi.getStylistServerDetail(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
    // 查询美发师入驻门店
    public void getStylistStore(String stylistId, String userId, YLRxSubscriberHelper<StoreListResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        mApi.getStylistStore(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
