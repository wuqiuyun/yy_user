package com.yiyue.user.module.mine.info;

import android.os.Bundle;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityUserInfoBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.model.vo.bean.UserCenterInfoBean;
import com.yiyue.user.module.mine.info.editinfo.EditInfoActivity;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

/**
 * 个人资料
 */
@CreatePresenter(UserInfoPresenter.class)
public class UserInfoActivity extends BaseMvpAppCompatActivity<IUserInfoView, UserInfoPresenter>
        implements IUserInfoView {

    ActivityUserInfoBinding mBinding;
    
    private UserCenterInfoBean mUser;
    private ImageLoaderConfig config;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {

        mBinding = (ActivityUserInfoBinding) viewDataBinding;
        initView();
        StatusBarUtil.setLightMode(this);
    }

    private void initView() {
        ActivityUserInfoBinding binding = (ActivityUserInfoBinding) viewDataBinding;
        // 返回
        binding.titleView.setLeftClickListener(v -> finish());
        binding.titleView.setRightImgClickListener(view -> {
            if (mUser == null) {
                getMvpPresenter().getUserInfo();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("mUser",mUser);
            EditInfoActivity.startActivity(UserInfoActivity.this, EditInfoActivity.class, bundle);
        });

        UserBean user = AccountManager.getInstance().getAccount();
        if (null != user){
            mBinding.tvNickname.setText(FormatUtil.Formatstring(user.getNickname()));
            mBinding.tvId.setText("ID:" + user.getIdNo());//使用长id展示
        }

        config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getUserInfo();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getUserInfoSuc(UserCenterInfoBean data) {
        mUser = data;
        ImageLoader.loadImage(mBinding.civHeadPhoto, data.getHeadImg(), config, null);
        AccountManager.getInstance().setHeadImg(data.getHeadImg());
        mBinding.tvNickname.setText(data.getNickname());
        String gender = data.getGender() == 1 ? "男" : "女";
        mBinding.tvGender.setText(gender);
        mBinding.tvBirthday.setText(data.getBirthday());
        mBinding.tvJob.setText(data.getJob());
        mBinding.tvHobby.setText(data.getHobby());
        mBinding.tvFace.setText(getFace(data.getFaceture()));
        mBinding.tvHair.setText(getHair(data.getHairstyle()));
    }

    @Override
    public void getUserInfoFail() {
        showToast("获取用户详细信息失败");
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
}
