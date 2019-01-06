package com.yiyue.user.module.im.groups;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.GroupBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface GroupsView extends IBaseView {

    void getGroupsSuccess(List<GroupBean> list);

    void getGroupsEmpty();

    void getGroupsFail();
}
