package com.yiyue.user.module.home.orders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.core.util.DateUtil;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityOrderStatisticsBinding;
import com.yiyue.user.widget.mytimepickview.CustomDatePicker;
import com.yl.core.util.StatusBarUtil;

import java.util.Date;

/**
 * 订单统计
 * Created by lvlong on 2018/10/13.
 */
public class OrderStatisticsActivity
        extends BaseMvpAppCompatActivity<OrderStatisticsView, OrderStatisticsPresenter>
        implements ClickHandler, OrderStatisticsView {

    ActivityOrderStatisticsBinding mBinding;

    private CustomDatePicker mDatePicker;
    private String mNowDate;
    private int type;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_statistics;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityOrderStatisticsBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();

        initDatePicker();

    }



    private void initView() {

        mBinding.titleView.setLeftClickListener(v -> finish());

        //设置适配器
        RecyclerView           orderRecycle = mBinding.orderRecycle;
        OrderStatisticsAdapter adapter      = new OrderStatisticsAdapter(this);
        orderRecycle.setLayoutManager(new LinearLayoutManager(this));
        orderRecycle.setAdapter(adapter);


    }

    private void initDatePicker() {

        mNowDate = DateUtil.date2Str(new Date() , DateUtil.FORMAT_YMD);
        mDatePicker = new CustomDatePicker(this, "2010-01-01", mNowDate, time -> {

            switch (type){

                case 0:
                    mBinding.tvStartTime.setText(time);
                    break;

                case 1:
                    mBinding.tvEndTime.setText(time);
                    break;
                default:
                    break;

            }

        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_start_time:       //选择开始时间
                type = 0;
                mDatePicker.show(mBinding.tvStartTime.getText().toString() );
                break;

            case R.id.tv_end_time:         //选择结束时间
                type = 1 ;
                mDatePicker.show(mNowDate);
                break;

        }

    }

    @Override
    public void showToast(String message) {

    }
}
