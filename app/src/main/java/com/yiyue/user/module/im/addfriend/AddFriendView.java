package com.yiyue.user.module.im.addfriend;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by zhangzz on 2018/10/16.
 */
public interface AddFriendView extends IBaseView {
    void onSearchUserSuccess(List<UserFriendsBean> data);
}
