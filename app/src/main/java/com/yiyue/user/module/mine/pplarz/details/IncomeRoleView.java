package com.yiyue.user.module.mine.pplarz.details;


import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.result.RecommendUserListResult;

/**
 * Created by zm on 2019/1/4.
 */
public interface IncomeRoleView extends IBaseView {
     void setData(RecommendUserListResult.Data data);
     void onLoadFail();
}
