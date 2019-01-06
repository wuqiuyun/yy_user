package com.yiyue.user.module.im.redpacket.redrecords;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.result.RedRecordResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface RedRecordsView extends IBaseView {
    void requestSuccess(RedRecordResult redRecordResult, boolean isRefresh);
    void requestFail();
}
