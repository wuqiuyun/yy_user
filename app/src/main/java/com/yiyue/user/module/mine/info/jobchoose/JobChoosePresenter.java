package com.yiyue.user.module.mine.info.jobchoose;

import com.yiyue.user.api.UserCenterInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by Lizhuo on 2018/11/9.
 */
public class JobChoosePresenter extends BasePresenter<IJobChooseView> {
    public void updateJob(String job){
        new UserCenterInfoApi().updateJob(job, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateJobSuc();
            }

            @Override
            public void _onError(ApiException error) {
                DLog.e(error.message);
                getMvpView().showToast("修改职业失败");
            }
        });
    }

    public void updateFace(int faceId){
        new UserCenterInfoApi().updateFaceture(String.valueOf(faceId), new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateFaceSuc();
            }

            @Override
            public void _onError(ApiException error) {
                DLog.e(error.message);
                getMvpView().showToast("修改脸型失败");
            }
        });
    }
}
