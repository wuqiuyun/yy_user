package com.yiyue.user.module.home.evaluation;

import android.content.Context;

import com.yiyue.user.api.EvaluationApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.EvaluationResult;

/**
 * 评价
 * Created by lvlong on 2018/10/11.
 */
public class EvaluationManagerPresenter extends BasePresenter<IEvaluationManagerView> {

    // 查看门店评论
    public void getStoreCommentList(String storeId, int page, int size, Context context) {
        new EvaluationApi().getStoreCommentList(storeId, page, size,
                new YLRxSubscriberHelper<EvaluationResult>(context,true) {
                    @Override
                    public void _onNext(EvaluationResult result) {
                        if (null != result.getData() && result.getData().size() > 0) {
                            getMvpView().getEvaluationSuccess(result.getData());
                        } else {
                            getMvpView().getEvaluationFail();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        if (null != getMvpView()) getMvpView().getEvaluationFail();
                    }
                });
    }

    // 查看美发师评论
    public void getStylistCommentList(String stylistId, int page, int size, Context context) {
        new EvaluationApi().getStylistCommentList(stylistId, page, size,
                new YLRxSubscriberHelper<EvaluationResult>(context,true) {
                    @Override
                    public void _onNext(EvaluationResult result) {
                        if (null != result.getData() && result.getData().size() > 0) {
                            getMvpView().getEvaluationSuccess(result.getData());
                        } else {
                            getMvpView().getEvaluationFail();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        if (null != getMvpView()) getMvpView().getEvaluationFail();
                    }
                });
    }
}
