package com.yiyue.user.module.mine.order.time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentTimeManagerBinding;
import com.yiyue.user.model.vo.bean.TimeBean;
import com.yiyue.user.model.vo.bean.TimeManagerBean;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间管理
 * <p>
 * Created by zm on 2018/11/12.
 */
@CreatePresenter(TimeManagerPresenter.class)
public class TimeManagerFragment extends BaseMvpFragment<ITimeManagerView, TimeManagerPresenter>
        implements ITimeManagerView, ClickHandler{
    private static final String ARGUMENTS_DATE = "date";

    FragmentTimeManagerBinding mBinding;
    private TimeManagerAdapter mAdapter;

    private String stylistId; // 美发师id
    private String storeId; // 门店id
    private String orderId; // 门店id
    private String date; // 哪一天
    /**
     * 切换刷新
     */
    protected boolean isCreated = false;

    /**
     *
     * @param date 时间
     * @return
     */
    public static TimeManagerFragment newInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTS_DATE, date);
        TimeManagerFragment fragment = new TimeManagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 标记
        isCreated = true;
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            stylistId = bundle.getString(UpdateTimeActivity.EXTRAS_STYLIST_ID);
            storeId = bundle.getString(UpdateTimeActivity.EXTRAS_STORE_ID);
            orderId = bundle.getString(UpdateTimeActivity.EXTRAS_ORDER_ID);
        }
        date = getArguments().getString(ARGUMENTS_DATE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_time_manager;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentTimeManagerBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(4, 30, false));
        mAdapter = new TimeManagerAdapter(getContext());
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
               for (TimeBean timeBean : mAdapter.getDatas()) {
                   timeBean.setChecked(false);
               }
               mAdapter.getDatas().get(position).setChecked(true);
               mAdapter.notifyDataSetChanged();
            }
        });
        mBinding.recycleView.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            loadData();
        }
    }


    @Override
    protected void loadData() {
        getMvpPresenter().getStylistDateTime(stylistId, storeId, date);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void setDatas(TimeManagerBean data) {
        List<String > workTimes = new Gson().fromJson(data.getWorktime(), ArrayList.class);
        List<String> locktimes = new Gson().fromJson(data.getLocktime(), ArrayList.class);
        List<String> resttime = null;
        if (data.getResttime()!=null){
            resttime = new Gson().fromJson(data.getResttime(), ArrayList.class);
        }

        ArrayList<TimeBean> timeBeans = new ArrayList<>();

        if (workTimes != null && !workTimes.isEmpty()) {
            for (String workTime : workTimes) {
                TimeBean timeBean = new TimeBean();
                timeBean.setTime(workTime);
                if (locktimes != null && !locktimes.isEmpty()) {
                    for (String lockTime : locktimes) {
                        if (lockTime.equals(workTime)) {
                            locktimes.remove(workTime);
                            timeBean.setLock(true);
                            break;
                        }
                    }
                }
                if (resttime != null && !resttime.isEmpty()) {
                    for (String restTime : resttime) {
                        if (restTime!=null&&restTime.equals(workTime)) {
                            resttime.remove(workTime);
                            timeBean.setLock(true);
                            break;
                        }
                    }
                }
                timeBeans.add(timeBean);
            }
        }
        mAdapter.setDatas(timeBeans, true);
    }

    @Override
    public void updateOrderTimeSuccess() {
        showToast("已成功更改预约时间.");
        getActivity().finish();
    }

    public String getDate() {
        return date;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String usetime = mAdapter.getSelectTime();
                if(TextUtils.isEmpty(usetime)) {
                    showToast("请选择预约时间.");
                    return;
                }
                getMvpPresenter().updateOrderTime(orderId, date.replace("/",""), usetime);
                break;
        }
    }
}
