package com.yiyue.user.module.home.stylist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStylistSearchBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.module.mine.stylist.IUpDataFragment;
import com.yl.core.util.StatusBarUtil;

import java.util.HashMap;


/**
 * Created by lvlong on 2018/10/27.
 */
public class SearchStylistActivity extends BaseAppCompatActivity implements ClickHandler{
    private static final String BUNDLE_FRAGMENT = "stylistFragment";
    private Fragment mFragment;
    private IUpDataFragment mIUpDataFragment;
    private ActivityStylistSearchBinding mBinding;
    private HashMap<String, String> mSearchs;
    private String mStoreId;

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        mIUpDataFragment = IUpDataFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stylist_search;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityStylistSearchBinding) this.viewDataBinding;
        mBinding.setClick(this);
        mSearchs = new HashMap<>();
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String searchType = extras.getString(Constants.SEARCH_TYPE);
            mStoreId = extras.getString(Constants.STORE_ID);
            mSearchs.put(Constants.SEARCH_TYPE,searchType);
            if (searchType.equals(Constants.SEARCH_FROM_HOME)){
                mBinding.etSearch.setHint(getString(R.string.search_service_hint));
            }else {
                mBinding.etSearch.setHint(getString(R.string.search_stylist_hint));
            }
        }
        initFragment();
        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    goSearchs();
                    return true;
                }
                return false;
            }
        });
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (mFragment == null) {
            mFragment = StylistFragment.newInstance(Constants.ACTIVITY_SEARCH,mStoreId);
            mFragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_stylist_search, mFragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_search:
                goSearchs();
            break;
        }
    }

    private void goSearchs() {
        String content = mBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            ToastUtils.shortToast("搜索内容不能为空");
            return;
        }
        if (mIUpDataFragment != null) {
            mSearchs.put("content",content);
            mIUpDataFragment.onUpData(Constants.ACTIVITY_SEARCH,mSearchs );
        }
    }

}
