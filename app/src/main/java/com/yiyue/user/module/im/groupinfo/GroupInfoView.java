package com.yiyue.user.module.im.groupinfo;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.GroupBean;
import com.yiyue.user.model.vo.bean.daobean.GroupUserBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/17.
 */
public interface GroupInfoView extends IBaseView {
    void findGroupAllUserSuccess(List<GroupUserBean> list);

//    void getGroupAllUserFail();

    void findGroupSuccess(GroupBean group);

//    void findGroupFail();

    void addSingleToGroupSuccess();

    void removeSingleFromGroupSuccess();
}
