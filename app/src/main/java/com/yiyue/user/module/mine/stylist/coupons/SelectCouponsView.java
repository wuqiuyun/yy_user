package com.yiyue.user.module.mine.stylist.coupons;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CouponBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/6.
 */

public interface SelectCouponsView extends IBaseView {
    void onSuccess(ArrayList<CouponBean> couponBeans);
}
