package com.yiyue.user.module.im.msgnotice.msgdetail;

import com.yiyue.user.api.BulletinApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.model.vo.result.BulletinDetailResult;

/**
 * Created by Lizhuo on 2018/11/20.
 */
public class MsgDetailPresenter extends BasePresenter<IMsgDetailView> {
    public void getDetail(long id){
        if (id == 0){
            ToastUtils.shortToast("公告获取有误");
            return;
        }
        
        new BulletinApi().findBulletin(String.valueOf(id), new YLRxSubscriberHelper<BulletinDetailResult>() {
            @Override
            public void _onNext(BulletinDetailResult result) {
                if (result.getData() != null){
                    getMvpView().getDetailSuc(result.getData());
                }
            }
        });
    }
}
