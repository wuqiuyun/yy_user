package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.SortSearchRequesetBody;
import com.yiyue.user.model.vo.result.ServerTypeResult;
import com.yiyue.user.model.vo.result.StoreListResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by zm on 2018/10/15.
 */
public class StoreLisApi {

    public interface Api {
        //搜索门店
        @GET("/user-api/storeList/search")
        Observable<StoreListResult> search(@QueryMap Map<String, String> map);
        //排序查询
        @POST("/user-api/storeList/sortSearch")
        Observable<StoreListResult> sortSearch(@Body RequestBody requestBody);

    }

    private Api mApi;

    public StoreLisApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 搜索门店
     */
    public void search( String search,String lng,String lat,String userId,YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("search", search);
        params.put("lng", lng);
        params.put("lat", lat);
        params.put("userId", userId);
        mApi.search(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 门店排序查询
     */
    public void sortSearch(SortSearchRequesetBody sortSearchRequesetBody, YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        mApi.sortSearch(new BaseRequestBody(sortSearchRequesetBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
