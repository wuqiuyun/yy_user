package com.yiyue.user.module.mine.settings.security;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.model.vo.bean.SecurityInfoBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface ISecurityView extends IBaseView {
    //获取账户安全信息成功
    void onAccountInfoSuccess(SecurityInfoBean bean);

    //绑定微信号成功
    void onBindWxSuccess();
    
    //获取认证审核状态成功
    void getApplyStatusSuc(GetApplyStatusBean status);
    
    //获取审核状态失败
    void getApplyStatusFail();

    //获取账户支付密码信息成功
    void oninitPayWordInfoSuccess(String json);

    //获取账户支付密码信息失败
    void oninitPayWordInfoFail();


}
