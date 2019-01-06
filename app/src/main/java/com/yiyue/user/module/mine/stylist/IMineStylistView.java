package com.yiyue.user.module.mine.stylist;



import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.StylistBean;

import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public interface IMineStylistView extends IBaseView {
    void getStylistSuccess(List<StylistBean> stylistBeanList);
    void getStylistFail();
}
