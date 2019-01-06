package com.yiyue.user.module.mine.coupon;

import com.yiyue.user.api.CollectionApi;
import com.yiyue.user.api.UserCouponApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.StoreListResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/29.
 */
public class CouponPresenter extends BasePresenter<ICouponView>{
    //根据状态查询用户的优惠券列表
    public void getStoreCollection( String type,String userId) {
        new UserCouponApi().getUserCouponByType(type, userId, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                getMvpView().onSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().onFail();
            }
        });
    }
    //根据状态查询用户的优惠券列表
    public void getUserPackageByUserId(String userId) {
        new UserCouponApi().getUserPackageByUserId(userId, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                getMvpView().onSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().onFail();
            }
        });
    }
}
