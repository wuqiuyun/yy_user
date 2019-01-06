package com.yiyue.user.module.home.service.bundle.detail;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityBundleDetailBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.BundleDetailsBean;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.module.mine.wallet.buy.PayActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;

import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 套餐详情
 * Created by wqy on 2018/11/5.
 */
@CreatePresenter(BundleDetailPresenter.class)
public class BundleDetailActivity extends BaseMvpAppCompatActivity<IBundleDetailView, BundleDetailPresenter> implements IBundleDetailView, ClickHandler {

    private static final String TYPE_SINGLE = "1"; // 1 单项多次套餐
    private static final String TYPE_MULTIPLE = "2"; // 2 多项单次套餐
    private ActivityBundleDetailBinding detailBinding;
    private String page = "1";
    private String type = "";
    private String serviceId;
    private BundleDetailsBean bundleDetailsBeans;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bundle_detail;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.type = bundle.getString("type");
            this.serviceId = bundle.getString(Constants.SERVICEID);
        }
    }

    private void initView() {
        detailBinding = (ActivityBundleDetailBinding) viewDataBinding;
        detailBinding.titleView.setLeftClickListener(view -> finish());
        detailBinding.setClick(this);
    }

    private void loadData() {
        getMvpPresenter().getPackageByServiceId(page, type, serviceId);
    }

    @Override
    public void showToast(String message) {

    }

    //详情
    @Override
    public void getDetailsSuccess(BundleDetailsBean bundleDetailsBean) {
        bundleDetailsBeans = bundleDetailsBean;
        type = String.valueOf(bundleDetailsBean.getType());

        if (TYPE_MULTIPLE.equals(type)) {//多项套餐
            // 标题
            detailBinding.tvPackageType.setText("多项套餐");
            //服务类型
            detailBinding.tvType.setText("多项套餐");
            // 内容
            detailBinding.tvContent.setText(bundleDetailsBean.getCategoryname());
        } else { // 单项套餐
            // 标题
            detailBinding.tvPackageType.setText("单项套餐");
            //服务类型
            detailBinding.tvType.setText("单项套餐");
            // 内容
            detailBinding.tvContent.setText(String.format(bundleDetailsBean.getCategoryname()));
        }

        // 发布者
        detailBinding.tvStylistName.setText(bundleDetailsBean.getStylistName());
        detailBinding.tvStylistName.setText(bundleDetailsBean.getStylistName());
        detailBinding.tvStylistName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        // 发布者
        detailBinding.tvTvStylistName2.setText(String.format(getString(R.string.coupon_stylist), bundleDetailsBean.getStylistName()));
        //使用次数
        detailBinding.tvUseTimes.setText(String.valueOf(bundleDetailsBean.getTimes()));
        detailBinding.tvTimes.setText(String.format(getString(R.string.bundle_times),
                bundleDetailsBean.getTimes() + ""));

        //套餐价
        detailBinding.tvPackagePrice.setText(String.format(String.valueOf(bundleDetailsBean.getPrice())));
        // 应付
        detailBinding.tvPrice.setText(String.format(getString(R.string.coupon_price),
                bundleDetailsBean.getPrice() + ""));
        // 原价
        detailBinding.tvCostprice.setText(String.format(String.format(getString(R.string.coupon_package_costprice), bundleDetailsBean.getCostprice() + "")));
        detailBinding.tvCostprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_buy_bundle: // 购买套餐
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(BundleDetailActivity.this, LoginActivity.class);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ISORDER, 3);
                bundle.putSerializable(Constants.BUNDLEDETAIL, bundleDetailsBeans);
                PayActivity.startActivity(BundleDetailActivity.this, PayActivity.class, bundle);
                finish();
                break;
            case R.id.tv_stylist_name: // 美发师详情
                if (bundleDetailsBeans.getStylistId() == null) return;
                Bundle bundle2 = new Bundle();
                bundle2.putString(STYLISTId, bundleDetailsBeans.getStylistId());
                StylistDetailsActivity.startActivity(this, StylistDetailsActivity.class, bundle2);
                break;
        }
    }
}
