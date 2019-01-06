package com.yiyue.user.module.mine.stylist.order;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.result.CreateOrderResult;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/6.
 */

public interface OrderConfirmView extends IBaseView {
    void onGetCouponSuccess(ArrayList<CouponBean> couponBeans);
    void createOrderSuccess(CreateOrderBean createOrderBean);
    void showJumpOrderDialog();
}
