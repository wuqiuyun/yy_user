package com.yiyue.user.module.im.sysnotice;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.SysMsgBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface SysMsgView extends IBaseView {
//    void onFindAddFriendSuccess(List<SysMsgBean> data);

    void onReceiveAddFriendSuccess();

    void onReceiveAddGroupSuccess();
}
