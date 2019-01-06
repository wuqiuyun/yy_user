package com.yiyue.user.module.home.service.coupon;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.bean.ServiceCouponBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/11/1.
 */

public interface IServiceCouponView extends IBaseView {

    void getCouponSuccess(ArrayList<CouponBean> serviceCouponBean);

    void getCouponFail();

    void drawCouponSuccess();

    void drawCouponFail();
}
