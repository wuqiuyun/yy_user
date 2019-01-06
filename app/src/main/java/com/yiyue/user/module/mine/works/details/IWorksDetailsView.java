package com.yiyue.user.module.mine.works.details;

import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.OpusDetailsBean;
import com.yiyue.user.model.vo.bean.ReCodeBean;

/**
 * Created by zm on 2018/10/12.
 */
public interface IWorksDetailsView extends IBaseView {
    void getOpusDetailSuccess(OpusDetailsBean opusDetailsBean);

    void collectionSuccess(BaseResponse baseResponse);

    void collectFail();

    void findReCodeSuc(ReCodeBean reCode);
}
