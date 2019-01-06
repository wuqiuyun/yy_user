package com.yiyue.user.module.home.service.coupon;

import com.yiyue.user.api.UserCouponApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.ServiceCouponResult;

/**
 * Created by Administrator on 2018/11/1.
 */

public class ServiceCouponPresenter extends BasePresenter<IServiceCouponView> {

    // 获取平台优惠券接口
    public void getCouponByType(String couponId, String type, String userId) {
        new UserCouponApi().getCouponByType(couponId, type, userId, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                if (null != result.getData()) {
                    getMvpView().getCouponSuccess(result.getData());
                } else {
                    getMvpView().getCouponFail();
                }

            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView() != null) getMvpView().getCouponFail();
            }
        });
    }

    // 领优惠券
    public void drawCoupon(String couponId, String type) {
        new UserCouponApi().drawCoupon(couponId, type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse response) {
                if (response.getCode() == 200) {
                    getMvpView().drawCouponSuccess();
                } else {
                    getMvpView().drawCouponFail();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView() != null) getMvpView().drawCouponFail();
            }
        });
    }
}
