package com.yiyue.user.api;


import com.yiyue.user.base.data.BaseRequestBody;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRequestManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.rx.RxSchedulers;
import com.yiyue.user.model.vo.result.StylistResult;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Lizhuo on 2018/10/24.
 * 美发师相关接口
 */
public class StylistsearchApi {
    public interface Api {


        //类目ID 美发师查找
        @POST("/user-api/stylistsearch/getStylistByCategoryId")
        Observable<StylistResult> getStylistByCategoryId(@Body RequestBody requestBody);
       //根据昵称获取美发师
        @POST("/user-api/stylistsearch/getStylistByStylistName")
        Observable<StylistResult> getStylistByStylistName(@Body RequestBody requestBody);
        //附近的美发师
        @POST("/user-api/stylistsearch/inviteNear")
        Observable<StylistResult> inviteNear(@Body RequestBody requestBody);
        //美发师筛选接口
        @POST("/user-api/stylistsearch/inviteScreen")
        Observable<StylistResult> inviteScreen(@Body RequestBody requestBody);
        //美发师搜索接口
        @POST("/user-api/stylistsearch/inviteSearch")
        Observable<StylistResult> inviteSearch(@Body RequestBody requestBody);
        //美发师排序接口
        @POST("/user-api/stylistsearch/inviteSort")
        Observable<StylistResult> inviteSort(@Body RequestBody requestBody);


    }

    private Api api;

    public StylistsearchApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     *根据类目ID 查找美发师
     * @param categoryId 类目id
     * @param categoryName 类目名称
     */
    public void getStylistByCategoryId(int categoryId, String categoryName, String userId, int page, YLRxSubscriberHelper<StylistResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
         params.put("categoryId", String.valueOf(categoryId));
         params.put("categoryName", categoryName);
         params.put("userId", userId);
         params.put("page", String.valueOf(page));
        api.getStylistByCategoryId(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }
    /**
     *根据昵称获取美发师
     * @param stylistName 美发师昵称
     */
    public void getStylistByStylistName( String stylistName, String userId, int page, YLRxSubscriberHelper<StylistResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
         params.put("stylistName", stylistName);
         params.put("userId", userId);
         params.put("page", String.valueOf(page));
        api.getStylistByStylistName(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     *附近的美发师
     * @param cityId 城市ID
     * @param districtId 区域ID
     * @param distance 附近距离
     */
    public void inviteNear( String cityId,String districtId,String distance, String userId, int page, YLRxSubscriberHelper<StylistResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
         params.put("cityId", cityId);
         params.put("districtId", districtId);
         params.put("distance", distance);
         params.put("userId", userId);
         params.put("page", String.valueOf(page));

        api.inviteNear(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     *美发师筛选接口
     * @param coupon 优惠券类型 1 满减 2 折扣
     * @param position 职级
     */
    public void inviteScreen( String coupon,String position, String userId, int page, YLRxSubscriberHelper<StylistResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
         params.put("coupon", coupon);
         params.put("position", position);
         params.put("userId", userId);
         params.put("page", String.valueOf(page));
        api.inviteScreen(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     *美发师排序接口
     * @param sortType 0综合，1距离最近，2月接单最多，3评论量最多，4，价格最低，5价格最高
     */
    public void inviteSort( String sortType, String userId, int page, YLRxSubscriberHelper<StylistResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
         params.put("sortType", sortType);
         params.put("userId", userId);
         params.put("page", String.valueOf(page));
        api.inviteSort(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }


}
