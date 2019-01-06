package com.yiyue.user.model.constant;

import android.support.annotation.IntDef;

/**
 * Created by wqy on 2018/11/3.
 */

public class MineCouponStatus {
    public static final int COUPON_FULL_REDUCTION = 0; // 满减券
    public static final int COUPON_DISCOUNT = 1; // 折扣券
    public static final int COUPON_DEDUCTION = 2; // 抵扣券
    public static final int COUPON_PACKAGE = 3; // 套餐券

    @IntDef({COUPON_FULL_REDUCTION, COUPON_DISCOUNT, COUPON_DEDUCTION, COUPON_PACKAGE})
    public @interface MineCouponType {
    }
}
