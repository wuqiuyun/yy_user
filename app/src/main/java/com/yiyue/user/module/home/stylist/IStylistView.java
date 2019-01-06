package com.yiyue.user.module.home.stylist;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.model.vo.result.AreaResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 2018/11/3.
 */

public interface IStylistView extends IBaseView {
    void getStylistSuccess(List<StylistBean> stylistBeanList);
    void getStylistFail();
    void getArea(ArrayList<AreaBean>areaBeans);
}
