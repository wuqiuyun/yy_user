package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.AreaResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 地区
 * <p>
 * Created by zm on 2018/9/25.
 */
public class AreaApi {

    public interface Api {

        @GET("/user-api/area/getArea")
        Observable<AreaResult> getArea();
        @POST("/user-api/area/getAreaByUserId")
        Observable<AreaResult> getAreaByUserId(@Body RequestBody requestBody);

    }

    private Api api;

    public AreaApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取所有地区
     */
    public void getArea( YLRxSubscriberHelper<AreaResult> rxSubscriberHelper) {
        api.getArea()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }
    /**
     * 通过用户ID获取用户区域
     */
    public void getAreaByUserId( String userId,YLRxSubscriberHelper<AreaResult> rxSubscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        api.getAreaByUserId(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }


}
