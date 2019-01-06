package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.UserAuthApplyRequestBody;
import com.yiyue.user.model.vo.result.GetApplyStatusResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lizhuo on 2018/11/9.
 * 用户认证Api
 */
public class UserAuthApplyApi {
    public interface Api{
        // 提交认证信息
        @POST("/user-api/userAuthApply/addOrUpdate")
        Observable<BaseResponse> addOrUpdate(@Body RequestBody requestBody);

        // 获取认证信息
        @GET("/user-api/userAuthApply/getApplyStatus")
        Observable<GetApplyStatusResult> getApplyStatus(@Query("userId") String userId);

        // 获取认证信息
        @GET("/user-api/userAuthApply/getUserAuth")
        Observable<GetApplyStatusResult> getUserAuth(@Query("userId") String userId);

    }
    
    private Api mApi;

    public UserAuthApplyApi() {
        mApi = YLRequestManager.getRequest(UserAuthApplyApi.Api.class);
    }

    /**
     * 提交认证信息
     * @param requestBody 认证信息
     * @param subscriberHelper
     */
    public void addOrUpdate(UserAuthApplyRequestBody requestBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.addOrUpdate(new BaseRequestBody(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取认证信息
     * @param subscriberHelper
     */
    public void getApplyStatus(YLRxSubscriberHelper<GetApplyStatusResult> subscriberHelper) {
        mApi.getApplyStatus(AccountManager.getInstance().getUserId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取实名认证信息
     * @param subscriberHelper
     */
    public void getUserAuth(YLRxSubscriberHelper<GetApplyStatusResult> subscriberHelper) {
        mApi.getUserAuth(AccountManager.getInstance().getUserId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
