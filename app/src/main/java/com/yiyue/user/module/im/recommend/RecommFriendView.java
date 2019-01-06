package com.yiyue.user.module.im.recommend;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface RecommFriendView extends IBaseView {
    void onFindAllContactsSuccess(List<UserFriendsBean> data);
    void onFindAllContactsFail();
}
