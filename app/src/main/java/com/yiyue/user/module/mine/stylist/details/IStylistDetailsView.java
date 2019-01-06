package com.yiyue.user.module.mine.stylist.details;

import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;

/**
 * Created by zm on 2018/10/11.
 */
public interface IStylistDetailsView extends IBaseView {
    void getDetailsSuccess(StylistDetailsBean stylistDetailsBean);
    
    void getDetailsFail();

    void collectionSuccess(BaseResponse baseResponse);

    void collectFail();

    void drawCouponSuccess();

    void drawCouponFail();

    void findReCodeSuc(ReCodeBean reCode);
}
