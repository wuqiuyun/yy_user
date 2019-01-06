package com.yiyue.user.module.mine.coupon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.home.stylist.StylistActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.module.mine.stylist.selectstore.SelectStoreActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.FragmentMineCouponBinding;
import com.yiyue.user.model.constant.MineCouponStatus;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.module.mine.order.details.OrderDetailsActivity;
import com.yiyue.user.util.RefreshLayoutUtil;

import java.util.ArrayList;

import static com.yiyue.user.model.constant.Constants.EXTRA_MINE_COUPON_TYPE;
import static com.yiyue.user.model.constant.MineCouponStatus.COUPON_PACKAGE;

/**
 * Created by wqy on 2018/11/4.
 */
@CreatePresenter(CouponPresenter.class)
public class MineCouponFragment extends BaseMvpFragment<ICouponView, CouponPresenter>
        implements ICouponView ,OnRefreshListener {

    @MineCouponStatus.MineCouponType
    private int mineCouponType;
    private MineCouponAdapter mAdapter;
    private FragmentMineCouponBinding mBinding;
    private String mUserId;
    private SmartRefreshLayout mRefreshLayout;

    public static Fragment newInstance(@MineCouponStatus.MineCouponType int mineCouponType) {
        Fragment fragment = new MineCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MINE_COUPON_TYPE, mineCouponType);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_coupon;
    }

    @Override
    protected void initView() {
        hasExtras();
        initRecycleView();
    }

    private void hasExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mineCouponType = bundle.getInt(EXTRA_MINE_COUPON_TYPE);
        }
    }

    @SuppressLint("NewApi")
    private void initRecycleView() {
        mBinding = (FragmentMineCouponBinding) viewDataBinding;

        mRefreshLayout = mBinding.refreshLayout;
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mRefreshLayout.setOnRefreshListener(this);


        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MineCouponAdapter(getContext(), mineCouponType);
        mAdapter.setItemListener( new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                CouponBean couponBean = mAdapter.getDatas().get(position);
                if (couponBean.getDirection() == 1){//优惠卷所属平台 美发师列表
                    StylistActivity.startActivity(getContext(), StylistActivity.class);
                }else {//优惠卷所属美发师 美发师详情
                    Bundle bundle = new Bundle();
                    if (mineCouponType==COUPON_PACKAGE){
                        //套餐卷
                        CreateOrderBody createOrderBody = new CreateOrderBody();
                        createOrderBody.setOptionId(null);
                        createOrderBody.setStylistId(couponBean.getStylistId());
                        //此页面需提交数据
                        createOrderBody.setUserId(String.valueOf(couponBean.getUserId()));
                        createOrderBody.setServiceId(String.valueOf(couponBean.getServicePackage().getServiceId()));
                        createOrderBody.setOrderamount(couponBean.getServicePackage().getPrice());
                        createOrderBody.setServiceName(couponBean.getServiceName());
                        createOrderBody.setServicemodel("2");
                        createOrderBody.setPackageId(String.valueOf(couponBean.getId()));
                        createOrderBody.setTimes(couponBean.getStocktimes());
                        bundle.putParcelable(Constants.STYLIST_ORDER_BODY,createOrderBody);
                        SelectStoreActivity.startActivity(getContext(), SelectStoreActivity.class,bundle);
                    }else {
                        bundle.putString(Constants.STYLISTId, couponBean.getStylistId());
                        StylistDetailsActivity.startActivity(getContext(), StylistDetailsActivity.class, bundle);
                    }
                }
            }
        });

        mBinding.recycleView.setAdapter(mAdapter);


    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        if (mRefreshLayout!=null)mRefreshLayout.autoRefresh();
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onSuccess(ArrayList<CouponBean> couponBeans) {
        mAdapter.setDatas(couponBeans, true);
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (mineCouponType==MineCouponStatus.COUPON_PACKAGE){
            getMvpPresenter().getUserPackageByUserId(mUserId);
        }else {
            getMvpPresenter().getStoreCollection(String.valueOf(mineCouponType+1),mUserId);
        }
    }
}
