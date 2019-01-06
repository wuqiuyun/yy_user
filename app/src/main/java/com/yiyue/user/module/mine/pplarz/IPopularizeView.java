package com.yiyue.user.module.mine.pplarz;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.FindInviteBean;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.model.vo.result.RecommendResult;

/**
 * Created by zm on 2018/12/28.
 */
public interface IPopularizeView extends IBaseView {
    void setIncomeData(RecommendResult.Data data);
    void findInviteSuc(FindInviteBean findInvite);
    void findReCodeSuc(ReCodeBean reCode);
}
