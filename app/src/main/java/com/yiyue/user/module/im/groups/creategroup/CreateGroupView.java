package com.yiyue.user.module.im.groups.creategroup;

import com.yiyue.user.base.mvp.IBaseView;
import com.yiyue.user.model.vo.bean.daobean.GroupBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/16.
 */
public interface CreateGroupView extends IBaseView {
    //创建群成功
    void onSuccess(GroupBean groupbean);

    /**
     * 文件上传成功
     *
     * @param filePaths
     */
    void onUploadFileSuccess(List<String> filePaths);
}
