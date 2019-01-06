package com.yiyue.user.module.mine.store;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.amap.LocationPresenter;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentStoreBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.model.vo.requestbody.SortSearchRequesetBody;
import com.yiyue.user.module.home.store.StoreManagerActivity;
import com.yiyue.user.module.home.stores.SearchStoresActivity;
import com.yiyue.user.module.home.stores.StoreActivity;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.stylist.IUpDataFragment;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 门店
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StorePresenter.class)
public class StoreFragment extends BaseMvpFragment<IStoreView, StorePresenter>
        implements IStoreView, OnRefreshListener, OnLoadMoreListener, AMapLocationListener, IUpDataFragment {

    private StoreAdapter adapter;
    private ArrayList<StoreBean> data = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;

    private LocationPresenter locationPresenter;

    private String mUserId;
    private double lat;
    private double lng;

    private int page = 1;//页数
    private int size = 10;//每页数量

    private int fromActivity;//从哪个页面来的
    private String search;//搜索字段
    private SortSearchRequesetBody mSortSearchRequesetBody;
    private FragmentStoreBinding mBinding;

    public static Fragment newInstance(int from) {
        StoreFragment storeFragment = new StoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        storeFragment.setArguments(bundle);
        return storeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        fromActivity = bundle.getInt("from", 0);
        search = bundle.getString("search");
        mBinding = (FragmentStoreBinding) viewDataBinding;
        // init recycleview

        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.shap_divider_line_1dp)));
        mBinding.recycleView.addItemDecoration(divider);
        refreshLayout = mBinding.refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        initAdapter();

    }

    private void initAdapter() {
        switch (fromActivity) {
            case Constants.ACTIVITY_HOME_MORE://首页更多
                //注册监听
                StoreActivity activity = (StoreActivity) getActivity();
                activity.setIUpDataFragment(this);
                adapter = new StoreAdapter(getContext(), Constants.STORE_LIST_TYPE_1);
                break;
            case Constants.ACTIVITY_SEARCH://搜索
                SearchStoresActivity activity2 = (SearchStoresActivity) getActivity();
                activity2.setIUpDataFragment(this);
                adapter = new StoreAdapter(getContext(), Constants.STORE_LIST_TYPE_1);
                break;
            case Constants.ACTIVITY_COLLECT://收藏
                adapter = new StoreAdapter(getContext(), Constants.STORE_LIST_TYPE_2);
                break;
            case 0:
                showToast("来源页获取错误");
                break;
        }
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO 门店详情
                Intent intent = new Intent();
                Bundle b = new Bundle();
                StoreBean store = adapter.getDatas().get(position);
                b.putString(Constants.STORE_ID, String.valueOf(store.getStoreId()));
                intent.putExtras(b);
                intent.setClass(getContext(), StoreManagerActivity.class);
                startActivityForResult(intent, CollectActivity.FROMCOLLECTACTIVITY);
            }
        });
        mBinding.recycleView.setAdapter(adapter);
        adapter.setDatas(data, true);
    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        startLocation();

        mSortSearchRequesetBody = new SortSearchRequesetBody();
        mSortSearchRequesetBody.setUserId(mUserId);//用户ID

        if (fromActivity != Constants.ACTIVITY_SEARCH) refreshLayout.autoRefresh();


    }

    /**
     * 开启定位
     */
    private void startLocation() {
        if (locationPresenter == null) {
            locationPresenter = new LocationPresenter(getContext());
        }
        locationPresenter.setMapLocationListener(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    if (grant) {
                        locationPresenter.startLocation(true);
                    } else {
                        ToastUtils.shortToast("你拒绝了定位当前位置权限.");
                    }
                });
    }

    private void getStoreList(double lat, double lng, int page, int size, String userId, SortSearchRequesetBody sortSearchRequesetBody) {
        switch (fromActivity) {
            case Constants.ACTIVITY_HOME_MORE://筛选门店
                //注册监听
                getMvpPresenter().sortSearch(sortSearchRequesetBody);
                break;
            case Constants.ACTIVITY_SEARCH://搜索
                if (TextUtils.isEmpty(search)) {
                    ToastUtils.shortToast("搜索内容不能为空");
                    RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
                    return;
                }
                getMvpPresenter().search(search, String.valueOf(lng), String.valueOf(lat), userId);
                break;
            case Constants.ACTIVITY_COLLECT://收藏
                getMvpPresenter().getStoreCollection(userId, String.valueOf(page), String.valueOf(size), String.valueOf(lat), String.valueOf(lng));
                break;
            case 0:
                showToast("来源页获取错误");
                break;
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        getStoreList(lat, lng, page, size, mUserId, mSortSearchRequesetBody);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        adapter.getDatas().clear();
        getStoreList(lat, lng, page, size, mUserId, mSortSearchRequesetBody);
    }

    @Override
    public void getStoreListSuccess(List<StoreBean> list) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        ArrayList<StoreBean> newData = (ArrayList<StoreBean>) list;

        if (refreshLayout == null || refreshLayout.getState() == RefreshState.Refreshing) {//刷新
            adapter.setDatas(newData, true);
        } else if (refreshLayout.getState() == RefreshState.Loading) {//加载
            adapter.addDatas(newData, true);
        }

//        是否允许上拉加载
        switch (fromActivity) {
            case Constants.ACTIVITY_HOME_MORE://首页更多
            case Constants.ACTIVITY_SEARCH://搜索
                refreshLayout.setNoMoreData(true);
                break;
            default:
                if (list.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
                    refreshLayout.setNoMoreData(true);
                } else {
                    refreshLayout.setNoMoreData(false);
                }
                break;
        }

    }

    @Override
    public void getStoreListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        refreshLayout.setNoMoreData(true);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mSortSearchRequesetBody.setLat(String.valueOf(lat));//纬度
        mSortSearchRequesetBody.setLng(String.valueOf(lng));//经度
        if (mLocationCallBack != null) {
            mLocationCallBack.locationSuc(aMapLocation.getProvince(), aMapLocation.getCity());
        }
    }

    @Override
    public void onUpData(int filterType, Object o) {
        page = 1;
        mSortSearchRequesetBody.setSortType("0");
        mSortSearchRequesetBody.setDistrictId(null);
        mSortSearchRequesetBody.setMaxDistance(null);
        mSortSearchRequesetBody.setCategoryIds(null);
        switch (filterType) {
            case Constants.ACTIVITY_FILTER_1:
                //入驻门店-附近
                Map<String, String> map = (Map<String, String>) o;
                DLog.d(map.toString());
                mSortSearchRequesetBody.setProvinceId(map.get("provinceId"));//省ID，默认0
                mSortSearchRequesetBody.setCityId(map.get("cityId"));//城市ID，默认0
                mSortSearchRequesetBody.setDistrictId(map.get("districtId"));//区ID，默认0 ,
                if (map.get("maxDistance") != null) {
                    float f = Float.valueOf(map.get("maxDistance")) * 1000;
                    mSortSearchRequesetBody.setMaxDistance(String.valueOf((int) f));
                }
                break;
            case Constants.ACTIVITY_FILTER_2:
                //入驻门店-综合排序
                mSortSearchRequesetBody.setSortType(String.valueOf(o));
                break;
            case Constants.ACTIVITY_FILTER_3:
                //入驻门店-筛选
                mSortSearchRequesetBody.setCategoryIds((ArrayList<String>) o);
                break;
            case Constants.ACTIVITY_FILTER_4:
                //搜索门店
                search = String.valueOf(o);
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
        refreshLayout.autoRefresh();
    }

    private IIocationCallBack mLocationCallBack;

    public void setLocationCallBack(IIocationCallBack locationCallBack) {
        mLocationCallBack = locationCallBack;
    }

    public interface IIocationCallBack {
        void locationSuc(String province, String city);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CollectActivity.FROMCOLLECTACTIVITY && resultCode == CollectActivity.COLLECTPAGE2) {
            refreshLayout.autoRefresh();
        }
    }
}
