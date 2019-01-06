package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.ChooseStylistResult;
import com.yiyue.user.model.vo.result.ContactResult;
import com.yiyue.user.model.vo.result.StoreInfoResult;
import com.yiyue.user.model.vo.result.StoreScoreResult;
import com.yiyue.user.model.vo.result.StoreServerScopeResult;
import com.yiyue.user.model.vo.result.StylistResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by wqy on 2018/11/8.
 */

public class StoreManageApi {
    public interface Api {
        // 门店名 位置等信息
        @POST("/user-api/storeManage/getStoreInfo")
        Observable<StoreInfoResult> getStoreInfo(@Body RequestBody requestBody);

        // 入驻/签约美发师
        @POST("/user-api/storeManage/getNexusStyScrool")
        Observable<StylistResult> getNexusStyScrool(@Body RequestBody requestBody);

        // 门店联系方式
        @GET("/user-api/storeManage/contact")
        Observable<ContactResult> contact(@QueryMap Map<String, String> map);

        // 门店顾客评价
        @GET("/user-api/storeManage/getStoreScore")
        Observable<StoreScoreResult> getStoreScore(@QueryMap Map<String, String> map);

        // 门店服务范围
        @GET("/user-api/storeManage/getStoreServerScope")
        Observable<StoreServerScopeResult> getStoreServerScope(@QueryMap Map<String, String> map);

        // 门店收藏/取消
        @POST("/user-api/storeManage/updateCollectionType")
        Observable<BaseResponse> updateCollectionType(@Body RequestBody requestBody);

        // 预约理发师列表
        @GET("/user-api/storeManage/chooseStylists")
        Observable<ChooseStylistResult> chooseStylists(@QueryMap Map<String, String> map);
    }

    private Api mApi;

    public StoreManageApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 门店名 位置等信息
      * @param lat
     * @param lng
     * @param storeId
     * @param userId
     * @param subscriberHelper
     */
    public void getStoreInfo(double lat, double lng, String storeId, String userId, YLRxSubscriberHelper<StoreInfoResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", lat+"");
        params.put("lng", lng+"");
        params.put("storeId", storeId);
        params.put("userId", userId);
        mApi.getStoreInfo(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 入驻/签约美发师
     * @param nexus 入驻/签约，0入驻，1签约 3全部
     * @param storeId
     * @param userId
     * @param subscriberHelper
     */
    public void getNexusStyScrool(String nexus, String storeId, String userId, YLRxSubscriberHelper<StylistResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("nexus", nexus);
        params.put("storeId", storeId);
        params.put("userId", userId);
        mApi.getNexusStyScrool(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店联系方式
     * @param storeId
     * @param userId
     * @param subscriberHelper
     */
    public void contact(String storeId, String userId, YLRxSubscriberHelper<ContactResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("storeId", storeId);
        mApi.contact(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店顾客评价
     * @param userId
     * @param storeId
     * @param subscriberHelper
     */
    public void getStoreScore(String userId, String storeId, YLRxSubscriberHelper<StoreScoreResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("storeId", storeId);
        mApi.getStoreScore(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店服务范围
     * @param userId
     * @param storeId
     * @param subscriberHelper
     */
    public void getStoreServerScope(String userId, String storeId, YLRxSubscriberHelper<StoreServerScopeResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("storeId", storeId);
        mApi.getStoreServerScope(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店收藏/取消
     * @param isCollection
     * @param storeId
     * @param userId
     * @param subscriberHelper
     */
    public void updateCollectionType(int isCollection, String storeId, String userId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("isCollection", isCollection+"");// 当前用户前端收藏状态，1已收藏，0未收藏
        params.put("storeId", storeId);
        params.put("userId", userId);
        mApi.updateCollectionType(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 预约理发师列表
     * @param storeId
     * @param subscriberHelper
     */
    public void chooseStylists(String storeId, YLRxSubscriberHelper<ChooseStylistResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId", storeId);
        mApi.chooseStylists(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
