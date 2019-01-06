package com.yiyue.user.module.home.store;

import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.model.vo.bean.StoreInfoBean;
import com.yiyue.user.model.vo.bean.StoreServerScopeBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.model.vo.result.ContactResult;
import com.yiyue.user.model.vo.result.StoreScoreResult;
import com.yiyue.user.model.vo.result.StylistResult;

import java.util.List;

/**
 * Created by wqy on 2018/11/8.
 */
public interface IStoreManagerView extends IBaseView {
    void getInfoSuccess(StoreInfoBean storeInfoBean);

    void getStylistSuccess(List<StylistBean> stylistBeans);

    void getContactSuccess(ContactResult contactResult);

    void getStoreScoreSuccess(StoreScoreResult storeScoreResult);

    void getStoreServerScopeSuccess(StoreServerScopeBean storeServerScopeBean);

    void collectionSuccess(BaseResponse response);

    void collectFail();

    void findReCodeSuc(ReCodeBean recode);
}
