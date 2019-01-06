package com.yiyue.user.module.home.time;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityTimeManageBinding;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.module.home.service.setting.ServiceSettingAdapter;
import com.yiyue.user.widget.mytimepickview.CustomTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *
 * 时间管理
 * Created by lvlong on 2018/10/11.
 */
public class TimeManageActivity extends BaseMvpAppCompatActivity<TimeManageView , TimeManagePresenter> implements ClickHandler , TimeManageView {

    ActivityTimeManageBinding mBinding;

    private CustomTimePicker mTimePicker;

    private ServiceSettingAdapter timeAdapter;
    private ArrayList<ServiceSettingBean> timeData = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_time_manage;
    }

    @Override
    protected void init() {

        mBinding = (ActivityTimeManageBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();
        initData();

        initTimerPicker();

    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> finish());

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

    private void initData() {

        timeData.add(new ServiceSettingBean(getString(R.string.monday), true));
        timeData.add(new ServiceSettingBean(getString(R.string.tuesday), true));
        timeData.add(new ServiceSettingBean(getString(R.string.wednesday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.thursday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.friday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.saturday), false));
        timeData.add(new ServiceSettingBean(getString(R.string.sunday), false));
        timeAdapter.setDatas(timeData, true);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ll_order_time :         //服务时间

                mTimePicker.show();

                break;

            case R.id.btn_save :                  //保存

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
