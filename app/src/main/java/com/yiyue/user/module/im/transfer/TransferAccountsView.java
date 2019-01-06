package com.yiyue.user.module.im.transfer;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CashInfoBean;
import com.yiyue.user.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface TransferAccountsView extends IBaseView {
    void requestSuccess(RedBagSendResult redBagSendResult);
    void checkPasswordSuccess();
    //获取钱包余额
    void onGetCashInfoSuccess(CashInfoBean bean);
}
