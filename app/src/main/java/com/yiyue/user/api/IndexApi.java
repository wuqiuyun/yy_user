package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.StoreRecommendResult;
import com.yiyue.user.model.vo.result.StylistRecommendResult;
import com.yiyue.user.model.vo.result.StylistResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by wqy on 2018/11/7.
 */

public class IndexApi {
    public interface Api {
        @POST("/user-api/index/getStoreRecommend")
        Observable<StoreRecommendResult> getStoreRecommend(@Body RequestBody requestBody);

        @POST("/user-api/index/getStylistRecommend")
        Observable<StylistRecommendResult> getStylistRecommend(@Body RequestBody requestBody);

        @POST("/user-api/index/stylistCollection")
        Observable<BaseResponse> stylistCollection(@Body RequestBody requestBody);

        @POST("/user-api/index/search")
        Observable<StylistResult> search(@Body RequestBody requestBody);


    }

    private Api mApi;

    public IndexApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    // 口碑店铺推荐
    public void getStoreRecommend(YLRxSubscriberHelper<StoreRecommendResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getStoreRecommend(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    // 口碑美发师推荐
    public void getStylistRecommend(YLRxSubscriberHelper<StylistRecommendResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getStylistRecommend(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    // 根据类目查询美发师
    public void search(String userId, String categoryName, int page, YLRxSubscriberHelper<StylistResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("categoryName", categoryName);
        params.put("page", String.valueOf(page));
        mApi.search(new BaseRequestBody(params).toRequestBody())
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

}
