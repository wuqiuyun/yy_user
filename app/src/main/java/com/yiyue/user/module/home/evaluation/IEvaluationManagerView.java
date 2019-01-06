package com.yiyue.user.module.home.evaluation;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.EvaluationBean;

import java.util.ArrayList;

/**
 * Created by lvlong on 2018/10/11.
 */
public interface IEvaluationManagerView extends IBaseView {

    void getEvaluationSuccess(ArrayList<EvaluationBean> evaluationBeans);

    void getEvaluationFail();

}
