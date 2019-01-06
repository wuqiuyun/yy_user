package com.yiyue.user.module.mine.order.comment;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.OrderCommentBean;

import java.util.List;

/**
 * Created by zm on 2018/11/9.
 */
public interface ICommentView extends IBaseView {
    void onGetOrderComment(OrderCommentBean orderCommentBean);
    void onSubmitCommentSuccess();
    void onUploadFileSuccess(List<String> filePaths);
    void onUploadFileFail();
}
