package com.yiyue.user.module.mine.works.many;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.OpusBean;

/**
 * Created by zm on 2018/10/13.
 */
public interface IManyWorksView extends IBaseView {
    void getOpusListSuccess(OpusBean opusBean);

    void getOpusListFail();

    void getOpusListScreenSuc(OpusBean opusList);

}
