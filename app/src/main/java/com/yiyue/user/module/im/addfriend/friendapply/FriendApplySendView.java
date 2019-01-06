package com.yiyue.user.module.im.addfriend.friendapply;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.result.AddFriendResult;

/**
 * Created by zhangzz on 2018/10/16.
 */
public interface FriendApplySendView extends IBaseView {
    /**
     * 添加好友成功
     * @param result
     */
    void onAddFriendSuccess(AddFriendResult result);
}
