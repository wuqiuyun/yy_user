package com.yiyue.user.module.mine.settings.security.cashaccount;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CashAliBean;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.model.vo.bean.ServerItemBean;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public interface CashAccountManageView extends IBaseView{
    void onextractaLIAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void onUnBindSuccess();

    void onSuccess(ArrayList<ServerItemBean> serverItemBeans);
    void onFail(int type);

    void getUserInfoSuccess(GetApplyStatusBean getApplyStatusBean);

    void getUserInfoFail();

}
