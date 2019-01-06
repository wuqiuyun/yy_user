package com.yiyue.user.module.im.transfer.transrecords;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface TransferRecordsView extends IBaseView {
    void requestSuccess(RedBagSendResult redBagSendResult);
}
