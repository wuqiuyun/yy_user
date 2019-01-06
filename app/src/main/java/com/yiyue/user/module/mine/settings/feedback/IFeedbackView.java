package com.yiyue.user.module.mine.settings.feedback;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.InitAppFeedbackBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface IFeedbackView extends IBaseView {
    void initAppFeedbackSuc(InitAppFeedbackBean bean);

    void submitSuc();
}
