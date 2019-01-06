package com.yiyue.user.module.home.service.leaderboard;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.daobean.RankBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/2.
 */

public interface ILeaderBoardView extends IBaseView {
    void getRankSuccess(ArrayList<RankBean> rankList);

    void getRankFail();

    void getAreaSuccess(ArrayList<AreaBean> areaBeans);
}
