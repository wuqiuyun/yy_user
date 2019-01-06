package com.yiyue.user.module.mine.info.editinfo;

import com.yiyue.user.base.mvp.IBaseView;

/**
 * Created by Lizhuo on 2018/11/7.
 */
public interface IEditInfoView extends IBaseView {

    /**
     * 文件上传成功
     * @param filePath
     */
    void onUploadFileSuccess(String filePath);

    /**
     * 发型修改成功
     */
    void updateHaidStyleSuc();

    /**
     * 头像修改成功
     */
    void updateHeadImgSuc();
}
