package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.SaveLocationBody;
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
public class UserLoacationApi {

    public interface Api {
        @POST("/user-api/userLocation/save")
        Observable<BaseResponse> save(@Body RequestBody requestBody);
        @POST("/user-api/userLocation/changesave")
        Observable<BaseResponse> changesave(@Body RequestBody requestBody);
    }

    private Api api;

    public UserLoacationApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 保存用户地址信息
     */
    public void save(SaveLocationBody saveLocationBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        api.save(new BaseRequestBody(saveLocationBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    public void changesave(String cityId,String cityName, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("cityId", cityId);
        params.put("cityName", cityName);
        api.changesave(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
