package com.yiyue.user.module.home.stylist;

import android.text.TextUtils;

import com.yiyue.user.api.AreaApi;
import com.yiyue.user.api.IndexApi;
import com.yiyue.user.api.StoreManageApi;
import com.yiyue.user.api.StoreStylistApi;
import com.yiyue.user.api.StylistsearchApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.AreaResult;
import com.yiyue.user.model.vo.result.StylistResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/3.
 */

public class StylistPresenter extends BasePresenter<IStylistView> {
    //获取所有地区
    public void getArea() {
        new AreaApi().getArea( new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult areaResult) {
                getMvpView().getArea(areaResult.getData());
            }
        });
    }

    //根据昵称获取美发师
    public void getStylistByStylistName( String stylistName, String userId, int page) {
        if (TextUtils.isEmpty(stylistName)) {
            getMvpView().showToast("搜索内容不能为空");
            return;
        }
        new StylistsearchApi().getStylistByStylistName(stylistName, userId, page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (null != result.getData()) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }

    //附近的美发师
    public void inviteNear( String cityId,String districtId,String distance, String userId, int page) {
        new StylistsearchApi().inviteNear(cityId, districtId, distance, userId, page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (null != result.getData()) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //美发师筛选
    public void inviteScreen( String coupon,String position, String userId, int page){
        new StylistsearchApi().inviteScreen(coupon, position, userId,page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (null != result.getData()) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //美发师排序
    public void inviteSort( String sortType, String userId, int page){
        new StylistsearchApi().inviteSort(sortType, userId,page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (null != result.getData()) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //按类目搜索美发师
    public void getStylistByCategoryId(int categoryId, String categoryName, String userId, int page){
        new StylistsearchApi().getStylistByCategoryId(categoryId,categoryName, userId,page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (null != result.getData()) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }

    //通过用户ID获取用户区域
    public void getAreaByUserId( String userId){
        new AreaApi().getAreaByUserId(userId, new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                if (null != result.getData()) {
                    getMvpView().getArea(result.getData());
                } else {
                    getMvpView().showToast("定位区域失败!");
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().showToast("定位区域失败!");
            }
        });
    }

    //根据类目名称查询美发师
    public void search( String userId,String categoryName,int page){
        new IndexApi().search(userId,categoryName,page, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (result.getData()!=null){
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //门店美发师
    public void storeStylist(String storeId,String userId){
        new StoreStylistApi().storeStylist(storeId,userId, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (result.getData()!=null){
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //门店美发师搜索
    public void storeStylistSearch(String nickname,String storeId,String userId){
        new StoreStylistApi().storeStylistSearch(nickname,storeId,userId, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                if (result.getData()!=null){
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
}
