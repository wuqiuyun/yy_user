package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.HairListResult;
import com.yiyue.user.model.vo.result.ServiceOpusResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 作品相关api
 * Created by wqy on 2018/11/15.
 */

public class OpusApi {
    public interface Api {
        // 获取作品列表
        @POST("/user-api/opusRank/getAll")
        Observable<ServiceOpusResult> getAllWorks(@Body RequestBody body);

        // 筛选作品列表
        @POST("/user-api/opusRank/getOpusType")
        Observable<ServiceOpusResult> getOpusType(@Body RequestBody body);

        // 脸型列表
        @GET("/user-api/opusType/getFeature")
        Observable<HairListResult> getFeature();

        // 发型列表
        @GET("/user-api/opusType/getHairstyle")
        Observable<HairListResult> getHairstyle();
    }

    private Api mApi;

    public OpusApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取作品列表
     *
     * @param lat
     * @param lng
     * @param page
     * @param size
     * @param type 1 默认最新 2 人气 3 综合 4 距离
     */
    public void getAllWorks(double lat, double lng, int page, int size, String type, YLRxSubscriberHelper<ServiceOpusResult> subscriberelper) {
        Map<String, Object> params = new HashMap<>();
        params.put("latitude", lat);
        params.put("longitude", lng);
        params.put("page", page);
        params.put("size", size);
        params.put("type", type);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getAllWorks(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 筛选作品
     *
     * @param feature
     * @param hairStyle
     * @param page
     * @param size
     * @param subscriberelper
     */
    public void getOpusType(List<Integer> feature, List<Integer> hairStyle, int page, int size, YLRxSubscriberHelper<ServiceOpusResult> subscriberelper) {
        Map<String, Object> params = new HashMap<>();
        params.put("feature", feature);
        params.put("hairStyle", hairStyle);
        params.put("page", page);
        params.put("size", size);
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getOpusType(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 脸型列表
     *
     * @param subscriberelper
     */
    public void getFeature(YLRxSubscriberHelper<HairListResult> subscriberelper) {
        mApi.getFeature()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 发型列表
     *
     * @param subscriberelper
     */
    public void getHairstyle(YLRxSubscriberHelper<HairListResult> subscriberelper) {
        mApi.getHairstyle()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }


}
