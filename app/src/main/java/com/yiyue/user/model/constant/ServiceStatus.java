package com.yiyue.user.model.constant;

import android.support.annotation.IntDef;

/**
 * 服务状态
 * Created by wqy on 2018/10/30.
 */

public class ServiceStatus {
    public static final int SERVICE_WASH_BLOW = 0; // 洗剪吹
    public static final int SERVICE_HOT_DYEING= 1; // 烫染拉
    public static final int SERVICE_HAIR_EXTENSION = 2; // 接发
    public static final int SERVICE_NURSING = 3; // 护理
    public static final int SERVICE_LEADERBOARD = 4; // 排行榜
    public static final int SERVICE_WORKS = 5; // 作品
    public static final int SERVICE_PACKAGE_COUPON = 6; // 套餐券
    public static final int SERVICE_PACKAGE = 7; // 套餐

    @IntDef({SERVICE_WASH_BLOW, SERVICE_HOT_DYEING, SERVICE_HAIR_EXTENSION, SERVICE_NURSING,SERVICE_LEADERBOARD,SERVICE_WORKS,SERVICE_PACKAGE_COUPON,SERVICE_PACKAGE})
    public @interface ServiceType {
    }
}
