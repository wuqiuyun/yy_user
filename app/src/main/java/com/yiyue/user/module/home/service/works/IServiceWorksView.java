package com.yiyue.user.module.home.service.works;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.HairListBean;
import com.yiyue.user.model.vo.bean.ServiceOpusBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/1.
 */

public interface IServiceWorksView extends IBaseView {
    void getListSuccess(ArrayList<ServiceOpusBean> opusBeans);

    void getListFail();

    void getHairSuccess(ArrayList<HairListBean> hairListBeans);

    void getFeatureSuccess(ArrayList<HairListBean> hairListBeans);

    void collectionSuccess();

    void collectFail();

}
