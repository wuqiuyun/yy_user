package com.yiyue.user.module.home;

import com.yiyue.user.api.AreaApi;
import com.yiyue.user.api.IndexApi;
import com.yiyue.user.api.StylistCardApi;
import com.yiyue.user.api.UserBannerApi;
import com.yiyue.user.api.UserLoacationApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.requestbody.SaveLocationBody;
import com.yiyue.user.model.vo.result.AreaResult;
import com.yiyue.user.model.vo.result.BannerResult;
import com.yiyue.user.model.vo.result.LoginResult;
import com.yiyue.user.model.vo.result.StoreRecommendResult;
import com.yiyue.user.model.vo.result.StylistRecommendResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/19.
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    // 口碑店铺推荐
    public void getStoreRecommend() {
        new IndexApi().getStoreRecommend(new YLRxSubscriberHelper<StoreRecommendResult>() {
            @Override
            public void _onNext(StoreRecommendResult storeRecommendResult) {
                getMvpView().onGetStoreListSuccess(storeRecommendResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onGetListFail();

            }
        });
    }

    // 口碑美发师推荐
    public void getStylistRecommend() {
        new IndexApi().getStylistRecommend( new YLRxSubscriberHelper<StylistRecommendResult>() {
            @Override
            public void _onNext(StylistRecommendResult stylistRecommendResult) {
                getMvpView().onGetStylistListSuccess(stylistRecommendResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onGetListFail();

            }
        });

    }

    // 美发师收藏
    public void stylistCollection(String stylistId, int type) {
        new StylistCardApi().stylistCollection(stylistId, type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().collectionSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().collectFail();
            }
        });
    }

    // 保存用户地位信息
    public void saveLocation(SaveLocationBody saveLocationBody) {
        new UserLoacationApi().save(saveLocationBody, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onGetLocationSuccess();
                DLog.d("用户定位信息保存成功!");
            }
        });
    }

    // 获取所有地区
    public void getArea() {
        new AreaApi().getArea(new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult areaResult) {
                getMvpView().onGetAreaSuccess(areaResult.getData());
            }
        });
    }

    // 用户banner信息
    public void getBanner() {
        new UserBannerApi().getBanner(new YLRxSubscriberHelper<BannerResult>() {
            @Override
            public void _onNext(BannerResult result) {
                if (null != result.getData()) {
                    getMvpView().onGetBannerSuccess(result.getData());
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onGetBannerFail();
            }
        });
    }

    // 修改城市
    public void changesave(String cityId,String cityName) {
        new UserLoacationApi().changesave(cityId,cityName, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onChangesaveSuccess();
            }
        });
    }

}
