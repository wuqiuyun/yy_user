package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.OpusDetailsResult;
import com.yiyue.user.model.vo.result.OpusResult;
import com.yiyue.user.model.vo.result.StylistDetailsResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 美发师名片
 * Created by wqy on 2018/11/9.
 */

public class StylistCardApi {
    public interface Api {
        @POST("/user-api/stylistCard/card")
        Observable<StylistDetailsResult> getStylistDetails(@Body RequestBody requestBody);

        @POST("/user-api/stylistCard/opusList")
        Observable<OpusResult> getOpusList(@Body RequestBody requestBody);

        // 美发师作品集筛选
        @POST("/user-api/stylistCard/opusListScreen")
        Observable<OpusResult> opusListScreen(@Body RequestBody requestBody);

        @POST("/user-api/stylistCard/opusDetail")
        Observable<OpusDetailsResult> getOpusDetail(@Body RequestBody requestBody);

        @POST("/user-api/index/stylistCollection")
        Observable<BaseResponse> stylistCollection(@Body RequestBody requestBody);

        @POST("/user-api/stylistCard/oupsCollection")
        Observable<BaseResponse> opusCollection(@Body RequestBody requestBody);

        @POST("/user-api/stylistCard/opusCount")
        Observable<BaseResponse> opusCount(@Body RequestBody requestBody);
    }

    private Api mApi;

    public StylistCardApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 美发师名片
     *
     * @param stylistId
     * @param userId
     * @param subscriberHelper
     */
    public void getStylistDetails(String stylistId, String userId, YLRxSubscriberHelper<StylistDetailsResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        mApi.getStylistDetails(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师作品集
     *
     * @param stylistId
     * @param userId
     * @param subscriberHelper
     */
    public void getOpusList(String stylistId, String userId, YLRxSubscriberHelper<OpusResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        mApi.getOpusList(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 筛选美发师作品
     *
     * @param stylistId
     * @param screenId  对应筛选项的id
     * @param type      筛选类型 1发型  2 脸型
     */
    public void getOpusListScreen(String stylistId, String screenId, String type, YLRxSubscriberHelper<OpusResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("screenId", screenId);
        params.put("stylistId", stylistId);
        params.put("type", type);

        mApi.opusListScreen(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 作品详情
     *
     * @param opusId           作品ID
     * @param userId
     * @param subscriberHelper
     */
    public void getOpusDetail(String opusId, String userId, YLRxSubscriberHelper<OpusDetailsResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId);
        params.put("userId", userId);
        mApi.getOpusDetail(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师收藏
     *
     * @param stylistId
     * @param type
     * @param subscriberHelper
     */
    public void stylistCollection(String stylistId, int type, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("type", type + "");
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.stylistCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 作品收藏
     *
     * @param opusId
     * @param subscriberHelper
     */
    public void opusCollection(String opusId, int type,  YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId);
        params.put("type", type + "");
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.opusCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师作品转发、浏览
     *
     * @param opusId
     * @param type   1 转发/分享  2 浏览
     */
    public void opusCount(String opusId, String type, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId);
        params.put("type", type);

        mApi.opusCount(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
