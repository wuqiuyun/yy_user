package com.yiyue.user.module.mine.stylist.coupons;

import com.yiyue.user.api.CouponApi;
import com.yiyue.user.api.StylistServerApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.StoreListResult;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;

public class SelectCouponsPresenter extends BasePresenter<SelectCouponsView> {
    //获取用户优惠劵
    public void getCouponByUserId(String userId,String stylistId,String appointmentTime) {
        new CouponApi().getCouponByUserId(userId,stylistId,appointmentTime, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                getMvpView().onSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
