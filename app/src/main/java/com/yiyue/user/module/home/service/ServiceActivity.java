package com.yiyue.user.module.home.service;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.YLApplication;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.databinding.ActivityServiceBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.ServiceStatus;
import com.yiyue.user.module.home.service.bundle.ServiceBundleFragment;
import com.yiyue.user.module.home.service.coupon.ServiceCouponFragment;
import com.yiyue.user.module.home.service.leaderboard.LeaderBoardFragment;
import com.yiyue.user.module.home.service.works.ServiceWorksFragment;
import com.yiyue.user.module.home.stylist.SearchStylistActivity;
import com.yiyue.user.module.home.stylist.StylistFragment;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 服务
 * <p>
 * Created by wqy on 2018/10/30.
 */
public class ServiceActivity extends BaseAppCompatActivity {

    ActivityServiceBinding serviceBinding;
    @ServiceStatus.ServiceType
    private int serviceType = ServiceStatus.SERVICE_WASH_BLOW;
    private final ArrayList<Fragment> fragments = new ArrayList<>();

    private final String[] labels = new String[]{
            YLApplication.getContext().getString(R.string.home_wash_blow),
            YLApplication.getContext().getString(R.string.home_hot_dyeing),
            YLApplication.getContext().getString(R.string.home_hair_extension),
            YLApplication.getContext().getString(R.string.home_nursing),
            YLApplication.getContext().getString(R.string.home_leaderboard),
            YLApplication.getContext().getString(R.string.home_works),
            YLApplication.getContext().getString(R.string.home_package_coupon),
            YLApplication.getContext().getString(R.string.home_package)
    };

    {
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_CATEGORY_1));
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_CATEGORY_2));
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_CATEGORY_3));
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_CATEGORY_4));
        fragments.add(LeaderBoardFragment.newInstance());
        fragments.add(ServiceWorksFragment.newInstance());
        fragments.add(ServiceCouponFragment.newInstance());
        fragments.add(ServiceBundleFragment.newInstance());
    }

    public static void startActivity(Context context, @ServiceStatus.ServiceType int serviceType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_service_TYPE, serviceType);
        startActivity(context, ServiceActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        hasExtras();
        initView();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serviceType = bundle.getInt(Constants.EXTRA_service_TYPE, ServiceStatus.SERVICE_WASH_BLOW);
        }
    }

    private void initView() {
        serviceBinding = (ActivityServiceBinding) viewDataBinding;
        serviceBinding.titleView.setLeftClickListener(v -> finish());
        serviceBinding.titleView.setRightImgClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_TYPE, Constants.SEARCH_FROM_STYLIST);
            startActivity(ServiceActivity.this, SearchStylistActivity.class, bundle);
        });
        setTable(serviceBinding.tableLayout, getLayoutInflater());
        serviceBinding.viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        serviceBinding.viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(serviceBinding.tableLayout));
        serviceBinding.tableLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(serviceBinding.viewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                serviceBinding.viewPage.setCurrentItem(tab.getPosition(), false);
            }
        });
        // tableLayout设置横向滑动
        serviceBinding.tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 是否允许viewPage滑动切换
        serviceBinding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        serviceBinding.viewPage.setOffscreenPageLimit(4);
        // 加载第几个页面
        serviceBinding.viewPage.setCurrentItem(serviceType, false);
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
}
