package com.yiyue.user.module.mine.coupon.usage;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.UsageBean;

import java.util.List;


/*
 * Create by lvlong on  2018/12/4
 */

public interface UsageView extends IBaseView {
    void setData(List<UsageBean> usageBeans);
    void onLoadFail();
}
