package com.yiyue.user.module.login.information;

import android.text.TextUtils;

import com.yiyue.user.api.FileApi;
import com.yiyue.user.api.UserApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.result.UploadImageResult;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lvlong on 2018/9/27.
 */
public class PerfectInformationPresenter extends BasePresenter<IPerfectInformationView> {

    /**
     * 完善用户信息
     * @param gender
     * @param headPortrait
     * @param password
     * @param confirmPwd
     */
    public void doUserData(String nickName, int gender, String headPortrait, String password, String confirmPwd) {
        if (TextUtils.isEmpty(headPortrait)) {
            getMvpView().showToast("头像不能为空.");
            return;
        }

        if (TextUtils.isEmpty(nickName)) {
            getMvpView().showToast("用户名不能为空.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().showToast("密码不能为空.");
            return;
        }
        if (password.length()< 6) {
            getMvpView().showToast("密码不能小于6位数");
            return;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            getMvpView().showToast("确认密码不能为空.");
            return;
        }
        if (!password.equals(confirmPwd)) {
            getMvpView().showToast("两次密码输入不一致.");
            return;
        }

        new UserApi().doUserData(nickName, gender, headPortrait, password, new YLRxSubscriberHelper<BaseResponse>(){

            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onDoUserDataSuccess();
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
                getMvpView().showToast("图片上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }
        });
    }
}
