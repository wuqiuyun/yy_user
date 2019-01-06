package com.yiyue.user.module.home.search;

import android.view.View;
import android.widget.Toast;

import com.yl.core.component.toast.ToastUtil;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivitySearchBinding;
import com.yl.core.util.StatusBarUtil;

/**
 * 搜索服务
 * Created by zm on 2018/11/2.
 */
public class SearchActivity extends BaseMvpAppCompatActivity<ISearchView, SearchPresenter> implements ISearchView, ClickHandler {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        ActivitySearchBinding mBinding = (ActivitySearchBinding) viewDataBinding;
        mBinding.setClick(this);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_search:
                ToastUtil.showToast(this, "搜索", Toast.LENGTH_SHORT);
                break;
        }
    }
}
