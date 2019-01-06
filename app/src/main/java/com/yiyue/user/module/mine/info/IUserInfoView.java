package com.yiyue.user.module.mine.info;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.UserCenterInfoBean;

/**
 * Created by zm on 2018/9/29.
 */
public interface IUserInfoView extends IBaseView{
    void getUserInfoSuc(UserCenterInfoBean userCenterInfo);
    
    void getUserInfoFail();
}
