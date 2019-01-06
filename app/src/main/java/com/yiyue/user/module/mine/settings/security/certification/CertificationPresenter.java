package com.yiyue.user.module.mine.settings.security.certification;

import android.content.Context;

import com.yiyue.user.api.FileApi;
import com.yiyue.user.api.UserAuthApplyApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.UserAuthApplyRequestBody;
import com.yiyue.user.model.vo.result.UploadImageResult;

import java.util.List;

/**
 * Created by lvlong on 2018/9/20.
 */
public class CertificationPresenter extends BasePresenter<ICertificationView> {

    /**
     * 提交认证信息
     * @param requestBody 认证信息
     */
    public void submitCertiData(Context context, UserAuthApplyRequestBody requestBody) {
        if (!requestBody.checkParams()) {
            return;
        }
        new UserAuthApplyApi().addOrUpdate(requestBody, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onSubmitCertiDataSuccess();
            }
        });
    }

    /**
     * 上传文件
     * @param filePaths 文件地址
     */
    public void uploadImage(Context context, List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) {
            ToastUtils.shortToast("文件不存在");
            return;
        }

        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>(context, true) {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().showToast("上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData());
            }
        });
    }
}
