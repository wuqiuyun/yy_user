package com.yiyue.user.module.im.sysnotice;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yl.core.util.DateUtil;
import com.yiyue.user.R;
import com.yiyue.user.model.vo.bean.daobean.SysMessageBean;

/**
 */
public class SysNoticeAdapter extends BaseQuickAdapter<SysMessageBean, BaseViewHolder> {

    public SysNoticeAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SysMessageBean msg) {
        if (msg != null) {
            baseViewHolder.setText(R.id.tv_notice_title, msg.getTitle());
            baseViewHolder.setText(R.id.tv_notice_time, DateUtil.getTime(msg.getCreatetime(), DateUtil.FORMAT_HM));
            baseViewHolder.setText(R.id.tv_notice_desc, msg.getContent());
        }
    }
}
