package com.yiyue.user.module.mine.stylist.selectstore;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.StoreBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/6.
 */

public interface ISelectStoreView extends IBaseView{
    void onSuccess(ArrayList<StoreBean> storeBeans);
}
