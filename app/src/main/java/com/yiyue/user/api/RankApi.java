package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.requestbody.RankRequestBody;
import com.yiyue.user.model.vo.result.RankResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 排行榜
 * <p>
 * Created by wqy on 2018/11/19.
 */
public class RankApi {

    public interface Api {
        @POST("/user-api/stylistRank/getAll")
        Observable<RankResult> getRankAll(@Body RequestBody requestBody);
    }

    private Api api;

    public RankApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取排行榜列表
     */
    public void getRankAll(RankRequestBody body, YLRxSubscriberHelper<RankResult> rxSubscriberHelper) {
        api.getRankAll(new BaseRequestBody(body).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }
}
