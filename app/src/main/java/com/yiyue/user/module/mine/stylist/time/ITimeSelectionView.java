package com.yiyue.user.module.mine.stylist.time;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.TimeManagerBean;

/**
 * Created by wqy on 2018/11/6.
 */

public interface ITimeSelectionView extends IBaseView {
    void setDatas(TimeManagerBean data);
}
