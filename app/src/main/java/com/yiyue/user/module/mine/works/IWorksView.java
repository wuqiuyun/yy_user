package com.yiyue.user.module.mine.works;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.WorksBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
public interface IWorksView extends IBaseView {
    void onSuccess(ArrayList<WorksBean> worksBeans);
    void onFail();
}
