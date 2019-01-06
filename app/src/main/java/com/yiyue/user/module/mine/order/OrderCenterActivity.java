package com.yiyue.user.module.mine.order;

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
import com.yiyue.user.databinding.ActivityOrderBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.OrderStatus;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 订单中心
 * <p>
 *  Create by zm on 2018/9/27.
 */
public class OrderCenterActivity extends BaseAppCompatActivity {

    ActivityOrderBinding orderBinding;

    @OrderStatus.OrderType
    private int orderType = OrderStatus.ORDER_UNFINISHED;

    private final String[] labels = new String[]{
            YLApplication.getContext().getString(R.string.label_title_payment),
            YLApplication.getContext().getString(R.string.label_title_pass),
//            YLApplication.getContext().getString(R.string.label_title_unprocessed),
            YLApplication.getContext().getString(R.string.label_title_unfinished),
            YLApplication.getContext().getString(R.string.label_title_complete)
    };

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    {
        fragments.add(OrderFragment.newInstance(OrderStatus.ORDER_PAYMENT));
        fragments.add(OrderFragment.newInstance(OrderStatus.ORDER_PASS));
//        fragments.add(OrderFragment.newInstance(OrderStatus.ORDER_UNPROCESSED));
        fragments.add(OrderFragment.newInstance(OrderStatus.ORDER_UNFINISHED));
        fragments.add(OrderFragment.newInstance(OrderStatus.ORDER_COMPLETE));
    }

    /**
     * start activity
     * @param context
     * @param orderType 订单状态{@link com.yiyue.user.model.constant.OrderStatus}
     */
    public static void startActivity (Context context, @OrderStatus.OrderType int orderType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_ORDER_TYPE, orderType);
        startActivity(context, OrderCenterActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        initEvent();
        StatusBarUtil.setLightMode(this);
    }

    private void hasExtras () {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderType = bundle.getInt(Constants.EXTRA_ORDER_TYPE, OrderStatus.ORDER_UNFINISHED);
        }
    }

    private void initView() {
        orderBinding = (ActivityOrderBinding) viewDataBinding;
        setTable(orderBinding.tableLayout, getLayoutInflater());
        orderBinding.viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        orderBinding.viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(orderBinding.tableLayout));
        orderBinding.tableLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(orderBinding.viewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               orderBinding.viewPage.setCurrentItem(tab.getPosition(), false);
            }
        });
        // 是否允许viewPage滑动切换
        orderBinding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        orderBinding.viewPage.setOffscreenPageLimit(1);
        // 加载第几个页面
        switch (orderType) {
            case OrderStatus.ORDER_PAYMENT:
                orderBinding.viewPage.setCurrentItem(0, false);
                break;
            case OrderStatus.ORDER_PASS:
                orderBinding.viewPage.setCurrentItem(1, false);
                break;
            case OrderStatus.ORDER_UNFINISHED:
                orderBinding.viewPage.setCurrentItem(2, false);
                break;
            case OrderStatus.ORDER_COMPLETE:
                orderBinding.viewPage.setCurrentItem(3, false);
                break;
        }
    }

    private void initEvent() {
        // 返回
        orderBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    /**
     * 设置Tab
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
