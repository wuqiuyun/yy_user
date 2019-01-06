package com.yiyue.user.module.mine.info.updatename;

import android.os.Bundle;
import android.text.TextUtils;

import com.yiyue.user.model.vo.bean.EventBean;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityUpdateNameBinding;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 修改昵称
 */
@CreatePresenter(UpdateNamePresenter.class)
public class UpdateNameActivity extends BaseMvpAppCompatActivity<IUpdateNameView, UpdateNamePresenter> implements IUpdateNameView {

    ActivityUpdateNameBinding mBinding;
    private String name;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_name;
    }

    @Override
    protected void init() {
        mBinding = (ActivityUpdateNameBinding) viewDataBinding;
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            name = bundle.getString("name");
        }
        initView();
        StatusBarUtil.setLightMode(this);
    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(view -> finish());
        mBinding.titleView.setRightTextClickListener(view -> updateStylistName(mBinding.etName.getText().toString()));
        mBinding.etName.setText(TextUtils.isEmpty(name) ? "" : name);
    }

    private void updateStylistName(String newName) {
        if (TextUtils.isEmpty(newName)) {
            showToast("新昵称不能为空！");
            return;
        }
        
        if (newName.equals(name)) {
            finish();
        }

        getMvpPresenter().updateStylistName(newName);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void updateStylistNameSuc() {
        showToast("修改成功！");
        EventBean.UpdateUserSuc update = new EventBean.UpdateUserSuc();
        update.setTag(1);
        update.setName(mBinding.etName.getText().toString());
        EventBus.getDefault().post(update);
        finish();
    }
}
