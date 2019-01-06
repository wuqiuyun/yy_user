package com.yiyue.user.module.mine.works;

import com.yiyue.user.api.CollectionApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.GetOpusResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/10/10.
 */
public class WorksPresenter extends BasePresenter<IWorksView> {

    //作品列表
    public void getOpus( String userId,String page,String size) {
        new CollectionApi().getOpus(userId, page, size, new YLRxSubscriberHelper<GetOpusResult>() {
            @Override
            public void _onNext(GetOpusResult result) {
                    getMvpView().onSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onFail();
            }
        });
    }

}
