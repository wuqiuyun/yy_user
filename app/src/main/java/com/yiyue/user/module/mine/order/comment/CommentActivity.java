package com.yiyue.user.module.mine.order.comment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityCommentBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.model.vo.bean.OrderCommentBean;
import com.yiyue.user.model.vo.requestbody.OrderCommentRequestBody;
import com.yiyue.user.util.FilePathUtil;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.util.compressutil.CompressPicUtil;
import com.yiyue.user.util.compressutil.OnCompressListener;
import com.yiyue.user.widget.PhotoView.PhotoViewActivity;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.SelectPhotoView;
import com.yiyue.user.widget.bottomview.base.BaseBottomView;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 评价、查看评价页面
 * <p>
 * Created by zm on 2018/11/9.
 */
@CreatePresenter(CommentPresenter.class)
public class CommentActivity extends BaseMvpAppCompatActivity<ICommentView, CommentPresenter>
        implements ICommentView, ClickHandler {
    private static final String EXTRAS_ORDER_BEAN = "order_ban";
    private static final String EXTRAS_COMMENT_TYPE = "order_type";

    public static final int COMMENT_LOOK = 0; // 查看评价
    public static final int COMMENT_EDIT = 1; // 编辑评价

    private ImageLoaderConfig mImageLoaderConfig;

    @IntDef({COMMENT_LOOK, COMMENT_EDIT})
    @interface CommentType {
    }

    private ActivityCommentBinding mBinding;

    @CommentType
    private int commentType;
    private OrderBean mOrderBean;
    private int imageType = -1; // 0美发师 1门店

    private CommentPicAdapter stylistAdapter;
    private CommentPicAdapter storeAdapter;

    private List<String> storeImgPaths = new ArrayList<>();
    private List<String> stylistImgPaths = new ArrayList<>();

    private ArrayList<AlbumFile> storeImgs = new ArrayList<>();
    private ArrayList<AlbumFile> stylistImgs = new ArrayList<>();

    /**
     * @param context
     * @param type      {@link #COMMENT_LOOK #COMMENT_EDIT}
     * @param orderBean
     */
    public static void startActivity(Context context, @CommentType int type, OrderBean orderBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRAS_ORDER_BEAN, orderBean);
        bundle.putInt(EXTRAS_COMMENT_TYPE, type);
        startActivity(context, CommentActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        hasExtras();
        initView();
        initEvent();
        loadData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mBinding = (ActivityCommentBinding) viewDataBinding;
        mBinding.setClick(this);
        if (mImageLoaderConfig == null) {
            mImageLoaderConfig = new ImageLoaderConfig.Builder()
                    .setAsBitmap(true)
                    .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                    .setErrorResId(R.drawable.icon_head_pic_def)
                    .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                    .setPrioriy(ImageLoaderConfig.LoadPriority.NORMAL)
                    .setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE)
                    .build();
        }
        // back
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setTitleText(getString(commentType == COMMENT_LOOK ?
                R.string.order_comment_look : R.string.order_comment));
        // stylist picture adapter
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.commentStore.recycleView.setLayoutManager(layoutManager1);
        storeAdapter = new CommentPicAdapter(this);
        storeAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, storeAdapter.getDatas());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(CommentActivity.this, PhotoViewActivity.class, bundle);
            }
        });
        mBinding.commentStore.recycleView.setAdapter(storeAdapter);
        // store picture adapter
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.commentStylist.recycleView.setLayoutManager(layoutManager2);
        stylistAdapter = new CommentPicAdapter(this);
        stylistAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, stylistAdapter.getDatas());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(CommentActivity.this, PhotoViewActivity.class, bundle);
            }
        });
        mBinding.commentStylist.recycleView.setAdapter(stylistAdapter);

        mBinding.commentStore.tvName.setText(FormatUtil.Formatstring(mOrderBean.getStoreName()));
        mBinding.commentStore.tvCommentName.setText("门店评价");
        mBinding.commentStore.tvComment1.setText("服务环境");
        mBinding.commentStore.tvComment2.setText("服务态度");
        ImageLoader.loadImage(mBinding.commentStore.ivPhoto, mOrderBean.getStorePath(), mImageLoaderConfig, null);

        mBinding.commentStylist.tvName.setText(FormatUtil.Formatstring(mOrderBean.getStylistNickname()));
        mBinding.commentStylist.tvCommentName.setText("美发师评价");
        mBinding.commentStylist.tvComment1.setText("专业技能");
        mBinding.commentStylist.tvComment2.setText("服务态度");
        ImageLoader.loadImage(mBinding.commentStylist.ivPhoto, mOrderBean.getStylistPath(), mImageLoaderConfig, null);

        // 不可编辑
        if (commentType == COMMENT_LOOK) {
            mBinding.commentStore.ratingBar.setOnTouchListener((v, event) -> true);
            mBinding.commentStore.ratingBar1.setOnTouchListener((v, event) -> true);
            mBinding.commentStore.ratingBar2.setOnTouchListener((v, event) -> true);
            mBinding.commentStylist.ratingBar.setOnTouchListener((v, event) -> true);
            mBinding.commentStylist.ratingBar1.setOnTouchListener((v, event) -> true);
            mBinding.commentStylist.ratingBar2.setOnTouchListener((v, event) -> true);
            mBinding.commentStylist.etCommentContent.setEnabled(false);
            mBinding.commentStore.etCommentContent.setEnabled(false);
            mBinding.btnSubmit.setVisibility(View.GONE);
        } else {
            mBinding.commentStore.ratingBar.setStepSize(1);
            mBinding.commentStore.ratingBar1.setStepSize(1);
            mBinding.commentStore.ratingBar2.setStepSize(1);
            mBinding.commentStylist.ratingBar.setStepSize(1);
            mBinding.commentStylist.ratingBar1.setStepSize(1);
            mBinding.commentStylist.ratingBar2.setStepSize(1);
        }
    }

    private int number = 9;

    private void initEvent() {
        mBinding.commentStylist.ivAdd.setOnClickListener(v -> {
            imageType = 0;
            openAlbum();
        });
        mBinding.commentStore.ivAdd.setOnClickListener(v -> {
            imageType = 1;
            openAlbum();
        });
    }

    /**
     * 权限
     */
    private void openAlbum() {
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(grant -> {
                    if (grant) {
                        if (imageType == 0) {
                            //美发师
                            selectStylistImg();
                        } else {
                            //门店
                            selectStoreImg();
                        }
                    }
                });
    }

    private void selectStoreImg() {
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .checkedList(storeImgs)
                .selectCount(number - storeAdapter.getDatas().size())
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        storeImgs = result;
                        storeImgPaths.clear();
                        ArrayList<String> pathLists = new ArrayList<>();
                        for (AlbumFile albumFile : result) {
                            // 拿到用户选择的图片路径List：
                            pathLists.add(albumFile.getPath());
                        }
                        compressPicAndUpload(pathLists);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    private void selectStylistImg() {
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .checkedList(stylistImgs)
                .columnCount(3)
                .selectCount(number - stylistAdapter.getDatas().size())
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        stylistImgs = result;
                        stylistImgPaths.clear();
                        ArrayList<String> pathLists = new ArrayList<>();
                        for (AlbumFile albumFile : result) {
                            // 拿到用户选择的图片路径List：
                            pathLists.add(albumFile.getPath());
                        }
                        compressPicAndUpload(pathLists);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    private void loadData() {
        if (commentType == COMMENT_LOOK) {
            getMvpPresenter().getOrderComment(mOrderBean.getId());
        }
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            commentType = bundle.getInt(EXTRAS_COMMENT_TYPE);
            mOrderBean = bundle.getParcelable(EXTRAS_ORDER_BEAN);
        }
        if (mOrderBean == null) {
            finish();
            return;
        }
    }

    /**
     * 压缩后上传
     *
     * @param pathList
     */
    private void
    compressPicAndUpload(List<String> pathList) {
        for (String filePath : pathList) {
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
                            if (imageType == 0) { // 美发师
                                stylistImgPaths.add(file.getPath());
                                //压缩完添加入上传数组,size等于用户选择的就开始上传
                                if (stylistImgPaths.size() == pathList.size()) {
                                    getMvpPresenter().uploadImage(stylistImgPaths, CommentActivity.this);
                                }
                            } else if (imageType == 1) { // 门店
                                storeImgPaths.add(file.getPath());
                                //压缩完添加入上传数组,size等于用户选择的就开始上传
                                if (storeImgPaths.size() == pathList.size()) {
                                    getMvpPresenter().uploadImage(storeImgPaths, CommentActivity.this);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (imageType == 0) { // 美发师
                                // 压缩成功后调用，返回压缩后的图片文件
                                stylistImgPaths.add(filePath);
                                //压缩完添加入上传数组,size等于选择的就开始上传
                                if (stylistImgPaths.size() == pathList.size()) {
                                    getMvpPresenter().uploadImage(stylistImgPaths, CommentActivity.this);
                                }
                            } else if (imageType == 1) { // 门店
                                // 压缩成功后调用，返回压缩后的图片文件
                                storeImgPaths.add(filePath);
                                //压缩完添加入上传数组,size等于选择的就开始上传
                                if (storeImgPaths.size() == pathList.size()) {
                                    getMvpPresenter().uploadImage(storeImgPaths, CommentActivity.this);
                                }
                            }
                        }
                    }).launch();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                DLog.d(mBinding.commentStylist.etCommentContent.getText().toString().trim().length() + "**************");
                DLog.d(mBinding.commentStore.etCommentContent.getText().toString().trim().length() + "**************");
                OrderCommentRequestBody requestBody
                        = new OrderCommentRequestBody.Builder()
                        .orderId(mOrderBean.getId())
                        .userId(AccountManager.getInstance().getUserId())
                        .stylistComp((int) mBinding.commentStylist.ratingBar.getRating())
                        .skill((int) mBinding.commentStylist.ratingBar1.getRating())
                        .stylistServer((int) mBinding.commentStylist.ratingBar2.getRating())
                        .stylistComment(mBinding.commentStylist.etCommentContent.getText().toString())
                        .storeCom((int) mBinding.commentStore.ratingBar.getRating())
                        .storeEnv((int) mBinding.commentStore.ratingBar1.getRating())
                        .storeServer((int) mBinding.commentStore.ratingBar2.getRating())
                        .storeComment(mBinding.commentStore.etCommentContent.getText().toString().trim())
                        .storeImgPaths(storeAdapter.getDatas())
                        .stylistImgPaths(stylistAdapter.getDatas())
                        .build();
                getMvpPresenter().submitComment(this, requestBody);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onGetOrderComment(OrderCommentBean data) {
        // 门店
        mBinding.commentStore.tvName.setText(FormatUtil.Formatstring(data.getStoreName()));
        if (!TextUtils.isEmpty(data.getStoreHeadImg())) {
            ImageLoader.loadImage(mBinding.commentStore.ivPhoto, data.getStoreHeadImg());
        }

        mBinding.commentStore.ratingBar.setRating(data.getStoreCom());
        mBinding.commentStore.ratingBar1.setRating(data.getSotreEnv());
        mBinding.commentStore.ratingBar2.setRating(data.getStoreServer());
        mBinding.commentStore.ivAdd.setVisibility(View.GONE);
        mBinding.commentStore.etCommentContent.setText(FormatUtil.Formatstring(data.getStoreComment()));
        storeAdapter.setDatas((ArrayList<String>) data.getStoreImgPaths(), true);
        // 美发师
        mBinding.commentStylist.tvName.setText(FormatUtil.Formatstring(data.getStylistName()));
        if (!TextUtils.isEmpty(data.getStoreHeadImg())) {
            ImageLoader.loadImage(mBinding.commentStylist.ivPhoto, data.getStylistHeadImg());
        }
        mBinding.commentStylist.ratingBar.setRating(data.getStylistComp());
        mBinding.commentStylist.ratingBar1.setRating(data.getSkill());
        mBinding.commentStylist.ratingBar2.setRating(data.getStylistServer());
        mBinding.commentStylist.ivAdd.setVisibility(View.GONE);
        mBinding.commentStylist.etCommentContent.setText(data.getStylistComment());
        stylistAdapter.setDatas((ArrayList<String>) data.getStylistImgPaths(), true);
    }

    @Override
    public void onSubmitCommentSuccess() {
        finish();
    }

    @Override
    public void onUploadFileSuccess(List<String> filePath) {
        switch (imageType) {
            case 0: // 美发师
                stylistAdapter.getDatas().addAll(filePath);
                stylistAdapter.notifyDataSetChanged();
                if (stylistAdapter.getDatas().size() == number) {
                    mBinding.commentStylist.ivAdd.setVisibility(View.GONE);
                }
                break;
            case 1: // 门店
                storeAdapter.getDatas().addAll(filePath);
                storeAdapter.notifyDataSetChanged();
                if (storeAdapter.getDatas().size() == number) {
                    mBinding.commentStore.ivAdd.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onUploadFileFail() {
    }
}
