package com.yiyue.user.module.mine.stylist.selectstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStoreSelectionBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yiyue.user.module.mine.stylist.time.TimeSelectionActivity;
import com.yiyue.user.util.CacheActivity;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 选择门店
 * Created by wqy on 2018/11/6.
 */
@CreatePresenter(SelectStorePresenter.class)
public class SelectStoreActivity extends BaseMvpAppCompatActivity<ISelectStoreView, SelectStorePresenter> implements ISelectStoreView, ClickHandler {
    private StoreSelectionAdapter storeSelectionAdapter;
    private ArrayList<StoreBean> storeBeans = new ArrayList<>();
    private Bundle mBundle;
    private CreateOrderBody mCreateOrderBody;
    private UserBean userBean;

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_selection;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mCreateOrderBody = mBundle.getParcelable(Constants.STYLIST_ORDER_BODY);
            userBean = (UserBean) mBundle.getSerializable(Constants.USER_INFO_BODY);
            DLog.d("选择门店" + mCreateOrderBody.toString());
            getMvpPresenter().getStylistStore(mCreateOrderBody.getStylistId(), mCreateOrderBody.getUserId());
        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        if (!CacheActivity.activityList.contains(SelectStoreActivity.this)) {
            CacheActivity.addActivity(SelectStoreActivity.this);
        }
        ActivityStoreSelectionBinding storeSelectionBinding = (ActivityStoreSelectionBinding) viewDataBinding;
        storeSelectionBinding.setClick(this);
        storeSelectionBinding.titleView.setLeftClickListener(view -> finish());
        storeSelectionBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        storeSelectionBinding.recycleView.addItemDecoration(new SpaceItemHorizontalDecoration(10));

        storeSelectionAdapter = new StoreSelectionAdapter(this);
        storeSelectionBinding.recycleView.setAdapter(storeSelectionAdapter);

        storeSelectionAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < storeSelectionAdapter.getDatas().size(); i++) {
                    StoreBean storeBean = storeSelectionAdapter.getDatas().get(i);
                    if (i == position) {
                        storeBean.setStatus(true);
                        mCreateOrderBody.setStoreId(String.valueOf(storeBean.getStoreId()));
                        mCreateOrderBody.setStoreName(storeBean.getStorename());
                    } else {
                        storeBean.setStatus(false);
                    }
                }
                storeSelectionAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (mCreateOrderBody.getStoreId() == null) {
                    showToast("请选择门店");
                    return;
                }
                if (mCreateOrderBody.getIsResult() != null) {
                    //提交订单页返回
                    Intent intent = new Intent();
                    intent.putExtra(Constants.STYLIST_ORDER_BODY, mCreateOrderBody);
                    intent.putExtra(Constants.USER_INFO_BODY,userBean);
                    setResult(OrderConfirmActivity.FROMACTIVITY, intent);
                    finish();
                } else {
                    mBundle.putParcelable(Constants.STYLIST_ORDER_BODY, mCreateOrderBody);
                    mBundle.putSerializable(Constants.USER_INFO_BODY, userBean);
                    TimeSelectionActivity.startActivity(this, TimeSelectionActivity.class, mBundle);
                }
                break;
        }
    }

    @Override
    public void onSuccess(ArrayList<StoreBean> storeBeans) {
        for (StoreBean storeBean : storeBeans) {
            if (mCreateOrderBody.getStoreId() != null && mCreateOrderBody.getStoreId().equals(storeBean.getStoreId())) {
                storeBean.setStatus(true);
                return;
            }
        }
        storeSelectionAdapter.setDatas(storeBeans, true);
    }

}
