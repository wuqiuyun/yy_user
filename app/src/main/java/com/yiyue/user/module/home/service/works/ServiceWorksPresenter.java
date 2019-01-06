package com.yiyue.user.module.home.service.works;

import com.yiyue.user.api.OpusApi;
import com.yiyue.user.api.StylistCardApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.HairListResult;
import com.yiyue.user.model.vo.result.ServiceOpusResult;
import com.yl.core.component.log.DLog;

import java.util.List;

/**
 * 作品（首页）
 * Created by wqy on 2018/11/15.
 */

public class ServiceWorksPresenter extends BasePresenter<IServiceWorksView> {

    // 获取全部作品
    public void getAllWorks(double lat, double lng, int page, int size, String type) {
        new OpusApi().getAllWorks(lat, lng, page, size, type,
                new YLRxSubscriberHelper<ServiceOpusResult>() {
                    @Override
                    public void _onNext(ServiceOpusResult result) {
                        if (null != result.getData()) {
                            getMvpView().getListSuccess(result.getData());
                        } else {
                            getMvpView().getListFail();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        if (getMvpView() != null) getMvpView().getListFail();
                    }
                });
    }

    //通过脸型/发长筛选作品
    public void getOpusType(List<Integer> feature, List<Integer> hairStyle, int page, int size) {
        new OpusApi().getOpusType(feature, hairStyle, page, size, new YLRxSubscriberHelper<ServiceOpusResult>() {
            @Override
            public void _onNext(ServiceOpusResult result) {
                if (null != result.getData() && result.getData().size() > 0) {
                    getMvpView().getListSuccess(result.getData());
                    DLog.e("通过脸型/发长筛选作品 **** " + result.getData().toString());
                } else {
                    getMvpView().getListFail();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView() != null) getMvpView().getListFail();
            }
        });

    }

    // 脸型
    public void getFeature() {
        new OpusApi().getFeature(new YLRxSubscriberHelper<HairListResult>() {
            @Override
            public void _onNext(HairListResult result) {
                getMvpView().getFeatureSuccess(result.getData());
            }
        });

    }

    // 发型
    public void getHairstyle() {
        new OpusApi().getHairstyle(new YLRxSubscriberHelper<HairListResult>() {
            @Override
            public void _onNext(HairListResult result) {
                getMvpView().getHairSuccess(result.getData());
            }
        });

    }

    public void opusCollection(String opusId, int type) {
        new StylistCardApi().opusCollection(opusId, type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().collectionSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                getMvpView().collectFail();
            }
        });
    }
}
