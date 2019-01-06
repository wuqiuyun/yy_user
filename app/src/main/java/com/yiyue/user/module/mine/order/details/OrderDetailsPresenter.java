package com.yiyue.user.module.mine.order.details;

import android.content.Context;
import android.os.CountDownTimer;

import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetOrderResult;

/**
 * Created by zm on 2018/9/27.
 */
public class OrderDetailsPresenter extends BasePresenter<IDetailsView>{
    private static final int ONECE_TIME = 1000;

    private OrderApi mOrderApi;

    private OrderApi getOrderApi() {
        return mOrderApi == null ? new OrderApi() : this.mOrderApi;
    }

    private CountDownTimer mCountDownTimer;
    /**
     * 开启倒计时
     */
    public void startCountdownTime(long pendingTime) {
        if (mCountDownTimer != null) {
            stopCountdownTime();
        }
        mCountDownTimer = new CountDownTimer(pendingTime, ONECE_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getMvpView() != null) {
                    getMvpView().updateCountdownTime(millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {
                if (getMvpView() != null) {
                    getMvpView().onCountdownFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 关闭倒计时
     */
    public void stopCountdownTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    /**
     * 获取订单详情
     * @param orderId
     */
    public void getOrderDetails(Context context, String orderId) {
        getOrderApi().getOrder(orderId, new YLRxSubscriberHelper<GetOrderResult>(context, true) {
            @Override
            public void _onNext(GetOrderResult baseResponse) {
                getMvpView().onGetOrderDetailsSuccess(baseResponse.getData());
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

    /**
     * 发送提醒
     * @param orderId
     * @param status
     */
    public void remindStylist(Context context, String orderId, String status) {
        getOrderApi().remindStylist(orderId, status, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("提醒已发送.");
                getMvpView().onRemindStylistSuccess();
            }
        });
    }
}
