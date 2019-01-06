package com.yiyue.user.module.mine.settings.security.certification;


import android.content.Intent;
import android.support.annotation.IntDef;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityCertificationBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.requestbody.UserAuthApplyRequestBody;
import com.yiyue.user.util.BitmapUtils;
import com.yiyue.user.util.FilePathUtil;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.util.compressutil.CompressPicUtil;
import com.yiyue.user.util.compressutil.OnCompressListener;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.SelectPhotoView;
import com.yiyue.user.widget.bottomview.base.BaseBottomView;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户认证
 * <p>
 * Create by zm on 2018/11/2
 */
@CreatePresenter(CertificationPresenter.class)
public class CertificationActivity extends BaseMvpAppCompatActivity<ICertificationView,CertificationPresenter>implements ICertificationView,ClickHandler{

    private ActivityCertificationBinding mBinding;

    private BaseBottomView mBaseBottomView;

    private String cardFrontPath; // 身份证正面
    private String cardReversePath; // 身份证反面
    private String hardCardPath; // 手执证件照

    private static final int CARD_FRONT = 0; // 身份证正面
    private static final int CARD_REVERSE = 1; // 身份证反面
    private static final int CARD_HARD = 2; // 手执证件照

    @IntDef({CARD_FRONT, CARD_REVERSE, CARD_HARD})
    @interface ImageType{}
    @ImageType int mImageType;

    @Override
    protected void init() {
        mBinding = (ActivityCertificationBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setLeftClickListener(view -> finish());
        StatusBarUtil.setLightMode(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_certification;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    if (mBaseBottomView == null) return;
                    String imagePath = ((SelectPhotoView) mBaseBottomView).getImagePath();
                    switch (mImageType) {
                        case CARD_FRONT:
                            mBinding.ivCertificateFront.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCertificateFront.getWidth(), mBinding.ivCertificateFront.getHeight()));
                            break;
                        case CARD_REVERSE:
                            mBinding.ivCertificateReverse.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCertificateReverse.getWidth(), mBinding.ivCertificateReverse.getHeight()));
                            break;
                        case CARD_HARD:
                            mBinding.ivHandheldCertificate.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivHandheldCertificate.getWidth(), mBinding.ivHandheldCertificate.getHeight()));
                            break;
                    }
                    compressPicAndUpload(imagePath);
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    if (data == null || data.getData() == null) return;
                    switch (mImageType) {
                        case CARD_FRONT:
                            mBinding.ivCertificateFront.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCertificateFront.getWidth(), mBinding.ivCertificateFront.getHeight()));
                            break;
                        case CARD_REVERSE:
                            mBinding.ivCertificateReverse.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCertificateReverse.getWidth(), mBinding.ivCertificateReverse.getHeight()));
                            break;
                        case CARD_HARD:
                            mBinding.ivHandheldCertificate.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivHandheldCertificate.getWidth(), mBinding.ivHandheldCertificate.getHeight()));
                            break;
                    }
                    compressPicAndUpload(FilePathUtil.getPath(data.getData()));
                    break;
            }
        }
    }

    /**
     * 压缩后上传
     *
     * @param filePath
     */
    private void compressPicAndUpload(String filePath) {
        CompressPicUtil.with()
                .load(filePath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(file.getPath());
                        getMvpPresenter().uploadImage(CertificationActivity.this, filePaths);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用 上传原图
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(filePath);
                        getMvpPresenter().uploadImage(CertificationActivity.this, filePaths);
                    }
                }).launch();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_handheld_certificate: //上传手持证件照
                mImageType = CARD_HARD;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_certificate_front: //上传证件正面
                mImageType = CARD_FRONT;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_certificate_reverse: //上传证件反面
                mImageType = CARD_REVERSE;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.btn_next: //下一步
                UserAuthApplyRequestBody requestBody = new UserAuthApplyRequestBody.Builder()
                        .realname(mBinding.etUserName.getText().toString().trim())
                        .cardNo(mBinding.etCardno.getText().toString().trim())
                        .cardFront(cardFrontPath)
                        .cardReverse(cardReversePath)
                        .hardCard(hardCardPath)
                        .userId(AccountManager.getInstance().getUserId())
                        .build();
                getMvpPresenter().submitCertiData(this, requestBody);
                break;
        }
    }

    @Override
    public void onSubmitCertiDataSuccess() {
        showToast("提交成功，请等待审核完成");
        finish();
    }

    @Override
    public void onUploadFileSuccess(List<String> filePaths) {
        String filePath = filePaths.get(0);
        switch (mImageType) {
            case CARD_FRONT:
                cardFrontPath = filePath;
                break;
            case CARD_REVERSE:
                cardReversePath = filePath;
                break;
            case CARD_HARD:
                hardCardPath = filePath;
                break;
        }
    }
}
