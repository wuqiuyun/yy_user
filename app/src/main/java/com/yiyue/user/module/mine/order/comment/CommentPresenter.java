package com.yiyue.user.module.mine.order.comment;

import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.FileApi;
import com.yiyue.user.api.OrderCommentApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.bean.OrderCommentBean;
import com.yiyue.user.model.vo.requestbody.OrderCommentRequestBody;
import com.yiyue.user.model.vo.result.GetOrderCommentResult;
import com.yiyue.user.model.vo.result.UploadImageResult;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/11/9.
 */
public class CommentPresenter extends BasePresenter<ICommentView>{

    /**
     * 获取订单评价
     * @param orderId
     */
    public void getOrderComment(String orderId) {
        new OrderCommentApi().getAllComment(orderId, new YLRxSubscriberHelper<GetOrderCommentResult>() {
            @Override
            public void _onNext(GetOrderCommentResult baseResponse) {
                getMvpView().onGetOrderComment(baseResponse.getData());
            }
        });
    }

    /**
     * 提交订单评价
     * @param requestBody
     */
    public void submitComment(Context context, OrderCommentRequestBody requestBody) {
        if (!requestBody.checkParams()) {
            return;
        }

        new OrderCommentApi().submitComment(requestBody, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onSubmitCommentSuccess();
            }
        });
    }

    /**
     * 上传文件
     * @param filePaths 文件地址
     */
    public void uploadImage(List<String> filePaths,Context context) {
        if (filePaths.size()==0) {
            getMvpView().showToast("文件不存在.");
            return;
        }
        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>(context,true) {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().onUploadFileSuccess(baseResponse.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onUploadFileFail();
            }
        });
    }
}
