package com.yiyue.user.module.home.service.coupon;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.YLApplication;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentServiceCouponBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.bean.ServiceCouponBean;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;

/**
 * 服务-> 优惠券
 * Created by wqy on 2018/11/1.
 */
@CreatePresenter(ServiceCouponPresenter.class)
public class ServiceCouponFragment extends BaseMvpFragment<IServiceCouponView, ServiceCouponPresenter>
        implements IServiceCouponView, OnRefreshListener, OnLoadMoreListener {
    private static final String TYPE_REDUCTION = "1"; // 满减券
    private static final String TYPE_DISCOUNT = "2"; // 折扣券
    private static final String TYPE_DEDUCTION = "3"; // 抵用券

    FragmentServiceCouponBinding mBinding;
    private ServiceCouponAdapter mAdapter;
    private ArrayList<CouponBean> data = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private ArrayList<ServiceCouponBean> list = new ArrayList<>();

    private final String[] labels = new String[]{
            YLApplication.getContext().getString(R.string.coupon_reduce),
            YLApplication.getContext().getString(R.string.coupon_discount),
            YLApplication.getContext().getString(R.string.coupon_offset),
    };
    private String userId = "";
    private String couponType = TYPE_REDUCTION;  // 优惠券类型
    private String mCouponId = ""; // 优惠券ID

    public static Fragment newInstance() {
        return new ServiceCouponFragment();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_coupon;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentServiceCouponBinding) viewDataBinding;
        initRefreshLoadLayout();
        setTable(mBinding.tableLayout, getLayoutInflater());

        mAdapter = new ServiceCouponAdapter(getContext(), Integer.valueOf(couponType) - 1);
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recycleView.setAdapter(mAdapter);
        setAdapter();
    }

    private void setAdapter() {
        mBinding.tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // 满减券
                        couponType = TYPE_REDUCTION;
                        mAdapter.setType(Integer.valueOf(couponType) - 1);
                        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
                        break;
                    case 1: // 折扣券
                        couponType = TYPE_DISCOUNT;
                        mAdapter.setType(Integer.valueOf(couponType) - 1);
                        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
                        break;
                    case 2:  // 抵用券
                        couponType = TYPE_DEDUCTION;
                        mAdapter.setType(Integer.valueOf(couponType) - 1);
                        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
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
        userId = AccountManager.getInstance().getUserId();
        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
        // 领取优惠券
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(getActivity(), LoginActivity.class);
                    return;
                }
                CouponBean couponBean = mAdapter.getDatas().get(position);
                getMvpPresenter().drawCoupon(couponBean.getCouponId() + "", couponType);

            }
        });
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
        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
    }

    @Override
    public void getCouponSuccess(ArrayList<CouponBean> serviceCouponBean) {
        mAdapter.setDatas(serviceCouponBean, true);
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getCouponFail() {
        mAdapter.clearData(true);
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void drawCouponSuccess() {
        showToast("领取成功");
        getMvpPresenter().getCouponByType(mCouponId, couponType, userId);
    }

    @Override
    public void drawCouponFail() {
    }
}