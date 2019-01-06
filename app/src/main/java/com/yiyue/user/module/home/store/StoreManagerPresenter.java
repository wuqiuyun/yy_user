package com.yiyue.user.module.home.store;

import com.yiyue.user.api.RecomUserApi;
import com.yiyue.user.api.StoreManageApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.ContactResult;
import com.yiyue.user.model.vo.result.FindReCodeResult;
import com.yiyue.user.model.vo.result.StoreInfoResult;
import com.yiyue.user.model.vo.result.StoreScoreResult;
import com.yiyue.user.model.vo.result.StoreServerScopeResult;
import com.yiyue.user.model.vo.result.StylistResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by wqy on 2018/11/09.
 */
public class StoreManagerPresenter extends BasePresenter<IStoreManagerView> {

    //获取我的推荐码
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
            }
        });
    }
    
    // 门店名 位置等信息
    public void getStoreInfo(double lat, double lng, String storeId, String userId) {
        new StoreManageApi().getStoreInfo(lat, lng, storeId, userId, new YLRxSubscriberHelper<StoreInfoResult>() {
            @Override
            public void _onNext(StoreInfoResult storeInfoResult) {
                getMvpView().getInfoSuccess(storeInfoResult.getData());
            }
        });
    }

    // 入驻/签约美发师
    public void getNexusStyScrool(String nexus, String storeId, String userId) {
        new StoreManageApi().getNexusStyScrool(nexus, storeId, userId, new YLRxSubscriberHelper<StylistResult>() {
            @Override
            public void _onNext(StylistResult stylistResult) {
                getMvpView().getStylistSuccess(stylistResult.getData());
            }
        });
    }

    // 门店联系方式
    public void contact(String storeId, String userId) {
        new StoreManageApi().contact(storeId, userId, new YLRxSubscriberHelper<ContactResult>() {
            @Override
            public void _onNext(ContactResult contactResult) {
                getMvpView().getContactSuccess(contactResult);
            }
        });
    }

    // 门店顾客评价
    public void getStoreScore(String userId, String storeId) {
        new StoreManageApi().getStoreScore( userId, storeId, new YLRxSubscriberHelper<StoreScoreResult>() {
            @Override
            public void _onNext(StoreScoreResult storeScoreResult) {
                getMvpView().getStoreScoreSuccess(storeScoreResult);
            }
        });
    }

    // 门店服务范围
    public void getStoreServerScope( String userId, String storeId) {
        new StoreManageApi().getStoreServerScope(userId, storeId, new YLRxSubscriberHelper<StoreServerScopeResult>() {
            @Override
            public void _onNext(StoreServerScopeResult storeServerScopeResult) {
                getMvpView().getStoreServerScopeSuccess(storeServerScopeResult.getData());
            }
        });
    }

    // 门店收藏/取消
    public void updateCollectionType(int isCollection, String storeId, String userId) {
        new StoreManageApi().updateCollectionType(isCollection, storeId, userId, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse response) {
                getMvpView().collectionSuccess(response);
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                getMvpView().collectFail();
            }
        });
    }
}
