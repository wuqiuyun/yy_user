package com.yiyue.user.module.login.information;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityPerfectInformationBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.module.main.MainActivity;
import com.yiyue.user.util.FilePathUtil;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.util.compressutil.CompressPicUtil;
import com.yiyue.user.util.compressutil.OnCompressListener;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.SelectPhotoView;
import com.yiyue.user.widget.bottomview.base.BaseBottomView;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.io.File;

import retrofit2.http.HEAD;

/**
 * 完善资料
 * <p>
 * Created by lvlong on 2018/9/27.
 */
@CreatePresenter(PerfectInformationPresenter.class)
public class PerfectInformationActivity extends BaseMvpAppCompatActivity<IPerfectInformationView, PerfectInformationPresenter>
        implements ClickHandler , IPerfectInformationView {

    ActivityPerfectInformationBinding mBinding;

    private BaseBottomView mBaseBottomView;
    private String headUrl = "";
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_perfect_information;
    }

    @Override
    protected void init() {

        mBinding = (ActivityPerfectInformationBinding) viewDataBinding;
        mBinding.setClick(this);

        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机

                    Uri uri = null;
                    if (mBaseBottomView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mBaseBottomView).getUri();
                    }
                    if (uri == null) {
                        return;
                    }
                    PhoneUtil.toCrop(PerfectInformationActivity.this, uri, FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;
                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    PhoneUtil.toCrop(PerfectInformationActivity.this, data.getData(), FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(FilePathUtil.getCacheCrop() + "image_photo.jpg")) return;
                    getMvpPresenter().uploadImage(FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head_photo: //上传头像
                if (mBaseBottomView == null) {
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.btn_ok: //保存
                getMvpPresenter().doUserData(
                        mBinding.etNickName.getText().toString().trim(),
                        getSex(),
                        headUrl,
                        mBinding.etLoginPassword.getText().toString().trim(),
                        mBinding.etConfirmPassword.getText().toString().trim());
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onDoUserDataSuccess() {
        AccountManager.getInstance().setUserStatus(0);
        AccountManager.getInstance().setHeadImg(headUrl);
        AccountManager.getInstance().setNickname(mBinding.etNickName.getText().toString().trim());
        MainActivity.startActivity(this, MainActivity.HOME);
        finish();
    }

    @Override
    public void onUploadFileSuccess(String filePath) {
        ImageLoaderConfig config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        ImageLoader.loadImage(mBinding.ivHeadPhoto, filePath, config, null);
        headUrl = filePath;
    }

    /**
     * 获取性别
     * @return 1男 2女 3人妖
     */
    private int getSex() {
        if (mBinding.rbMan.isChecked()) {
            return 1;
        }
        if (mBinding.rbWoman.isChecked()) {
            return 2;
        }
        return 3;
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
                        getMvpPresenter().uploadImage(filePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用 上传原图

                        getMvpPresenter().uploadImage(filePath);
                    }
                }).launch();
    }
}
