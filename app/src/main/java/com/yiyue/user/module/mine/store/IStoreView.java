package com.yiyue.user.module.mine.store;


import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.StoreBean;

import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public interface IStoreView extends IBaseView {
    void getStoreListSuccess(List<StoreBean> list);
    void getStoreListFail();

}
