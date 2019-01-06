package com.yiyue.user.module.mine.stylist.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityOrderConfirmBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.OrderStatus;
import com.yiyue.user.model.vo.bean.CouponBean;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.mine.order.OrderCenterActivity;
import com.yiyue.user.module.mine.stylist.coupons.SelectCouponsActivity;
import com.yiyue.user.module.mine.stylist.project.ProjectListActivity;
import com.yiyue.user.module.mine.stylist.selectstore.SelectStoreActivity;
import com.yiyue.user.module.mine.stylist.time.TimeSelectionActivity;
import com.yiyue.user.module.mine.wallet.buy.PayActivity;
import com.yiyue.user.util.CacheActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.MathematicsUtils;
import com.yiyue.user.widget.dialog.YLDialog;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 确认订单
 * Created by wqy on 2018/11/6.
 */
@CreatePresenter(OrderConfirmPresenter.class)
public class OrderConfirmActivity extends BaseMvpAppCompatActivity<OrderConfirmView, OrderConfirmPresenter> implements OrderConfirmView, ClickHandler {
    private CreateOrderBody createOrderBody;
    private ActivityOrderConfirmBinding mConfirmBinding;
    private Bundle mBundle;
    public static int FROMACTIVITY = 1001;
    private Intent mStartActivityIntent;
    private UserBean userBean;
    public static String APPOINTMENTTIME="appointmentTime";
    private String mTempUserTime;
    private String time="预约时间：";

    private long ORDER_SUBMIT_SPACE_TIME = 2000L; // 订单提交间隔时间
    private long ORDER_SUBMIT_OLD_TIME = 0L; // 记录订单提交时间

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void init() {
        initView();
        initData();

    }

    private void initData() {
        mBundle = getIntent().getExtras();
        mStartActivityIntent = new Intent();
        createOrderBody = getIntent().getParcelableExtra(Constants.STYLIST_ORDER_BODY);
        createOrderBody.setIsResult("1");
        userBean = (UserBean) getIntent().getSerializableExtra(Constants.USER_INFO_BODY);
        mBundle.putParcelable(Constants.STYLIST_ORDER_BODY, createOrderBody);
        mStartActivityIntent.putExtras(mBundle);
        if (createOrderBody != null) {
            DLog.d("提交订单" + createOrderBody.toString());
            writeData(createOrderBody);
            //获取用户优惠卷
            getMvpPresenter().getCouponByUserId(createOrderBody.getUserId(),createOrderBody.getStylistId(),mConfirmBinding.tvTime.getText().toString().replace(time,""));
        }
        if (userBean != null) {
            mConfirmBinding.tvName.setText(FormatUtil.Formatstring(userBean.getNickname()));
            mConfirmBinding.tvRank.setText(FormatUtil.Formatstring(userBean.getPosition()));
            ImageLoader.loadImage(mConfirmBinding.ivAvatar, userBean.getHeadImg());
            Drawable girlPic = getResources().getDrawable(R.drawable.icon_girl);
            Drawable boyPic = getResources().getDrawable(R.drawable.icon_boy);
            mConfirmBinding.tvName.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, userBean.getGender() == 1 ? boyPic : girlPic, null);
        }
    }

    private void writeData(CreateOrderBody createOrderBody) {
        mConfirmBinding.tvServiceContent.setText(FormatUtil.Formatstring("服务项目：" + createOrderBody.getServiceName()));
        mConfirmBinding.tvServicePrice.setText(FormatUtil.Formatstring("￥" + createOrderBody.getOrderamount()));
        mConfirmBinding.tvStoreName.setText(FormatUtil.Formatstring("服务门店：" + createOrderBody.getStoreName()));

        if (!TextUtils.isEmpty(createOrderBody.getDay())) {
            String y = createOrderBody.getDay().substring(0, 4);
            String m = createOrderBody.getDay().substring(4, 6);
            String d = createOrderBody.getDay().substring(6);

            mConfirmBinding.tvTime.setText(FormatUtil.Formatstring(time + y + "-" + m + "-" + d + " " + createOrderBody.getUsetime()));
        }
        //根据优惠卷,计算价格
        initPriceSum(createOrderBody);
        //套餐
        if (createOrderBody.getServicemodel() != null && createOrderBody.getServicemodel().equals("2")) {
            mConfirmBinding.llCoupons.setVisibility(View.GONE);
            mConfirmBinding.tvServicePrice.setText(String.format(getString(R.string.RMB),"0.00"));
            mConfirmBinding.tvPriceSum.setText(String.format(getString(R.string.RMB),"0.00"));
        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        if (!CacheActivity.activityList.contains(OrderConfirmActivity.this)) {
            CacheActivity.addActivity(OrderConfirmActivity.this);
        }
        mConfirmBinding = (ActivityOrderConfirmBinding) viewDataBinding;
        mConfirmBinding.setClick(this);
        mConfirmBinding.titleView.setLeftClickListener(view -> finish());
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_service_content:
                //服务项目
                if (createOrderBody.getServicemodel().equals("2")){
                    ToastUtils.shortToast("此为套餐,不可再选择其他服务");
                    return;
                }
                mStartActivityIntent.setClass(this, ProjectListActivity.class);
                startActivityForResult(mStartActivityIntent, FROMACTIVITY);
                break;
            case R.id.rl_store_name:
                //服务门店
                mStartActivityIntent.setClass(this, SelectStoreActivity.class);
                startActivityForResult(mStartActivityIntent, FROMACTIVITY);
                break;
            case R.id.rl_time:
                //预约时间
                mStartActivityIntent.setClass(this, TimeSelectionActivity.class);
                startActivityForResult(mStartActivityIntent, FROMACTIVITY);
                break;
            case R.id.rl_coupons:
                //优惠卷
                mStartActivityIntent.setClass(this, SelectCouponsActivity.class);
                mStartActivityIntent.putExtra(APPOINTMENTTIME,mConfirmBinding.tvTime.getText().toString().replace(time,""));
                startActivityForResult(mStartActivityIntent, FROMACTIVITY);
                break;
            case R.id.tv_ordre_commit:
                long currentTime = System.currentTimeMillis();
                if (currentTime - ORDER_SUBMIT_OLD_TIME < ORDER_SUBMIT_SPACE_TIME) {
                    ToastUtils.shortToast("订单提交过于频繁，请稍后再试...");
                    return;
                }
                ORDER_SUBMIT_OLD_TIME = currentTime; // 更新订单提交时间
                //提交订单
                createOrderBody.setRemark(mConfirmBinding.etRemark.getText().toString().trim());
                getMvpPresenter().createOrder(createOrderBody, this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FROMACTIVITY) {
            mTempUserTime =this.createOrderBody.getUsetime();
            createOrderBody=data.getExtras().getParcelable(Constants.STYLIST_ORDER_BODY);
            mBundle.putParcelable(Constants.STYLIST_ORDER_BODY, createOrderBody);
            mStartActivityIntent.putExtras(mBundle);
            writeData(createOrderBody);
        }
    }

    private void initPriceSum(CreateOrderBody createOrderBody ) {
        mConfirmBinding.tvPriceSum.setText(String.format(getString(R.string.RMB),createOrderBody.getOrderamount()));
        if (mTempUserTime!=null&&!mTempUserTime.equals(createOrderBody.getUsetime())){
            deleteCouponInfo();
            mConfirmBinding.tvCouponsSum.setText("您已更改预约时间,请重新选择优惠卷");
            return;
        }
        if (createOrderBody.getCoupontype() != null) {
            double f = Double.valueOf(!createOrderBody.getOrderamount().equals("null") ? createOrderBody.getOrderamount() : "0");//订单总价
            double f2 = Double.valueOf(!createOrderBody.getCouponamount().equals("null") ? createOrderBody.getCouponamount() : "0");//优惠金额/折扣
            double f3 = Double.valueOf(!createOrderBody.getAmount().equals("null") ? createOrderBody.getAmount() : "0");//满多少才 减/折扣
            switch (createOrderBody.getCoupontype()) {
                case "1":
                    //满减
                    if (f >= f3) {
                        double p = MathematicsUtils.sub(f ,f2);
                        mConfirmBinding.tvPriceSum.setText(FormatUtil.Formatstring("￥" + p + "(" + f + "-" + f2 + ")"));
                        mConfirmBinding.tvCouponsSum.setText("已选");
                    } else {
                        deleteCouponInfo();
                        showToast("此优惠劵需满" + f3 + "元");
                    }
                    break;
                case "2":
                    //折扣
                    double p2 = MathematicsUtils.div(MathematicsUtils.multiply(f,f2),10);
                    mConfirmBinding.tvPriceSum.setText(FormatUtil.Formatstring("￥" + p2 + "(" + f + "×" + f2 + "折)"));
                    mConfirmBinding.tvCouponsSum.setText("已选");
                    break;
                case "3":
                    //抵用
                    if (f2<=f){
                        double p = MathematicsUtils.sub(f ,f2);
                        mConfirmBinding.tvPriceSum.setText(FormatUtil.Formatstring("￥" + p + "(" + f + "-" + f2 + ")"));
                        mConfirmBinding.tvCouponsSum.setText("已选");
                    }else {
                        deleteCouponInfo();
                        showToast("订单金额不能大于抵扣金额");
                    }
                    break;
            }
        }
    }

    private void deleteCouponInfo() {
        createOrderBody.setAmount(null);
        createOrderBody.setCouponamount(null);
        createOrderBody.setCoupondirection(null);
        createOrderBody.setCouponId(null);
        createOrderBody.setCoupontype(null);
    }

    @Override
    public void onGetCouponSuccess(ArrayList<CouponBean> couponBeans) {
        mConfirmBinding.tvCouponsSum.setText(FormatUtil.Formatstring(couponBeans.size() + "张可用"));
    }

    @Override
    public void createOrderSuccess(CreateOrderBean createOrderBean) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ISORDER, 1);
        if (!TextUtils.isEmpty(createOrderBody.getPackageId())){
            createOrderBean.setPackageId(Integer.valueOf(createOrderBody.getPackageId()));
        }
        createOrderBean.setTimes(30 * 60);
        bundle.putParcelable(Constants.STYLIST_ORDER_RESULT, createOrderBean);
        PayActivity.startActivity(OrderConfirmActivity.this, PayActivity.class, bundle);
        finish();
    }

    @Override
    public void showJumpOrderDialog() {
        new YLDialog.Builder(this)
                .setTitle("提交失败提示")
                .setMessage("您还有待支付订单未处理")
                .setNegativeButton("不处理", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("去支付", (dialog, which) -> {
                    dialog.dismiss();
                    OrderCenterActivity.startActivity(OrderConfirmActivity.this, OrderStatus.ORDER_PAYMENT);
                }).create().show();
    }
}
