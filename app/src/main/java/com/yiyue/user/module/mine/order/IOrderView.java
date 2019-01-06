package com.yiyue.user.module.mine.order;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.OrderBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/9/20.
 */
public interface IOrderView extends IBaseView {
        /**
         * 获取订单列表成功回调
         */
        void onGetOrderListSuccess(ArrayList<OrderBean> orderBeans);

        /**
         * 获取订单列表异常
         */
        void onGetOrderListFail();

        /**
         * 完成订单
         */
        void onCompleteOrderSuccess();

        /**
         * 取消订单
         */
        void onCancelOrderSuccess();

        /**
         * 统一美发师加价
         */
        void onAddMoneyAgreeSuccess();

        /**
         * 拒绝美发师加价
         */
        void onAddMoneyRefuseSuccess();

        /**
         * 送提醒成功
         */
        void onRemindStylistSuccess();
}
