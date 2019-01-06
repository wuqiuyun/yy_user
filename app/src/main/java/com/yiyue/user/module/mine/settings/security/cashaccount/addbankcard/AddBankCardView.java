package com.yiyue.user.module.mine.settings.security.cashaccount.addbankcard;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.BankCardBean;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public interface AddBankCardView extends IBaseView{
    void onBankSuccess(ArrayList<BankCardBean> bankCardBeans);

    void onBindSuccess();

    void onFail(int type);
}
