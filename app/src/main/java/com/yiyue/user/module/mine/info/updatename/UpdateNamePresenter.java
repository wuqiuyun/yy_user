package com.yiyue.user.module.mine.info.updatename;

import com.yiyue.user.api.UserCenterInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;

/**
 * Created by Lizhuo on 2018/10/31.
 */
public class UpdateNamePresenter extends BasePresenter<IUpdateNameView> {

    //修改昵称
    public void updateStylistName(String name) {

        new UserCenterInfoApi().updateUserName(name, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateStylistNameSuc();
            }
        });
    }
}
