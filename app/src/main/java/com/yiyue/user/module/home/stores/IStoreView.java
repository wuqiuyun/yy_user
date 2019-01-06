package com.yiyue.user.module.home.stores;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.ServerTypeBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/3.
 */

public interface IStoreView extends IBaseView {
    void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans);
    void getArea(ArrayList<AreaBean> areaBeans);
}
