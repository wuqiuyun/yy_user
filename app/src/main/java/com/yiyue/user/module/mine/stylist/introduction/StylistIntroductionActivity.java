package com.yiyue.user.module.mine.stylist.introduction;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.databinding.ActivityStylistIntroductionBinding;
import com.yiyue.user.module.home.store.StoreManagerActivity;
import com.yl.core.util.StatusBarUtil;

import io.reactivex.annotations.NonNull;

import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 美发师简介
 * Create by wqy on 2018/11/11
 */
public class StylistIntroductionActivity extends BaseMvpAppCompatActivity<IIntroductionView, IntroductionPresenter> implements IIntroductionView {
    private static final String DESCRIPTION = "description";
    private String description = "";

    public static void startActivity(Context context, @NonNull String description) {
        Bundle bundle = new Bundle();
        bundle.putString(DESCRIPTION, description);
        startActivity(context, StylistIntroductionActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stylist_introduction;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        StatusBarUtil.setLightMode(this);
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.description = bundle.getString(DESCRIPTION);
        }
    }

    private void initView() {
        ActivityStylistIntroductionBinding introductionBinding = (ActivityStylistIntroductionBinding) viewDataBinding;
        introductionBinding.titleView.setLeftClickListener(view -> finish());
        introductionBinding.tvIntroduction.setText(description);
    }

    @Override
    public void showToast(String message) {

    }
}
