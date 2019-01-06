package com.yiyue.user.module.home.store.reservation;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.result.ChooseStylistResult;

/**
 * Created by wqy on 2018/11/9.
 */

public interface IStoreReservationView extends IBaseView {
    void getStylistsSuccess(ChooseStylistResult chooseStylistResult);
}
