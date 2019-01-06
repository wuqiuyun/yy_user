package com.yiyue.user.module.im.chat;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.GroupApi;
import com.yiyue.user.api.RedBagApi;
import com.yiyue.user.api.SettingsApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.RedBagSendResult;
import com.yiyue.user.model.vo.result.findGroupAllUserResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/19.
 */
public class ChatPresenter extends BasePresenter<ChatView> {

    public void findRedBagReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().findRedBag(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().findRedBagSuccess(result);
            }
        });
    }

    public void receiveRedBagReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().receiveRedBag(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().receiveRedBagSuc(result);
            }
        });
    }

    public void findTransferReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().findTransfer(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().findTransferSuccess(result);
            }
        });
    }

    public void receiveTransferReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().receiveTransfer(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().receiveTransferSuc(result);
            }
        });
    }

    //支付密码状态
    public void initPayWord(Context context) {
        new SettingsApi().initPayWord(new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().oninitPayWordInfoSuccess(CoinInfoResult.getData()+"");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

}
