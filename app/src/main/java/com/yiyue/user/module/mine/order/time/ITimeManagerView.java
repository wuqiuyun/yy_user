package com.yiyue.user.module.mine.order.time;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.TimeManagerBean;

/**
 * Created by zm on 2018/11/12.
 */
public interface ITimeManagerView extends IBaseView{
    void setDatas(TimeManagerBean data);

    /**
     * 更改时间成功回调
     */
    void updateOrderTimeSuccess();
}
