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

public class StoreStylistApi {
    public interface Api {
        //门店美发师
        @POST("/user-api/storeStylist/storeStylist")
        Observable<StylistResult> storeStylist(@Body RequestBody requestBody);
        //门店美发师搜索
        @POST("/user-api/storeStylist/storeStylistSearch")
        Observable<StylistResult> storeStylistSearch(@Body RequestBody requestBody);
    }

    private Api mApi;

    public StoreStylistApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }


    /**
     *门店美发师
     */
    public void storeStylist(String storeId,String userId, YLRxSubscriberHelper<StylistResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("userId", userId);
        mApi.storeStylist(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
    /**
     *门店美发师搜索
     */
    public void storeStylistSearch(String nickname, String storeId, String userId, YLRxSubscriberHelper<StylistResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("storeId", storeId);
        params.put("userId", userId);
        mApi.storeStylistSearch(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
