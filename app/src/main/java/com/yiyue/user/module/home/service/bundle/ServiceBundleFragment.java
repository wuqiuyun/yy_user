package com.yiyue.user.module.home.service.bundle;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.YLApplication;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.databinding.FragmentServiceBundleBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.MineCouponStatus;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.module.home.service.bundle.detail.BundleDetailActivity;
import com.yiyue.user.module.home.service.coupon.ServiceCouponAdapter;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;

/**
 * 服务-> 套餐
 * Created by wqy on 2018/11/1.
 */
@CreatePresenter(ServiceBundlePresenter.class)
public class ServiceBundleFragment extends BaseMvpFragment<IServiceBundleView, ServiceBundlePresenter>
        implements IServiceBundleView, OnRefreshListener, OnLoadMoreListener {

    private static final String TYPE_SINGLE = "1"; //单项套餐
    private static final String TYPE_MULTIPLE = "2"; //多项套餐
    FragmentServiceBundleBinding mBinding;
    private ServiceCouponAdapter mAdapter;
    private int page = 1;
    private String bundleType = TYPE_SINGLE;
    private SmartRefreshLayout mRefreshLayout;
    private InputMethodManager mImm;
    private int size = 10;//每页数量

    private final String[] labels = new String[]{
            YLApplication.getContext().getString(R.string.coupon_single),
            YLApplication.getContext().getString(R.string.coupon_multiple),
    };

    public static Fragment newInstance() {
        return new ServiceBundleFragment();
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_bundle;
    }

    @Override
    protected void initView() {
        mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mBinding = (FragmentServiceBundleBinding) viewDataBinding;
        initRefreshLoadLayout();
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ServiceCouponAdapter(getActivity(), MineCouponStatus.COUPON_PACKAGE);
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                CouponBean couponBean = mAdapter.getDatas().get(position);
                // 套餐详情
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SERVICEID, String.valueOf(couponBean.getServiceId()));
                bundle.putString("type", bundleType);
                BundleDetailActivity.startActivity(getContext(), BundleDetailActivity.class, bundle);
            }
        });
        mBinding.recycleView.setAdapter(mAdapter);

        setTable(mBinding.tableLayout, getLayoutInflater());
        mBinding.tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // 单项套餐
                        bundleType = TYPE_SINGLE;
                        mRefreshLayout.autoRefresh();
                        break;
                    case 1: // 多项套餐
                        bundleType = TYPE_MULTIPLE;
                        mRefreshLayout.autoRefresh();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void loadData() {
        mRefreshLayout.autoRefresh();
    }

    /**
     * 设置Tab
     *
     * @param tabLayout
     * @param inflater
     */
    private void setTable(TabLayout tabLayout, LayoutInflater inflater) {
        for (String label : labels) {
            TabLayout.Tab newTab = tabLayout.newTab();
            View tabView = inflater.inflate(R.layout.tab_layout_home, null);
            newTab.setCustomView(tabView);

            TextView tvLabel = tabView.findViewById(R.id.tv_label);
            tvLabel.setText(label);
            tabLayout.addTab(newTab);
        }
    }

    @Override
    public void getBundleSuccess(ArrayList<CouponBean> packageBeans) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            mAdapter.setDatas(packageBeans, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            mAdapter.addDatas(packageBeans, true);
        }

        if (packageBeans.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getBundleFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    protected void initRefreshLoadLayout() {
        mRefreshLayout = mBinding.refreshLayout;
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            mRefreshLayout.setOnLoadMoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        getMvpPresenter().getPackageByType(page, bundleType);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        getMvpPresenter().getPackageByType(page, bundleType);
    }
}