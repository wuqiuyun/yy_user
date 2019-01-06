package com.yiyue.user.module.mine.stylist;

import com.yiyue.user.api.CollectionApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.StylistResult;
import com.yl.core.component.net.exception.ApiException;


/**
 * Created by zm on 2018/10/10.
 */
public class MineStylistPresenter extends BasePresenter<IMineStylistView> {
    //我的收藏——美发师列表
    public void getStylist( String userId,int page,int size) {
        new CollectionApi().getStylist(userId, page, size, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult result) {
                getMvpView().getStylistSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getStylistFail();
            }
        });
    }

}
