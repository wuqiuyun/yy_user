package com.yiyue.user.module.home.service.setting;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityServiceSettingBinding;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.widget.mytimepickview.CustomTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 服务设置
 * Created by lvlong on 2018/10/9.
 */
public class ServiceSettingActivity extends BaseMvpAppCompatActivity<ServiceSettingView, ServiceSettingPresenter> implements ClickHandler, ServiceSettingView {

    ActivityServiceSettingBinding mBinding;

    private CustomTimePicker mTimePicker;

    private ServiceSettingAdapter timeAdapter;
    private ArrayList<ServiceSettingBean> timeData = new ArrayList<>();

    private ArrayList<ServiceSettingBean> facilitiesData = new ArrayList<>();
    private ServiceSettingAdapter facilitiesAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_setting;
    }

    @Override
    protected void init() {

        mBinding = (ActivityServiceSettingBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();
        loadData();
        initTimerPicker();
    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> {
            finish();
        });

        //设置日期适配器
        setTimeAdapter();

        //设置美发设施适配器
        setFacilitiesAdapter();

    }

    private void loadData() {
        mBinding.titleView.setLeftClickListener(view -> finish());

        //添加日期数据
        addTimeData();

        //添加美发设施数据
        addFacilitiesData();

    }


    private void setTimeAdapter() {
        RecyclerView timeRecyclerView = mBinding.rvBusinessCycle;
        timeAdapter = new ServiceSettingAdapter(this);
        timeAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                timeData.get(position).setChecked(!timeData.get(position).isChecked());
                timeAdapter.notifyDataSetChanged();
                ToastUtils.shortToast("点击了第" + position + "条目");
            }
        });
        timeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        timeRecyclerView.setAdapter(timeAdapter);
        timeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
    }

    private void setFacilitiesAdapter() {
        RecyclerView facilitiesRecyclerView = mBinding.serviceFacilitiesCycle;
        facilitiesAdapter = new ServiceSettingAdapter(this);
        facilitiesAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                facilitiesData.get(position).setChecked(!facilitiesData.get(position).isChecked());
                facilitiesAdapter.notifyDataSetChanged();
                ToastUtils.shortToast("点击了第" + position + "条目");
            }
        });
        facilitiesRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        facilitiesRecyclerView.setAdapter(facilitiesAdapter);
        facilitiesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
    }

    private void addTimeData() {
        timeData.add(new ServiceSettingBean(getString(R.string.monday), true));
        timeData.add(new ServiceSettingBean(getString(R.string.tuesday), true));
        timeData.add(new ServiceSettingBean(getString(R.string.wednesday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.thursday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.friday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.saturday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.sunday), false));
        timeAdapter.setDatas(timeData, true);
    }

    private void addFacilitiesData() {

        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities1), true));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities2), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities3), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities4), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities5), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities6), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities7), false));
        facilitiesData.add(new ServiceSettingBean(getString(R.string.service_facilities8), false));

        facilitiesAdapter.setDatas(facilitiesData, true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_stores_business_time:     //修改时间
            mTimePicker.show();
                break;

            case R.id.btn_ok:         //确定

                finish();

                break;
        }

    }

    private void initTimerPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        String time = sdf.format(new Date());
        mTimePicker = new CustomTimePicker(this, "营业时间", time, time, mSelectTime ->
        {
            String businessTime = mSelectTime.getStartTime() + "-" + mSelectTime.getEndTime();
            mBinding.tvStoresBusinessTime.setText(businessTime);
        });
    }


    @Override
    public void showToast(String message) {

    }

}
