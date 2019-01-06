package com.yiyue.user.model.constant;

import android.support.annotation.IntDef;

/**
 * Created by wqy on 2018/11/3.
 */

public class OrderStatus {
    public static final int ORDER_UNFINISHED = 0; // 待完成
    public static final int ORDER_UNPROCESSED = 1; // 待处理
    public static final int ORDER_PASS = 2; // 待通过
    public static final int ORDER_COMPLETE = 3; // 已完成
    public static final int ORDER_PAYMENT = 4; // 待付款

    @IntDef({ORDER_UNFINISHED, ORDER_UNPROCESSED, ORDER_PASS, ORDER_COMPLETE, ORDER_PAYMENT})
    public @interface OrderType {
    }
}
