package com.yiyue.user.module.home.service.leaderboard;

import com.yiyue.user.api.AreaApi;
import com.yiyue.user.api.RankApi;
import com.yiyue.user.api.UserLoacationApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.requestbody.RankRequestBody;
import com.yiyue.user.model.vo.requestbody.SaveLocationBody;
import com.yiyue.user.model.vo.result.AreaResult;
import com.yiyue.user.model.vo.result.RankResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/15.
 */

public class LeaderBoardPresenter extends BasePresenter<ILeaderBoardView> {
    // 通过用户ID获取用户区域
    public void getAreaByUserId(String userId) {
        new AreaApi().getAreaByUserId(userId, new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                if (null != result.getData()) {
                    getMvpView().getAreaSuccess(result.getData());
                } else {
                    getMvpView().showToast("定位区域失败!");
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView() != null) getMvpView().showToast("定位区域失败!");
            }
        });
    }

    // 筛选排行榜
    public void getRankAll(RankRequestBody body) {
        new RankApi().getRankAll(body, new YLRxSubscriberHelper<RankResult>() {
            @Override
            public void _onNext(RankResult result) {
                if (null != result.getData()) {
                    getMvpView().getRankSuccess(result.getData());
                } else {
                    getMvpView().getRankFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView() != null) getMvpView().getRankFail();
            }
        });
    }
}
