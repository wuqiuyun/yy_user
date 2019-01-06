package com.yiyue.user.module.mine.wallet.buy;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AliPayBean;
import com.yiyue.user.model.vo.bean.CashInfoBean;
import com.yiyue.user.model.vo.bean.CouponDetailsBean;
import com.yiyue.user.model.vo.bean.OrderDetailsBean;
import com.yiyue.user.model.vo.bean.WeiXinPayBean;

/**
 * Created by wqy on 2018/11/6.
 */

public interface IPayView extends IBaseView {
    //获取钱包余额
    void onGetCashInfoSuccess(CashInfoBean bean);

    //支付宝支付成功
    void onAliPaySuccess(AliPayBean aliPayBean);

    //支付宝支付成功
    void onAliPay1Success(AliPayBean aliPayBean);

    //查询支付宝支付状态成功
    void onAliPayStatusSuccess();

    //微信支付成功
    void onWxPaySuccess(WeiXinPayBean weiXinPay);

    //获取账户支付密码信息成功
    void oninitPayWordInfoSuccess(String json);

    //意约钱包支付成功
    void onYiYuePaySuccess();

    void checkPasswordSuccess();
    //套餐订单提交成功
    void commitPackageSuccess();

    /**
     * 订单详情
     */
    void onGetOrderDetailsSuccess(OrderDetailsBean data);

    void onGetOrderDetailsFail();

    void onGetCouponDetailsSuccess(CouponDetailsBean data);

}
