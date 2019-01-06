package com.yiyue.user.module.mine.wallet.address;

import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityAddressBinding;
import com.yl.core.util.StatusBarUtil;

/**
 * 外部钱包地址
 * <p>
 * Create by zm on 2018/10/8
 */
@CreatePresenter(AddressPresenter.class)
public class AddressActivity extends BaseMvpAppCompatActivity<IAddressView, AddressPresenter>
        implements IAddressView, ClickHandler{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        ActivityAddressBinding addressBinding = (ActivityAddressBinding) viewDataBinding;
        addressBinding.setClick(this);
        // title
        addressBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: // 提交
//                ToastUtils.shortToast("submit");
                ToastUtils.shortToast("暂未开放,敬请期待!");
                break;
        }
    }
}
