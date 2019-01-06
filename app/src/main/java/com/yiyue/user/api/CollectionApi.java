package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.requestbody.SortSearchRequesetBody;
import com.yiyue.user.model.vo.result.GetOpusResult;
import com.yiyue.user.model.vo.result.StoreListResult;
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
 * 我的收藏
 * Created by zm on 2018/10/15.
 */
public class CollectionApi {
    public interface Api {
        //作品
        @POST("/user-api/collection/getOpus")
        Observable<GetOpusResult> getOpus(@Body RequestBody requestBody);
        //门店
        @POST("/user-api/collection/getStore")
        Observable<StoreListResult> getStore(@Body RequestBody requestBody);
        //美发师
        @POST("/user-api/collection/getStylist")
        Observable<StylistResult> getStylist(@Body RequestBody requestBody);

    }

    private Api mApi;

    public CollectionApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 作品
     */
    public void getOpus( String userId,String page,String size,YLRxSubscriberHelper<GetOpusResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        mApi.getOpus(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 门店
     */
    public void getStore( String userId,String page,String size,String lat,String lng,YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        params.put("lat", lat);
        params.put("lng", lng);
        mApi.getStore(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 美发师
     */
    public void getStylist( String userId,int page,int size,YLRxSubscriberHelper<StylistResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        mApi.getStylist(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
