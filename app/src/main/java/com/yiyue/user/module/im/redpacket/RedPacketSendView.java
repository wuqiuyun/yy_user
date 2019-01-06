package com.yiyue.user.module.im.redpacket;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CashInfoBean;
import com.yiyue.user.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface RedPacketSendView extends IBaseView {
    void requestSuccess(RedBagSendResult redBagSendResult);
    void checkPasswordSuccess();
    //获取钱包余额
    void onGetCashInfoSuccess(CashInfoBean bean);
}
