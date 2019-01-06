package com.yiyue.user.module.mine.wallet.withdraw.account;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.CashAliBean;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/10/30.
 */
public interface IAccountWithdrawView extends IBaseView {

    void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void getUserInfoSuccess(GetApplyStatusBean getApplyStatusBean);

    void getUserInfoFail();

}
