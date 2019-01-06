package com.yiyue.user.module.mine.info.editinfo;

import android.content.Context;
import android.text.TextUtils;

import com.yiyue.user.api.FileApi;
import com.yiyue.user.api.UserCenterInfoApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.UploadImageResult;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lizhuo on 2018/11/7.
 */
public class EditInfoPresenter extends BasePresenter<IEditInfoView> {

    /**
     * 修改性别
     * @param gender 1男 2女
     */
    public void updateUserSex(int gender){
        new UserCenterInfoApi().updateUserSex(String.valueOf(gender), new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {

            }
        });
    }

    /**
     * 修改发型
     * @param hair //1长发 2短发 3中发
     */
    public void updateHaidStyle(int hair){
        new UserCenterInfoApi().updateHaidStyle(String.valueOf(hair), new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateHaidStyleSuc();
            }
        });
    }

    /**
     * 修改生日
     * @param birthday
     */
    public void updateBirthday(String birthday){
        new UserCenterInfoApi().updateBirthday(birthday, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("修改生日成功");
            }
        });
    }

    /**
     * 修改爱好
     * @param hobby
     */
    public void updateHobby(String hobby){
        new UserCenterInfoApi().updateHobby(hobby, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("修改爱好成功");
            }
        });
    }
    /**
     * 修改头像
     * @param headImg
     */
    public void updateHeadImg(String headImg){
        new UserCenterInfoApi().updateHeadImg(headImg, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                AccountManager.getInstance().setHeadImg(headImg);
                getMvpView().updateHeadImgSuc();
            }

        });
    }

    /**
     * 上传文件
     * @param filePath 文件地址
     */
    public void uploadImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            getMvpView().showToast("文件不存在.");
            return;
        }
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>() {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                DLog.d("图片上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }
        });
    }

}
