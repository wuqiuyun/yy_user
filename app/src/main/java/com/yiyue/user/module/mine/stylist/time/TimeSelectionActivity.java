package com.yiyue.user.module.mine.stylist.time;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.api.StylistManagerApi;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.databinding.ActivityTimeSelectionBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.DayBean;
import com.yiyue.user.model.vo.bean.ShowtimeBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.mine.order.time.TimeManagerFragment;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yiyue.user.util.CacheActivity;
import com.yl.core.component.log.DLog;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 选择时间
 * <p>
 * Create by zm on 2018/10/12.
 */
public class TimeSelectionActivity extends BaseAppCompatActivity{
    private static String FORMAT_MD = "MM月dd日";

    ActivityTimeSelectionBinding mBinding;
    private List<DayBean> dayBeans;
    private Bundle  mBundle;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_time_selection;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        hasExtras();
        initView();
        initData();
    }

    private void hasExtras() {
        mBundle = getIntent().getExtras();
        if (mBundle!=null){
          CreateOrderBody createOrderBody = mBundle.getParcelable(Constants.STYLIST_ORDER_BODY);
            DLog.d("选择时间"+createOrderBody.toString());
        }
    }
    private void initView() {
        if (!CacheActivity.activityList.contains(TimeSelectionActivity.this)) {
            CacheActivity.addActivity(TimeSelectionActivity.this);
        }
        mBinding = (ActivityTimeSelectionBinding) viewDataBinding;
        // back
        mBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    private void initData() {
        new StylistManagerApi().getShowTime(new YLRxSubscriberHelper<BaseResponse<ShowtimeBean>>(this, true) {
            @Override
            public void _onNext(BaseResponse<ShowtimeBean> baseResponse) {
                String showTime = baseResponse.getData().getShowtime();
                int size;//返回查询的展示天数
                try {
                    size = Integer.valueOf(showTime);
                }catch (Exception e) {
                    size = 7;
                }
                initFragment(size);
            }
        });
    }

    private void initFragment(int size ) {
        dayBeans = setDays(size);
        setTable(mBinding.tableLayout, getLayoutInflater());
        setFragment();

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
        // 是否允许viewPage滑动切换
        mBinding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        mBinding.viewPage.setOffscreenPageLimit(1);
        // 加载第几个页面
        mBinding.viewPage.setCurrentItem(0, false);
    }

    private void setFragment() {
        for (DayBean dayBean : dayBeans) {
            fragments.add(TimeSelectionFragment.newInstance(dayBean.getDate()));
        }
    }

    /**
     * 设置Tab
     * @param tabLayout
     * @param inflater
     */
    private void setTable(TabLayout tabLayout, LayoutInflater inflater) {
        for (DayBean label : dayBeans) {
            TabLayout.Tab newTab = tabLayout.newTab();
            View tabView = inflater.inflate(R.layout.view_table_time, null);
            newTab.setCustomView(tabView);

            TextView tvTitle = tabView.findViewById(R.id.tv_title);
            tvTitle.setText(label.getWeek());
            TextView tvContent = tabView.findViewById(R.id.tv_content);
            tvContent.setText(label.getDate1());
            tabLayout.addTab(newTab);
        }
    }

    private List<DayBean> setDays(int size) {
        List<DayBean> dayBeans = new ArrayList<>();
        // 今天
        Date date = new Date();
        for (int i=0; i<size; i++){
            Date newDate = DateUtil.addDay(date, i);
            switch (i) {
                case 0:
                    dayBeans.add(new DayBean("今天", DateUtil.date2Str(newDate, DateUtil.FORMAT_YMD_), DateUtil.date2Str(newDate, FORMAT_MD)));
                    break;
                case 1:
                    dayBeans.add(new DayBean("明天", DateUtil.date2Str(newDate, DateUtil.FORMAT_YMD_), DateUtil.date2Str(newDate, FORMAT_MD)));
                    break;
                default:
                    dayBeans.add(new DayBean(DateUtil.getWeekOfDate(newDate), DateUtil.date2Str(newDate, DateUtil.FORMAT_YMD_), DateUtil.date2Str(newDate, FORMAT_MD)));
                    break;
            }
        }
        return dayBeans;
    }
}
