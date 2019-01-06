package com.yiyue.user.module.mine.works.details;

import android.text.TextUtils;

import com.yiyue.user.api.RecomUserApi;
import com.yiyue.user.api.StylistCardApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.FindReCodeResult;
import com.yiyue.user.model.vo.result.OpusDetailsResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created wqy zm on 2018/11/11.
 */
public class WorksDetailsPresenter extends BasePresenter<IWorksDetailsView> {

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
    
    /**
     * 作品详情
     *
     * @param opusId
     * @param userId
     */
    public void getOpusDetail(String opusId, String userId) {
        new StylistCardApi().getOpusDetail(opusId, userId, new YLRxSubscriberHelper<OpusDetailsResult>() {
            @Override
            public void _onNext(OpusDetailsResult opusDetailsResult) {
                getMvpView().getOpusDetailSuccess(opusDetailsResult.getData());
            }
        });
    }

    /**
     * 作品收藏
     * @param opusId
     * @param type  收藏类型 0:取消，1:收藏
     */
    public void opusCollection(String opusId, int type) {
        new StylistCardApi().opusCollection(opusId,type, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().collectionSuccess(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                if (getMvpView()!=null)getMvpView().collectFail();
            }
        });
    }


    //浏览美发师作品
    public void opusPageview(String opusId){
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }

        new StylistCardApi().opusCount(opusId, "2", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                DLog.d("StylistCardApi","增加浏览数成功");
            }
        });
    }

    //转发分享美发师作品
    public void opusRepost(String opusId){
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }

        new StylistCardApi().opusCount(opusId, "1", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                DLog.d("StylistCardApi","增加转发数成功");
            }
        });
    }
}
