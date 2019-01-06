package com.yiyue.user.module.mine.pplarz.qrcode;

import com.yiyue.user.api.BasicSettingApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;

/**
 * Created by zm on 2018/12/29.
 */
public class InviteQRCodePresenter extends BasePresenter<InviteQRCodeView> {

    public void getWXShareQrCode(String inviteCode) {
        new BasicSettingApi().getWXShareQrCode(inviteCode, new YLRxSubscriberHelper<BaseResponse<String>>() {
            @Override
            public void _onNext(BaseResponse<String> result) {
                getMvpView().setShareQrCodeUrl(result.getData());
            }
        });
    }
}
