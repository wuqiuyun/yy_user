package com.yiyue.user.module.im.groupmembers;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.GroupUserBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/18.
 */
public interface GroupMembersView extends IBaseView {
    void findGroupAllUserSuccess(List<GroupUserBean> list);

    void deleteMemberSuccess();
}
