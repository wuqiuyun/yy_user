package com.yiyue.user.module.home.service.leaderboard;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.yiyue.user.databinding.FragmentServiceLeaderBoardBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.SortBean;
import com.yiyue.user.model.vo.bean.daobean.RankBean;
import com.yiyue.user.model.vo.requestbody.RankRequestBody;
import com.yiyue.user.model.vo.requestbody.SaveLocationBody;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yiyue.user.widget.filter.FilterAdapter;
import com.yiyue.user.widget.filter.FilterNearbyAdapter;
import com.yiyue.user.widget.filter.FilterView;
import com.yiyue.user.widget.filter.SynthesisAdapter;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 服务 -> 排行榜
 * Created by wqy on 2018/11/2.
 */
@CreatePresenter(LeaderBoardPresenter.class)
public class LeaderBoardFragment extends BaseMvpFragment<ILeaderBoardView, LeaderBoardPresenter>
        implements ILeaderBoardView, OnRefreshListener, OnLoadMoreListener, AMapLocationListener {

    private FragmentServiceLeaderBoardBinding mBinding;
    private ServiceLeaderBoardAdapter adapter;
    private LocationPresenter locationPresenter;
    private ArrayList<BaseRecycleViewAdapter> mAdapters;
    private SmartRefreshLayout mRefreshLayout;

    private String mUserId;
    private int filterType = 1;
    private Object mFilterTypeBean;//不同筛选类型的条件
    private String cityGDId;
    private String distance;
    private String latitude;
    private String longitude;
    private int type = 1;
    private int sort = 0;
    private int page = 1;//页数
    private int size = 10;//每页数量
    private SaveLocationBody.Builder mSaveLocationBody;
    private RankRequestBody body;
    private ArrayList<AreaBean> areaList = new ArrayList<>();

    private String tempListType;//选择排序名称

    public static Fragment newInstance() {
        return new LeaderBoardFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_leader_board;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        mBinding = (FragmentServiceLeaderBoardBinding) viewDataBinding;
        mBinding.viewFilter.setFilterVisibility(3);
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.shap_divider_line_1dp)));
        mBinding.recycleView.addItemDecoration(divider);
        adapter = new ServiceLeaderBoardAdapter(getContext());
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                String stylistId = String.valueOf(adapter.getDatas().get(position).getStylistId());
                Bundle bundle = new Bundle();
                bundle.putString(STYLISTId, stylistId);
                StylistDetailsActivity.startActivity(getContext(), StylistDetailsActivity.class, bundle);
            }
        });
        mBinding.recycleView.setAdapter(adapter);

        mBinding.vBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewFilter.showView(mBinding.viewFilter.getTempPosition());
            }
        });
        initRefreshLoadLayout();
        initFilterView();
    }

    protected void initRefreshLoadLayout() {
        mRefreshLayout = mBinding.refreshLayout;
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            mRefreshLayout.setOnLoadMoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    private void initFilterView() {
        mAdapters = new ArrayList<>();
        // 附近
        mAdapters.add(new FilterNearbyAdapter(getContext(),0));
        // 综合排序
        ArrayList<SortBean> tempList = new ArrayList<>();
        tempList.add(new SortBean("综合排序", 0,true));
        tempList.add(new SortBean("距离最近", 1,false));
        tempList.add(new SortBean("月接单最多", 2,false));
        tempList.add(new SortBean("评论量最多", 3,false));
        tempList.add(new SortBean("价格最低", 4,false));
        tempList.add(new SortBean("价格最高", 5,false));
        SynthesisAdapter synthesisAdapter = new SynthesisAdapter(getContext());
        synthesisAdapter.addDatas(tempList, true);
        synthesisAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                mBinding.viewFilter.showView(FilterView.SORT);
                // 综合排序数据回调
                switch (position) {
                    case 0:
                        type = 7;
                        break;
                    case 1:
                        type = 2;
                        break;
                    case 2:
                        type = 3;
                        break;
                    case 3:
                        type = 6;
                        break;
                    case 4:
                        type = 4;
                        sort = 2;
                        break;
                    case 5:
                        type = 4;
                        sort = 1;
                        break;
                    default:
                        break;
                }
                ArrayList<SortBean> tempList1 = new ArrayList<>();
                boolean isChoose = false;
                for (int i = 0;i<tempList.size();i++){
                    if (i==position){
                        isChoose = true;
                    }else {
                        isChoose = false;
                    }
                    tempList1.add(new SortBean(tempList.get(i).getTitle(), tempList.get(i).getId(),isChoose));
                }
                synthesisAdapter.setDatas(tempList1, true);
                mBinding.viewFilter.setSortText(tempList.get(position).getTitle());
                filterType = Constants.ACTIVITY_FILTER_2;
                mFilterTypeBean = synthesisAdapter.getDatas().get(position).getId();
                mRefreshLayout.autoRefresh();
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        mAdapters.add(synthesisAdapter);
        //筛选
        FilterAdapter filterAdapter = new FilterAdapter(getContext());
        ArrayList<Object> mObjects = new ArrayList<>();
        filterAdapter.setDatas(mObjects, true);
        mAdapters.add(filterAdapter);
        mBinding.viewFilter.setAdapters(mAdapters);
        mBinding.viewFilter.setIFilterViewCallBack(new FilterView.IFilterViewCallBack() {
            @Override
            public void isHide(boolean b) {
                //背景变暗
                if (b) {
                    mBinding.vBg.setVisibility(View.VISIBLE);
                } else {
                    mBinding.vBg.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        mRefreshLayout.autoRefresh();
        getMvpPresenter().getAreaByUserId(mUserId);

        // 定位
        mSaveLocationBody = new SaveLocationBody.Builder();
        locationPresenter = new LocationPresenter(getContext());
        locationPresenter.setMapLocationListener(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    locationPresenter.startLocation(true);// 传true定位一次,不传2秒一次
                });
    }

    @Override
    public void getRankSuccess(ArrayList<RankBean> rankList) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            adapter.setDatas((ArrayList<RankBean>) rankList, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            adapter.addDatas((ArrayList<RankBean>) rankList, true);
        }

        if (rankList.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getRankFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getAreaSuccess(ArrayList<AreaBean> areaBeans) {
        areaList.clear();
        areaList.add(new AreaBean(0, "附近"));
        if (areaBeans != null && areaBeans.size() != 0) {
            areaBeans.get(0).setName("全部区域");
            areaList.addAll(areaBeans);
        }

        FilterNearbyAdapter filterNearbyAdapter = (FilterNearbyAdapter) mAdapters.get(0);
        filterNearbyAdapter.setINearbySelectListener(new FilterNearbyAdapter.INearbySelectListener() {
            @Override
            public void callBack(Map<String, String> screenings) {
                mBinding.viewFilter.showView(FilterView.NEARBY);
                String mCityId = screenings.get("cityId");// 选择附近返回0 选择全部区域返回1
                String mDistance = screenings.get("distance");// 选择具体距离几千米
                String mDistrictId = screenings.get("districtId");// 选择区域返回id

                if (!TextUtils.isEmpty(mDistance)) {
                    type = 8;
                    distance = mDistance;
                } else if (!TextUtils.isEmpty(mCityId)) {
                    type = 5;
                    sort = 3;
                    cityGDId = mCityId;
                } else {
                    type = 5;
                    sort = 4;
                    cityGDId = mDistrictId;
                }

                filterType = Constants.ACTIVITY_FILTER_1;
                mFilterTypeBean = screenings;
                mRefreshLayout.autoRefresh();
            }
        });
        filterNearbyAdapter.setAreaList(areaList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != locationPresenter) {
            locationPresenter.stopLocation();
            locationPresenter = null;
        }
    }

    /**
     * 获取筛选/搜索后的数据
     */
    private void loadFilterTypeData(int sortType, Object o, int page) {
        body = RankRequestBody.newBuilder()
                .cityGDId(cityGDId)
                .distance(distance)
                .latitude(latitude)
                .longitude(longitude)
                .page(page)
                .size(size)
                .sort(sort)
                .type(type)
                .build();
        switch (sortType) {
            case Constants.ACTIVITY_FILTER_1:
                //附近
                getMvpPresenter().getRankAll(body);
                break;
            case Constants.ACTIVITY_FILTER_2:
                //综合排序
                getMvpPresenter().getRankAll(body);
                break;
            case Constants.ACTIVITY_FILTER_3:
                //筛选
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        DLog.i("aMapLocation:" + aMapLocation);
        mSaveLocationBody.userId(mUserId)
                .latitude(String.valueOf(aMapLocation.getLatitude()))
                .longitude(String.valueOf(aMapLocation.getLongitude()))
                .location(aMapLocation.getAddress())
                .districtId(aMapLocation.getAdCode())
                .city(aMapLocation.getCity())
                .province(aMapLocation.getProvince());
        if (null != locationPresenter) {
            locationPresenter.stopLocation();
            locationPresenter = null;
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        loadFilterTypeData(filterType, mFilterTypeBean, page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        switch (type) {
            case 0:
            case 1:
                cityGDId = "";
                distance = "";
                latitude = "";
                longitude = "";
                sort = 0;
                break;
            case 2:
                cityGDId = "";
                distance = "";
                latitude = mSaveLocationBody.latitude;
                longitude = mSaveLocationBody.longitude;
                sort = 0;
                break;
            case 3:
                cityGDId = "";
                distance = "";
                latitude = "";
                longitude = "";
                sort = 0;
                break;
            case 4:
                cityGDId = "";
                distance = "";
                latitude = "";
                longitude = "";
                break;
            case 5:
                distance = "";
                latitude = "";
                longitude = "";
                break;
            case 6:
                cityGDId = "";
                distance = "";
                latitude = "";
                longitude = "";
                sort = 0;
                break;
            case 7:
                cityGDId = "";
                distance = "";
                latitude = "";
                longitude = "";
                sort = 0;
                break;
            case 8:
                cityGDId = "";
                latitude = mSaveLocationBody.latitude;
                longitude = mSaveLocationBody.longitude;
                sort = 0;
                break;
        }
        loadFilterTypeData(filterType, mFilterTypeBean, page);
    }

    @Override
    public void showToast(String message) {

    }
}
