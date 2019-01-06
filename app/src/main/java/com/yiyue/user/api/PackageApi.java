package com.yiyue.user.api;

import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.GetPackageuseRecorderResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/12/5.
 */
public class PackageApi {
    private interface Api {
        @POST("/user-api/package/getPackageUseRecorder")
        Observable<GetPackageuseRecorderResult> getPackageUseRecorder(@Body RequestBody requestBody);
    }

    private Api mApi;

    public PackageApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    public void getPackageUseRecorder(int page, int size, YLRxSubscriberHelper<GetPackageuseRecorderResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getPackageUseRecorder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.handleResult())
                .compose(RxSchedulers.rxSchedulerHelper())
                .safeSubscribe(subscriberHelper);
    }
}
