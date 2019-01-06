package com.yiyue.user.module.home.store.reservation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.HomeActivityStoreReservationBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.ChooseStylistBean;
import com.yiyue.user.model.vo.result.ChooseStylistResult;
import com.yiyue.user.module.mine.order.OrderCenterActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

import static com.yiyue.user.model.constant.Constants.STYLISTId;


/**
 * 预约
 * Created by wqy on 2018/11/9.
 */
@CreatePresenter(StoreReservationPresenter.class)
public class StoreReservationActivity extends BaseMvpAppCompatActivity<IStoreReservationView, StoreReservationPresenter>
        implements IStoreReservationView, ClickHandler {

    private HomeActivityStoreReservationBinding mBinding;
    private StoreReservationAdapter reservationAdapter;
    private String stylistId = "";
    private String storeId = "";

    public static void startActivity(Context context, String storeId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.STORE_ID, storeId);
        startActivity(context, StoreReservationActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_activity_store_reservation;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
        StatusBarUtil.setLightMode(this);
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            storeId = bundle.getString(Constants.STORE_ID);
        }
    }

    private void initView() {
        mBinding = (HomeActivityStoreReservationBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.titleView.setLeftClickListener(view -> finish());
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycleView.addItemDecoration(new SpaceItemHorizontalDecoration(10));
        reservationAdapter = new StoreReservationAdapter(this);
        mBinding.recycleView.setAdapter(reservationAdapter);

        reservationAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < reservationAdapter.getDatas().size(); i++) {
                    ChooseStylistBean chooseStylistBean = reservationAdapter.getDatas().get(i);
                    if (i == position) {
                        chooseStylistBean.setStatus(true);
                        stylistId = String.valueOf(chooseStylistBean.getStylistId());
                    } else {
                        chooseStylistBean.setStatus(false);
                    }
                }
                reservationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadData() {
        getMvpPresenter().chooseStylists(storeId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (!TextUtils.isEmpty(stylistId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(STYLISTId, stylistId);
                    StylistDetailsActivity.startActivity(StoreReservationActivity.this, StylistDetailsActivity.class, bundle);
                } else {
                    ToastUtils.shortToast("请选择美发师");
                }
                break;
        }
    }

    // 可选美发师列表
    @Override
    public void getStylistsSuccess(ChooseStylistResult chooseStylistResult) {
        ArrayList<ChooseStylistBean> data = chooseStylistResult.getData();
        reservationAdapter.setDatas(data, true);
        ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_CROP)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.home_bg)
                .setErrorResId(R.drawable.home_bg)
                .build();
        if (data != null && data.size() > 0) {
            mBinding.llNoData.setVisibility(View.GONE);
            ImageLoader.loadImage(mBinding.ivBg, data.get(0).getStoreCover(), headConfig, null);
            ImageLoader.loadImage(mBinding.ivPic, data.get(0).getStoreHeadImg(), headConfig, null);
        } else {
            mBinding.llNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String message) {

    }
}
