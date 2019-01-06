package com.yiyue.user.module.mine.works.many;

import android.text.TextUtils;

import com.yiyue.user.api.StylistCardApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.OpusResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/11/11.
 */
public class ManyWorksPrenster extends BasePresenter<IManyWorksView> {

    // 作品集
    public void getOpusList(String stylistId, String userId) {
        new StylistCardApi().getOpusList(stylistId, userId, new YLRxSubscriberHelper<OpusResult>() {
            @Override
            public void _onNext(OpusResult opusResult) {
                if (opusResult.getData()!=null)  getMvpView().getOpusListSuccess(opusResult.getData());
               else getMvpView().getOpusListFail();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                getMvpView().getOpusListFail();
            }
        });
    }

    public void getOpusListScreen(String stylistId, long screenId, int type) {
        if (TextUtils.isEmpty(stylistId)) {
            getMvpView().showToast("美发师Id为空");
            return;
        }

        new StylistCardApi().getOpusListScreen(stylistId, String.valueOf(screenId), String.valueOf(type), new YLRxSubscriberHelper<OpusResult>() {
            @Override
            public void _onNext(OpusResult opusListResult) {
                if (null != opusListResult.getData()) getMvpView().getOpusListScreenSuc(opusListResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
            }
        });
    }
}
