package com.yiyue.user.module.home;

import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.BannerBean;
import com.yiyue.user.model.vo.bean.StoreRecommendBean;
import com.yiyue.user.model.vo.bean.StylistRecommendBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/9/19.
 */
public interface IHomeView extends IBaseView {

    void onGetStoreListSuccess(ArrayList<StoreRecommendBean> list);

    void onGetStylistListSuccess(ArrayList<StylistRecommendBean> list);

    void onGetAreaSuccess(ArrayList<AreaBean> areaBeans);

    void onGetListFail();

    void onGetLocationSuccess();

    void collectionSuccess();

    void collectFail();

    void onGetBannerSuccess(ArrayList<BannerBean> bannerBean);

    void onGetBannerFail();
    void onChangesaveSuccess();

}
