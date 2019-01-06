package com.yiyue.user.module.mine.store.join;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStoreStylistBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.module.home.stylist.SearchStylistActivity;
import com.yiyue.user.module.home.stylist.StylistFragment;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;


/**
 * 门店美发师
 * <p>
 * Create by zm on 2018/10/10
 */
@CreatePresenter(StoreStylistPresenter.class)
public class StoreStylistActivity extends BaseMvpAppCompatActivity<StoreStylistView, StoreStylistPresenter> implements StoreStylistView {
    ActivityStoreStylistBinding binding;
    private String mStoreId;
    private Fragment mFragment;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_stylist;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        binding = (ActivityStoreStylistBinding) viewDataBinding;
        binding.titleView.setLeftClickListener(v -> finish());
        binding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEARCH_TYPE,Constants.SEARCH_FROM_STORE);
                bundle.putString(Constants.STORE_ID,mStoreId);
                startActivity(StoreStylistActivity.this,SearchStylistActivity.class,bundle);
            }
        });
    }


    private void initData() {
        if (getIntent().getExtras()!=null)mStoreId = getIntent().getExtras().getString(Constants.STORE_ID);
        initFragment();
    }


    private void initFragment() {

        if (mFragment == null) {
            mFragment = StylistFragment.newInstance(Constants.ACTIVITY_STORE_STYLIST,mStoreId);
            mFragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_store_stylist, mFragment)
                .commit();
    }
    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }
}
