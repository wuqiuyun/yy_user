package com.yiyue.user.module.mine.stylist.order;

import android.content.Context;

import com.yiyue.user.api.CouponApi;
import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.model.vo.result.CouponResult;
import com.yiyue.user.model.vo.result.CreateOrderResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/6.
 */

public class OrderConfirmPresenter extends BasePresenter<OrderConfirmView> {
    //获取用户优惠劵
    public void getCouponByUserId(String userId,String stylistId,String appointmentTime) {
        new CouponApi().getCouponByUserId(userId,stylistId,appointmentTime, new YLRxSubscriberHelper<CouponResult>() {
            @Override
            public void _onNext(CouponResult result) {
                getMvpView().onGetCouponSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //创建订单
    public void createOrder(CreateOrderBody createOrderBody, Context context) {
        new OrderApi().createOrder(createOrderBody, new YLRxSubscriberHelper<CreateOrderResult>(context,true) {
            @Override
            public void _onNext(CreateOrderResult result) {
                getMvpView().showToast("提交订单成功");
                getMvpView().createOrderSuccess(result.getData());
            }

            @Override
            protected void onShowMessage(ApiException apiException) {
                if (apiException.status == 202) { // 有未支付订单
                    getMvpView().showJumpOrderDialog();
                }else {
                    super.onShowMessage(apiException);
                }
            }
        });
    }
}
