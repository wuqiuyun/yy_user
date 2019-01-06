package com.yiyue.user.module.mine.info;

import com.yiyue.user.api.UserCenterInfoApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.UserCenterInfoResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/29.
 */
public class UserInfoPresenter extends BasePresenter<IUserInfoView>{
    
    public void getUserInfo(){
        new UserCenterInfoApi().getUserCenterInfo(new YLRxSubscriberHelper<UserCenterInfoResult>() {
            @Override
            public void _onNext(UserCenterInfoResult userCenterInfoResult) {
                if(null != userCenterInfoResult.getData()) getMvpView().getUserInfoSuc(userCenterInfoResult.getData());
                else getMvpView().getUserInfoFail();
            }

            @Override
            public void _onError(ApiException error) {
                DLog.e(error.message);
                getMvpView().getUserInfoFail();
            }
        });
    }
}
