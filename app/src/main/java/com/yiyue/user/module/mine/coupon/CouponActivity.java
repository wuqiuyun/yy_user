package com.yiyue.user.module.mine.coupon;

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
import com.yiyue.user.databinding.ActivityMineCouponBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.MineCouponStatus;
import com.yiyue.user.module.mine.coupon.usage.UsageActivity;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 我的优惠券
 * <p>
 * Create by zm on 2018/9/29
 */
public class CouponActivity extends BaseAppCompatActivity {

    ActivityMineCouponBinding mBinding;
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    @MineCouponStatus.MineCouponType
    private int mineOrderType = MineCouponStatus.COUPON_FULL_REDUCTION;

    private String[] labels = new String[]{
            YLApplication.getContext().getString(R.string.coupon_reduce),
            YLApplication.getContext().getString(R.string.coupon_discount),
            YLApplication.getContext().getString(R.string.coupon_offset),
            YLApplication.getContext().getString(R.string.coupon_bundle)
    };

    {
        fragments.add(MineCouponFragment.newInstance(MineCouponStatus.COUPON_FULL_REDUCTION));
        fragments.add(MineCouponFragment.newInstance(MineCouponStatus.COUPON_DISCOUNT));
        fragments.add(MineCouponFragment.newInstance(MineCouponStatus.COUPON_DEDUCTION));
        fragments.add(MineCouponFragment.newInstance(MineCouponStatus.COUPON_PACKAGE));
    }

    public static void startActivity(Context context, @MineCouponStatus.MineCouponType int mineCouponType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_MINE_COUPON_TYPE, mineCouponType);
        startActivity(context, CouponActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_coupon;
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
            this.mineOrderType = bundle.getInt(Constants.EXTRA_MINE_COUPON_TYPE, MineCouponStatus.COUPON_FULL_REDUCTION);
        }
    }

    private void initView() {
        mBinding = (ActivityMineCouponBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsageActivity.startActivity(CouponActivity.this ,UsageActivity.class);
            }
        });
        setTable(mBinding.tableLayout, getLayoutInflater());
        mBinding.viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        mBinding.viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tableLayout));
        mBinding.tableLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.viewPage.setCurrentItem(tab.getPosition(), false);
            }
        });
        // tableLayout设置横向滑动
        mBinding.tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 是否允许viewPage滑动切换
        mBinding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        mBinding.viewPage.setOffscreenPageLimit(1);
        // 加载第几个页面
        mBinding.viewPage.setCurrentItem(mineOrderType, false);
    }

    private void setTable(TabLayout tableLayout, LayoutInflater inflater) {
        for (String label : labels) {
            TabLayout.Tab newTab = tableLayout.newTab();
            View tabView = inflater.inflate(R.layout.tab_layout_home, null);
            newTab.setCustomView(tabView);

            TextView tvLabel = tabView.findViewById(R.id.tv_label);
            tvLabel.setText(label);
            tableLayout.addTab(newTab);
        }
    }
}
