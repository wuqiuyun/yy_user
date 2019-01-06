package com.yiyue.user.module.mine.order.details;

import com.yiyue.user.model.vo.bean.OrderDetailsBean;
import com.yiyue.user.module.mine.order.IOrderView;

/**
 * Created by zm on 2018/9/27.
 */
public interface IDetailsView extends IOrderView{
    /**
     * 订单详情
     */
    void onGetOrderDetailsSuccess(OrderDetailsBean data);

    /**
     * 订单完成回调
     */
    void onCompleteOrderSuccess();

    /**
     * 更新倒计时
     *
     * @param time
     */
    void updateCountdownTime(long time);

    /**
     * 倒计时结束
     */
    void onCountdownFinish();
}
