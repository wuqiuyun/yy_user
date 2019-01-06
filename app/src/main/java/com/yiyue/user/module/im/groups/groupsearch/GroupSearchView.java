package com.yiyue.user.module.im.groups.groupsearch;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.GroupBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/15.
 */
public interface GroupSearchView extends IBaseView {
    void searchGroupSuccess(List<GroupBean> list);

    void searchGroupFail();
}
