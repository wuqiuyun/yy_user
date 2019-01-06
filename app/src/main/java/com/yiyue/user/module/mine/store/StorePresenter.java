package com.yiyue.user.module.mine.store;


import android.text.TextUtils;

import com.yiyue.user.api.CollectionApi;
import com.yiyue.user.api.StoreLisApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.requestbody.SortSearchRequesetBody;
import com.yiyue.user.model.vo.result.StoreListResult;
import com.yl.core.component.net.exception.ApiException;


/**
 * Created by zm on 2018/10/10.
 */
public class StorePresenter extends BasePresenter<IStoreView> {
    //我的收藏——门店列表
    public void getStoreCollection( String userId,String page,String size,String lat,String lng) {
        new CollectionApi().getStore(userId, page, size, lat, lng, new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().getStoreListFail();
            }
        });
    }

    /**
     *
     * @param sortSearchRequesetBody
     */
    public void sortSearch(SortSearchRequesetBody sortSearchRequesetBody) {
        new StoreLisApi().sortSearch(sortSearchRequesetBody,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getStoreListFail();

            }
        });
    }

    //搜索
    public void search(String search,String lng,String lat,String userId) {
        if (TextUtils.isEmpty(search)) {
            getMvpView().showToast("搜索内容不能为空");
            return;
        }
        new StoreLisApi().search(search,lng,lat,userId,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getStoreListFail();
            }
        });
    }
}
