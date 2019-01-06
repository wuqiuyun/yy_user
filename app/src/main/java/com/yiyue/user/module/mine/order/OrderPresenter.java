package com.yiyue.user.module.mine.order;

import android.content.Context;

import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetOrderListResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/20.
 */
public class OrderPresenter extends BasePresenter<IOrderView>{
    private OrderApi mOrderApi;

    private OrderApi getOrderApi() {
        return mOrderApi == null ? new OrderApi() : mOrderApi;
    }

    /**
     * 发送提醒
     * @param orderId
     * @param status
     */
    public void remindStylist(Context context, String orderId, String status) {
        getOrderApi().remindStylist(orderId, status, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onRemindStylistSuccess();
            }
        });
    }

    /**
     * 获取订单列表
     * @param status
     * @param page
     * @param size
     */
    public void getOrderList(int status, int page, int size) {
        getOrderApi().getOrderPage(status, page, size, new YLRxSubscriberHelper<GetOrderListResult>() {
            @Override
            public void _onNext(GetOrderListResult baseResponse) {
                getMvpView().onGetOrderListSuccess(baseResponse.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onGetOrderListFail();
            }
        });
    }

    /**
     * 完成订单
     * @param orderId
     */
    public void completeOrder(Context context, String orderId) {
        getOrderApi().completeOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onCompleteOrderSuccess();
            }
        });
    }

    /**
     * 取消订单
     * @param orderId
     */
    public void cancelOrder(Context context, String orderId) {
        getOrderApi().cancelOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onCancelOrderSuccess();
            }
        });
    }

    /**
     * 取消申请
     * @param orderId
     */
    public void cancelRequestOrder(Context context, String orderId) {
        getOrderApi().cancelRequestOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onCancelOrderSuccess();
            }
        });
    }

    /**
     * 同意加价
     * @param orderId
     */
    public void addMoneyAgree(Context context, String orderId) {
        getOrderApi().addMoneyAgree(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onAddMoneyAgreeSuccess();
            }
        });
    }

    /**
     * 拒绝加价
     * @param orderId
     */
    public void addMoneyRefuse(Context context, String orderId) {
        getOrderApi().addMoneyRefuse(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onAddMoneyRefuseSuccess();
            }
        });
    }
}
