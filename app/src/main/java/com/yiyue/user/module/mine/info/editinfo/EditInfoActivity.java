package com.yiyue.user.module.mine.info.editinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityEditInfoBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.model.vo.bean.UserCenterInfoBean;
import com.yiyue.user.module.mine.info.jobchoose.JobChooseActivity;
import com.yiyue.user.module.mine.info.updatename.UpdateNameActivity;
import com.yiyue.user.util.BitmapUtils;
import com.yiyue.user.util.FilePathUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.SelectPhotoView;
import com.yiyue.user.widget.bottomview.base.BaseBottomView;
import com.yiyue.user.widget.dialog.BaseEasyDialog;
import com.yiyue.user.widget.dialog.EasyDialog;
import com.yiyue.user.widget.dialog.ViewConvertListener;
import com.yiyue.user.widget.dialog.ViewHolder;
import com.yiyue.user.widget.mytimepickview.CustomDatePicker;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

/**
 * 编辑个人信息
 */
@CreatePresenter(EditInfoPresenter.class)
public class EditInfoActivity extends BaseMvpAppCompatActivity<IEditInfoView, EditInfoPresenter>
        implements IEditInfoView, ClickHandler {

    ActivityEditInfoBinding mBinding;
    private BaseBottomView mSelectPhotoView;
    private String headUrl = "";

    private ImageLoaderConfig config;
    private UserCenterInfoBean mUser;
    private CustomDatePicker mDatePicker;
    private String birthDate;

    private int hairType;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mUser = (UserCenterInfoBean) bundle.getSerializable("mUser");
        }
        mBinding = (ActivityEditInfoBinding) viewDataBinding;
        mBinding.setClick(this);

        config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();

        initView();
    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(v -> finish());
        ImageLoader.loadImage(mBinding.civHeadPhoto, mUser.getHeadImg(), config, null);
        mBinding.tvNickname.setText(mUser.getNickname());
        mBinding.tvBirthday.setText(mUser.getBirthday());
        mBinding.tvJob.setText(mUser.getJob());
        hairType = mUser.getHairstyle();
        mBinding.tvHair.setText(getHair(hairType));
        mBinding.tvFace.setText(getFace(mUser.getFaceture()));
        mBinding.tvHobby.setText(mUser.getHobby());

        if (mUser.getGender() == 1) mBinding.rbMan.setChecked(true);
        else mBinding.rbWoman.setChecked(true);
        mBinding.rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_man:
                    getMvpPresenter().updateUserSex(1);
                    break;
                case R.id.rb_woman:
                    getMvpPresenter().updateUserSex(2);
                    break;
            }
        });
    }


    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head_photo: //修改头像
                if (mSelectPhotoView == null) {
                    mSelectPhotoView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mSelectPhotoView.showBottomView(true);
                break;
            case R.id.rl_nickname:
                Bundle nameBundle = new Bundle();
                nameBundle.putString("name", mBinding.tvNickname.getText().toString());
                UpdateNameActivity.startActivity(EditInfoActivity.this, UpdateNameActivity.class, nameBundle);
                break;
            case R.id.rl_birthday:
                initDatePicker();
                birthDate = mBinding.tvBirthday.getText().toString();
                if (TextUtils.isEmpty(birthDate)) {
                    birthDate = DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD);
                } else {
                    birthDate = birthDate.replaceAll("/", "-");
                }
                mDatePicker.show(birthDate);
                break;
            case R.id.rl_job:
                Bundle jobBundle = new Bundle();
                jobBundle.putInt("type", 0);
                JobChooseActivity.startActivity(EditInfoActivity.this, JobChooseActivity.class, jobBundle);
                break;
            case R.id.rl_hair:
                showHairDialog();
                break;
            case R.id.rl_face:
                Bundle faceBundle = new Bundle();
                faceBundle.putInt("type", 1);
                JobChooseActivity.startActivity(EditInfoActivity.this, JobChooseActivity.class, faceBundle);
                break;
            case R.id.rl_hobby:
                showHobbyDialog();
                break;
        }
    }

    //获取脸型
    private String getFace(int faceType) {
        String face;
        switch (faceType) {
            case 1:
                face = "方脸";
                break;
            case 2:
                face = "圆脸";
                break;
            case 3:
                face = "尖脸";
                break;
            case 4:
                face = "瓜子脸";
                break;
            case 5:
                face = "鹅蛋脸";
                break;
            case 6:
                face = "包子脸";
                break;
            default:
                face = "未设置";
                break;
        }
        return face;
    }

    //获取发长
    private String getHair(int HairType) {
        String hair;
        switch (HairType) {
            case 1:
                hair = "长发";
                break;
            case 2:
                hair = "短发";
                break;
            case 3:
                hair = "中发";
                break;
            default:
                hair = "未设置";
                break;
        }
        return hair;
    }


    //在其他页面修改资料成功后返回 更新本页面相应信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.UpdateUserSuc event) {
        switch (event.getTag()) {
            case 1:
                mBinding.tvNickname.setText(event.getName());
                AccountManager.getInstance().setNickname(event.getName());
                break;
            case 2:
                mBinding.tvJob.setText(event.getJob());
                break;
            case 3:
                mBinding.tvHair.setText(getHair(event.getHair()));
                break;
            case 4:
                mBinding.tvFace.setText(getFace(event.getFace()));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initDatePicker() {
        String mNowDate = DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD);
        mDatePicker = new CustomDatePicker(this, "1900-01-01", mNowDate, time -> {
            mBinding.tvBirthday.setText(time.replaceAll("-", "\\/"));
//            getMvpPresenter().updateBirthday(time.replaceAll("-",""));
            getMvpPresenter().updateBirthday(mBinding.tvBirthday.getText().toString());
        });
    }

    //爱好dialog
    private void showHobbyDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_info_edit)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.setText(R.id.dialog_title, "填写爱好");
                        holder.setText(R.id.tv_left, "爱好:");
                        holder.setText(R.id.tv_total, "50");
                        EditText et = (holder.getView(R.id.et_content));
                        et.setHint("最多可填50字");
                        TextWatcher mTextWatcher = new TextWatcher() {
                            private CharSequence temp;
                            private int editStart;
                            private int editEnd;

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                temp = s;
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editStart = et.getSelectionStart();
                                editEnd = et.getSelectionEnd();
                                holder.setText(R.id.tv_num, temp.length() + "");
                                if (temp.length() > 50) {
                                    showToast("最多可填50字");
                                    s.delete(editStart - 1, editEnd);
                                    int tempSelection = editStart;
                                    et.setText(s);
                                    et.setSelection(tempSelection);
                                }
                            }
                        };
                        et.addTextChangedListener(mTextWatcher);

                        holder.getView(R.id.tv_ok).setOnClickListener(v -> {
                            String hobby = et.getText().toString().trim();
                            if (TextUtils.isEmpty(hobby)) {
                                showToast("请输入爱好");
                                return;
                            }
                            getMvpPresenter().updateHobby(hobby);
                            mBinding.tvHobby.setText(hobby);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    //发长dialog
    private void showHairDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_hair_type)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        //1长发 2短发 3中发
                        holder.getView(R.id.tv_ok).setOnClickListener(v -> {
                            int id = ((RadioGroup) holder.getView(R.id.rg_type)).getCheckedRadioButtonId();
                            switch (id) {
                                case R.id.rb_hair_1://短
                                    hairType = 2;
                                    break;
                                case R.id.rb_hair_2://中
                                    hairType = 3;
                                    break;
                                case R.id.rb_hair_3://长
                                    hairType = 1;
                                    break;
                            }
                            getMvpPresenter().updateHaidStyle(hairType);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_cancel).setOnClickListener(v -> dialog.dismiss());
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    Uri uri = null;
                    if (mSelectPhotoView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mSelectPhotoView).getUri();
                    }
                    if (uri == null) {
                        return;
                    }
                    PhoneUtil.toCrop(EditInfoActivity.this, uri, FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    PhoneUtil.toCrop(EditInfoActivity.this, data.getData(), FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(FilePathUtil.getCacheCrop() + "image_photo.jpg")) return;
                    getMvpPresenter().uploadImage(FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;
            }
        }
    }

    @Override
    public void onUploadFileSuccess(String filePath) {
        ImageLoader.loadImage(mBinding.civHeadPhoto, filePath, config, null);
        headUrl = filePath;
        getMvpPresenter().updateHeadImg(headUrl);
    }

    @Override
    public void updateHaidStyleSuc() {
        showToast("修改发长成功");
        mBinding.tvHair.setText(getHair(hairType));
    }

    @Override
    public void updateHeadImgSuc() {
        showToast("修改头像成功");
    }
}
