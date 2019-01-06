package com.yiyue.user.module.mine.stylist.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.SpaceItemDecoration;
import com.yiyue.user.databinding.ActivityCouponsBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 确认订单
 * Created by wqy on 2018/11/6.
 */
@CreatePresenter(SelectCouponsPresenter.class)
public class SelectCouponsActivity extends BaseMvpAppCompatActivity<SelectCouponsView, SelectCouponsPresenter> implements SelectCouponsView, ClickHandler {
    private CreateOrderBody createOrderBody;
    private ActivityCouponsBinding mCouponsBinding;
    private SelectCouponAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_coupons;
    }

    @Override
    protected void init() {
        initView();
        initData();

    }

    private void initData() {
        String userId = AccountManager.getInstance().getUserId();
        createOrderBody = getIntent().getParcelableExtra(Constants.STYLIST_ORDER_BODY);
        if (createOrderBody!=null){
            String time = getIntent().getStringExtra(OrderConfirmActivity.APPOINTMENTTIME);
            getMvpPresenter().getCouponByUserId(userId,createOrderBody.getStylistId(),time);
        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mCouponsBinding = (ActivityCouponsBinding) viewDataBinding;
        mCouponsBinding.titleView.setLeftClickListener(view -> finish());

        mCouponsBinding.rvCoupons.setLayoutManager(new LinearLayoutManager(this));
        mCouponsBinding.rvCoupons.addItemDecoration(new SpaceItemDecoration(20));

        mAdapter = new SelectCouponAdapter(this);
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view,int p) {
                createOrderBody.setCouponamount(String.valueOf(mAdapter.getDatas().get(p).getDeduction()));//折扣金额
                createOrderBody.setCoupondirection(String.valueOf(mAdapter.getDatas().get(p).getDirection()));//1平台2美发师
                createOrderBody.setAmount(String.valueOf(mAdapter.getDatas().get(p).getAmount()));//满足多少才能减/折扣
                createOrderBody.setCouponId(String.valueOf(mAdapter.getDatas().get(p).getId()));
                createOrderBody.setCoupontype(String.valueOf(mAdapter.getDatas().get(p).getType()));
                Intent intent = new Intent();
                intent.putExtra(Constants.STYLIST_ORDER_BODY,createOrderBody);
                setResult(OrderConfirmActivity.FROMACTIVITY,intent);
                finish();
            }
        });
        mCouponsBinding.rvCoupons.setAdapter(mAdapter);
        
    }

    @Override
    public void showToast(String message) {
        showToast(message);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSuccess(ArrayList<CouponBean> couponBeans) {
        mAdapter.setDatas(couponBeans,true);
    }
}
