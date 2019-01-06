package com.yiyue.user.module.mine.stylist.details;

import com.yiyue.user.api.RecomUserApi;
import com.yiyue.user.api.StylistCardApi;
import com.yiyue.user.api.UserCouponApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.FindReCodeResult;
import com.yiyue.user.model.vo.result.StylistDetailsResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/10/11.
 */
public class StylistDetailsPresenter extends BasePresenter<IStylistDetailsView> {

    /**
     * 获取我的推荐码
     */
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
                else getMvpView().showToast("获取我的推荐码失败");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    
    //美发师详情
    public void getStylistDetails(String stylistId, String userId) {
        new StylistCardApi().getStylistDetails(stylistId, userId, new YLRxSubscriberHelper<StylistDetailsResult>() {
            @Override
            public void _onNext(StylistDetailsResult stylistDetailsResult) {
                getMvpView().getDetailsSuccess(stylistDetailsResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().getDetailsFail();
            }
        });
    }


    // 美发师收藏
    public void stylistCollection(String stylistId, int type) {
        new StylistCardApi().stylistCollection(stylistId, type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().collectionSuccess(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                getMvpView().collectFail();
            }
        });
    }

    // 领优惠券
    public void drawCoupon(String couponId, String type) {
        new UserCouponApi().drawCoupon(couponId, type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse response) {
                if (response.getCode() == 200) {
                    getMvpView().drawCouponSuccess();
                } else {
                    getMvpView().drawCouponFail();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView()!=null)getMvpView().drawCouponFail();
            }
        });
    }
}
