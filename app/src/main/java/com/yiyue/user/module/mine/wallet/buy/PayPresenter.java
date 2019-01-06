package com.yiyue.user.module.mine.wallet.buy;

import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.OrderApi;
import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.api.UserPayApi;
import com.yiyue.user.api.WalletInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.AliPayBean;
import com.yiyue.user.model.vo.bean.CouponDetailsBean;
import com.yiyue.user.model.vo.bean.WeiXinPayBean;
import com.yiyue.user.model.vo.result.AliPaysResult;
import com.yiyue.user.model.vo.result.CashInfoResult;
import com.yiyue.user.model.vo.result.CoinInfoResult;
import com.yiyue.user.model.vo.result.CouponDetailsResult;
import com.yiyue.user.model.vo.result.GetOrderResult;
import com.yiyue.user.model.vo.result.WeiXinPayResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/6.
 */

public class PayPresenter extends BasePresenter<IPayView> {

    //获取钱包余额
    public void getCashInfo(Context context) {
        new WalletInfoApi().getCashInfo(new YLRxSubscriberHelper<CashInfoResult>(context,true) {
            @Override
            public void _onNext(CashInfoResult cashInfoResult) {
                getMvpView().onGetCashInfoSuccess(cashInfoResult.getData());
            }

        });
    }

    //支付宝支付
    public void postAlipay(String orderno, String type,Context context) {
        new UserPayApi().postAlipay(orderno,type,new YLRxSubscriberHelper<AliPaysResult>(context,true) {
            @Override
            public void _onNext(AliPaysResult cashInfoResult) {
                AliPayBean aliPayBean = cashInfoResult.getData();
                getMvpView().onAliPaySuccess(aliPayBean);
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //支付宝支付状态查询
    public void alipayQuery(String orderno,Context context) {
        new UserPayApi().alipayQuery(orderno,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse cashInfoResult) {
                getMvpView().onAliPayStatusSuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //微信支付
    public void postWxpay(String orderno, String type,Context context) {
        new UserPayApi().postWxpay(orderno,type,new YLRxSubscriberHelper<WeiXinPayResult>(context,true) {
            @Override
            public void _onNext(WeiXinPayResult cashInfoResult) {
                WeiXinPayBean weiXinPay = cashInfoResult.getData();
                // callback
                getMvpView().onWxPaySuccess(weiXinPay);
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //意约钱包支付
    public void yiyueOrderPay(String orderno, String type,Context context) {
        new UserPayApi().yiyueOrderPay(orderno,type,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse cashInfoResult) {
                getMvpView().onYiYuePaySuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast("钱包支付失败");
            }
        });
    }

    //验证支付密码
    public void checkPayWord(String pwd,Context context) {
        if (TextUtils.isEmpty(pwd)) {
            getMvpView().showToast("支付密码不能为空");
            return;
        }
        new SettingsApi().checkPayWord(pwd,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().checkPasswordSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
            }
        });
    }



    //套餐 支付宝支付
    public void postPackagePayAlipay(String money, String packageId,String serviceId,Context context) {
        new UserPayApi().postPackagePayAlipay(money,packageId,serviceId,new YLRxSubscriberHelper<AliPaysResult>(context,true) {
            @Override
            public void _onNext(AliPaysResult cashInfoResult) {
                AliPayBean aliPayBean = cashInfoResult.getData();
                getMvpView().onAliPay1Success(aliPayBean);
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //套餐 微信支付
    public void postPackagePayWxpay(String money, String packageId,String serviceId,Context context) {
        new UserPayApi().postPackagePayWxpay(money,packageId,serviceId,new YLRxSubscriberHelper<WeiXinPayResult>(context,true) {
            @Override
            public void _onNext(WeiXinPayResult cashInfoResult) {
                WeiXinPayBean weiXinPay = cashInfoResult.getData();
                // callback
                getMvpView().onWxPaySuccess(weiXinPay);
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //套餐 意约钱包支付
    public void packagePayYiyueOrderPay(String money, String packageId,String serviceId,Context context) {
        new UserPayApi().packagePayYiyueOrderPay(money,packageId,serviceId,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse cashInfoResult) {
                getMvpView().onYiYuePaySuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //支付密码状态
    public void initPayWord(Context context) {
        new SettingsApi().initPayWord(new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().oninitPayWordInfoSuccess(CoinInfoResult.getData()+"");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //提交套餐订单
    public void commitPackage(String orderno,Context context) {
        new OrderApi().commitPackage(orderno,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().commitPackageSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    /**
     * 获取订单详情
     * @param orderId
     */
    public void getOrderDetails(Context context, String orderId) {
        new OrderApi().getOrder(orderId, new YLRxSubscriberHelper<GetOrderResult>(context, true) {
            @Override
            public void _onNext(GetOrderResult baseResponse) {
                getMvpView().onGetOrderDetailsSuccess(baseResponse.getData());
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().onGetOrderDetailsFail();
            }

        });
    }



    //套餐券详情
    public void findPackage(String packageId,Context context) {
        new UserPayApi().findPackage(packageId,new YLRxSubscriberHelper<CouponDetailsResult>(context,true) {
            @Override
            public void _onNext(CouponDetailsResult cashInfoResult) {
                CouponDetailsBean couponDetailsBean = cashInfoResult.getData();
                getMvpView().onGetCouponDetailsSuccess(couponDetailsBean);
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

}
