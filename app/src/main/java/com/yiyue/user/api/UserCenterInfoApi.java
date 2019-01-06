package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.UserCenterInfoResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lizhuo on 2018/11/8.
 * 用户个人中心
 */
public class UserCenterInfoApi {

    public interface Api {
        @GET("/user-api/userCenterInfo/getUserCenterInfo")
        Observable<UserCenterInfoResult> getUserCenterInfo(@Query("userId") String userId);

        @POST("/user-api/userCenterInfo/updateBirthday")
        Observable<BaseResponse> updateBirthday(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateFaceture")
        Observable<BaseResponse> updateFaceture(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateHaidStyle")
        Observable<BaseResponse> updateHaidStyle(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateHeadImg")
        Observable<BaseResponse> updateHeadImg(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateHobby")
        Observable<BaseResponse> updateHobby(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateJob")
        Observable<BaseResponse> updateJob(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateUserName")
        Observable<BaseResponse> updateUserName(@Body RequestBody requestBody);

        @POST("/user-api/userCenterInfo/updateUserSex")
        Observable<BaseResponse> updateUserSex(@Body RequestBody requestBody);
    }

    private Api mApi;

    public UserCenterInfoApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取用户个人资料
     */
    public void getUserCenterInfo(YLRxSubscriberHelper<UserCenterInfoResult> subscriberHelper) {
        mApi.getUserCenterInfo(AccountManager.getInstance().getUserId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新生日
     */
    public void updateBirthday(String birthday, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("birthday", birthday);

        mApi.updateBirthday(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新脸型
     * @param faceture 1方脸 2圆脸 3尖脸 4瓜子脸 5鹅蛋脸 6包子脸
     */
    public void updateFaceture(String faceture, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("faceture", faceture);

        mApi.updateFaceture(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新发长
     * @param hairStyle 1长发 2短发 3中发 
     */
    public void updateHaidStyle(String hairStyle, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("hairStyle", hairStyle);

        mApi.updateHaidStyle(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新头像
     */
    public void updateHeadImg(String headImg, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("headImg", headImg);

        mApi.updateHeadImg(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新爱好
     */
    public void updateHobby(String hobby, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("hobby", hobby);

        mApi.updateHobby(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 更新职业
     */
    public void updateJob(String job, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("job", job);

        mApi.updateJob(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改名字
     */
    public void updateUserName(String name, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("name", name);

        mApi.updateUserName(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改性别
     */
    public void updateUserSex(String sex, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("sex", sex);

        mApi.updateUserSex(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
