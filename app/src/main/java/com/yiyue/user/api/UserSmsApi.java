package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/10/15.
 */
public class UserSmsApi {

    public interface Api {
        @POST("/user-api/userSms/getPhoneCode")
        Observable<BaseResponse> getPhoneCode(@Body RequestBody requestBody);

        //通过本人手机号获取验证码
        @POST("/user-api/userSms/getSelfPhoneCode")
        Observable<BaseResponse> getSelfPhoneCode(@Body RequestBody requestBody);

        //校验验证码
        @POST("/user-api/userSms/phoneCodeCheck")
        Observable<BaseResponse> checkCode(@Body RequestBody requestBody);
    }

    private Api mApi;

    public UserSmsApi() {
        mApi = YLRequestManager.getRequest(UserSmsApi.Api.class);
    }

    /**
     * 获取验证码
     * @param mobile 手机号码
     * @param subscriberelper
     */
    public void getPhoneCode(String mobile, String type, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("type", type);

        mApi.getPhoneCode(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 通过本人手机号获取验证码
     * mobile (string, optional),
     * type (integer, optional),
     * userId (integer, optional)
     */
    public void getSelfPhoneCode(String mobile, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("type", "3");
        params.put("userId", AccountManager.getInstance().getUserId());

        mApi.getSelfPhoneCode(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 校验验证码
     * @param mobile 手机号码
     * @param subscriberelper
     */
    public void checkCode(String mobile, String type, String phoneCode, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("type", type);
        params.put("phoneCode", phoneCode);

        mApi.checkCode(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
}
