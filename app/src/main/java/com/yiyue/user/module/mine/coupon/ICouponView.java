package com.yiyue.user.module.mine.coupon;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CouponBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/9/29.
 */
public interface ICouponView extends IBaseView{
    void onSuccess(ArrayList<CouponBean> couponBeans);
    void onFail();
}
