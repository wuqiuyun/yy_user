package com.yiyue.user.api;



import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.ServerTypeResult;


import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/10/15.
 */
public class CategoryApi {

    public interface Api {
        //获取平台类目
        @GET("/user-api/category/getAll")
        Observable<ServerTypeResult> getAll();



    }

    private Api mApi;

    public CategoryApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取平台类目
     */
    public void getAll( YLRxSubscriberHelper<ServerTypeResult> subscriberelper) {
        mApi.getAll()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
