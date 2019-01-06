package com.yiyue.user.module.mine.stylist.time;

import android.content.Intent;
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
import com.yiyue.user.databinding.FragmentTimeSelectionBinding;
import com.yiyue.user.helper.AppManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.TimeBean;
import com.yiyue.user.model.vo.bean.TimeManagerBean;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.mine.order.time.ITimeManagerView;
import com.yiyue.user.module.mine.order.time.TimeManagerAdapter;
import com.yiyue.user.module.mine.order.time.TimeManagerPresenter;
import com.yiyue.user.module.mine.order.time.UpdateTimeActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yiyue.user.module.mine.stylist.project.ProjectListActivity;
import com.yiyue.user.module.mine.stylist.selectstore.SelectStoreActivity;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间管理
 * <p>
 * Created by zm on 2018/11/12.
 */
@CreatePresenter(TimeSelectionPresenter.class)
public class TimeSelectionFragment extends BaseMvpFragment<ITimeSelectionView, TimeSelectionPresenter>
        implements ITimeSelectionView, ClickHandler{
    private static final String ARGUMENTS_DATE = "date";

    FragmentTimeSelectionBinding mBinding;
    private TimeManagerAdapter mAdapter;
    private Bundle mBundle;
    private CreateOrderBody createOrderBody;
    private String date; // 哪一天
    private UserBean userBean;
    /**
     * 切换刷新
     */
    protected boolean isCreated = false;
    /**
     *
     * @param date 时间
     * @return
     */
    public static TimeSelectionFragment newInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTS_DATE, date);
        TimeSelectionFragment fragment = new TimeSelectionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 标记
        isCreated = true;
        mBundle = getActivity().getIntent().getExtras();
        if (mBundle!=null){
            createOrderBody = mBundle.getParcelable(Constants.STYLIST_ORDER_BODY);
            userBean = (UserBean) mBundle.getSerializable(Constants.USER_INFO_BODY);
        }
        date = getArguments().getString(ARGUMENTS_DATE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_time_selection;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentTimeSelectionBinding) viewDataBinding;
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
                createOrderBody.setUsetime(mAdapter.getDatas().get(position).getTime());
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
        getMvpPresenter().getStylistDateTime(createOrderBody.getStylistId(), createOrderBody.getStoreId(),date);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void setDatas(TimeManagerBean data) {
        createOrderBody.setDay(String.valueOf(data.getDay()));
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
                        if (lockTime!=null&&lockTime.equals(workTime)) {
                            locktimes.remove(lockTime);
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



    public String getDate() {
        return date;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:

                if (createOrderBody.getUsetime()==null){
                    showToast("请选择预约时间");
                    return;
                }
                mBundle.putParcelable(Constants.STYLIST_ORDER_BODY,createOrderBody);
                if (createOrderBody.getIsResult()!=null){
                    //提交订单页返回
                    Intent intent = new Intent();
                    intent.putExtra(Constants.STYLIST_ORDER_BODY,createOrderBody);
                    intent.putExtra(Constants.USER_INFO_BODY,userBean);
                    getActivity().setResult(OrderConfirmActivity.FROMACTIVITY,intent);
                    getActivity().finish();
                }else {
                    OrderConfirmActivity.startActivity(getActivity(),OrderConfirmActivity.class,mBundle);
                    AppManager.getAppManager().finishActivity(TimeSelectionActivity.class);
                    AppManager.getAppManager().finishActivity(SelectStoreActivity.class);
                    AppManager.getAppManager().finishActivity(ProjectListActivity.class);
//                    AppManager.getAppManager().finishActivity(StylistDetailsActivity.class);//zm注释
                }
                break;
        }
    }

}
