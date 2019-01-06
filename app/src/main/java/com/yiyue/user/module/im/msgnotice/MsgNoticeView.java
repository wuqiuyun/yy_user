package com.yiyue.user.module.im.msgnotice;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.BulletinBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface MsgNoticeView extends IBaseView {
    void getMsgListSuc(List<BulletinBean> list);
    
    void getMsgListFail();
}
