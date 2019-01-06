package com.yiyue.user.module.mine.pplarz.role;


import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.IncomeRecordBean;

import java.util.ArrayList;

/**
 * Created by zm on 2019/1/4.
 */
public interface IncomeRoleDetailsView extends IBaseView {
    void setData(ArrayList<IncomeRecordBean> datas);
}
