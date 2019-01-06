package com.yiyue.user.module.home.qrcode;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.Result;
import com.yiyue.user.api.UserPayApi;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.bean.PersonPayBean;
import com.yiyue.user.model.vo.result.CreateOrderResult;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yiyue.user.module.mine.wallet.buy.PayActivity;
import com.yiyue.user.util.GsonUtils;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yiyue.user.R;
import com.yiyue.user.api.OrderApi;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityScancodeBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.module.home.qrcode.camera.CameraManager;
import com.yiyue.user.module.home.qrcode.config.BeepManager;
import com.yiyue.user.module.home.qrcode.config.FinishListener;
import com.yiyue.user.module.home.qrcode.config.InactivityTimer;
import com.yiyue.user.module.home.qrcode.config.ScancodeActivityHandler;
import com.yiyue.user.module.home.qrcode.config.ZxingConfig;
import com.yiyue.user.module.home.qrcode.config.ZxingConstant;
import com.yiyue.user.module.home.qrcode.decode.DecodeImgCallback;
import com.yiyue.user.module.home.qrcode.decode.DecodeImgThread;
import com.yiyue.user.module.home.qrcode.decode.ZxingImagePath;
import com.yiyue.user.module.home.qrcode.view.ViewfinderView;

import java.io.IOException;


/**
 * Created by zhangzz on 2018/11/1.
 *
 * @declare :扫一扫
 */

@CreatePresenter(ScanCodePresenter.class)
public class ScanCodeActivity extends BaseMvpAppCompatActivity<ScanCodeView, ScanCodePresenter> implements ScanCodeView, SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = ScanCodeActivity.class.getSimpleName();
    public ZxingConfig config;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private CameraManager cameraManager;
    private ScancodeActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private String storeId,stylistId,tradePayAmount;

    private ActivityScancodeBinding mBinding;


    public ViewfinderView getViewfinderView() {
        return mBinding.viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        mBinding.viewfinderView.drawViewfinder();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scancode;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        if (config == null) {
            config = new ZxingConfig();
        }

        mBinding = (ActivityScancodeBinding) viewDataBinding;
        initView();

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.setPlayBeep(config.isPlayBeep());
        beepManager.setVibrate(config.isShake());
    }


    private void initView() {
        mBinding.previewView.setOnClickListener(this);
        mBinding.viewfinderView.setZxingConfig(config);
        mBinding.backIv.setOnClickListener(this);
        mBinding.flashLightLayout.setOnClickListener(this);
        mBinding.albumLayout.setOnClickListener(this);

        switchVisibility(mBinding.bottomLayout, config.isShowbottomLayout());
        switchVisibility(mBinding.flashLightLayout, config.isShowFlashLight());
        switchVisibility(mBinding.albumLayout, config.isShowAlbum());

        /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(getPackageManager())) {
            mBinding.flashLightLayout.setVisibility(View.VISIBLE);
        } else {
            mBinding.flashLightLayout.setVisibility(View.GONE);
        }

    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == ZxingConstant.FLASH_OPEN) {
            mBinding.flashLightIv.setImageResource(R.drawable.s_light);
            mBinding.flashLightTv.setText("关闭闪光灯");
        } else {
            mBinding.flashLightIv.setImageResource(R.drawable.s_light);
            mBinding.flashLightTv.setText("打开闪光灯");
        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        String resultStr = rawResult.getText().toString();
        if (resultStr!=null){
            try {
                PersonPayBean mPersonBean = GsonUtils.fromJson(resultStr,PersonPayBean.class);
                storeId = mPersonBean.getStoreId();
                stylistId = mPersonBean.getStylistId();
                tradePayAmount = mPersonBean.getAmount();
                new UserPayApi().createTradePayOrder(storeId,stylistId,tradePayAmount, new YLRxSubscriberHelper<CreateOrderResult>(this,true) {
                    @Override
                    public void _onNext(CreateOrderResult baseResponse) {
                        CreateOrderBean createOrder = baseResponse.getData();
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.ISORDER, 4);
                        bundle.putParcelable(Constants.STYLIST_ORDER_RESULT, createOrder);
                        PayActivity.startActivity(ScanCodeActivity.this, PayActivity.class, bundle);
                        finish();
                    }
                });

            }catch (JsonSyntaxException e){
                ToastUtils.shortToast("无法识别此二维码");
            }

        }


    }


    private void switchVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        mBinding.viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = mBinding.previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new ScancodeActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.flashLightLayout) {
            /*切换闪光灯*/
            cameraManager.switchFlashLight(handler);
        } else if (id == R.id.albumLayout) {
            /*打开相册*/
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, ZxingConstant.REQUEST_IMAGE);
        } else if (id == R.id.backIv) {
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ZxingConstant.REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            String path = ZxingImagePath.getImageAbsolutePath(this, data.getData());

            new DecodeImgThread(path, new DecodeImgCallback() {
                @Override
                public void onImageDecodeSuccess(Result result) {
                    handleDecode(result);
                }

                @Override
                public void onImageDecodeFailed() {
                    Toast.makeText(ScanCodeActivity.this, "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                }
            }).run();

        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }
}
