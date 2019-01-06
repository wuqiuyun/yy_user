package com.yiyue.user.api;

import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.BulletinDetailResult;
import com.yiyue.user.model.vo.result.EvaluationResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 评价
 * Created by wqy on 2018/11/20.
 */

public class EvaluationApi {
    public interface Api {

        // 查看门店评论
        @GET("/user-api/stylistComment/getStoreCommentList")
        Observable<EvaluationResult> getStoreCommentList(@QueryMap Map<String, String> params);

        // 查看美发师评论
        @GET("/user-api/stylistComment/getStylistCommentList")
        Observable<EvaluationResult> getStylistCommentList(@QueryMap Map<String, String> params);

    }

    private Api mApi;

    public EvaluationApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 查看门店评论
     *
     * @param storeId
     * @param page
     * @param size
     * @param subscriberHelper
     */
    public void getStoreCommentList(String  storeId, int page, int size, YLRxSubscriberHelper<EvaluationResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId", storeId );
        params.put("page", page + "");
        params.put("size", size + "");
        mApi.getStoreCommentList(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }


    /**
     * 查看美发师评论
     *
     * @param stylistId
     * @param page
     * @param size
     * @param subscriberHelper
     */
    public void getStylistCommentList(String stylistId, int page, int size, YLRxSubscriberHelper<EvaluationResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("page", page + "");
        params.put("size", size + "");
        mApi.getStylistCommentList(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }


}
