package com.yiyue.user.module.home.service.works;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.databinding.FragmentServiceWorksBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.HairListBean;
import com.yiyue.user.model.vo.bean.ServiceOpusBean;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.model.vo.bean.SortBean;
import com.yiyue.user.module.mine.works.details.WorksDetailsActivity;
import com.yiyue.user.module.mine.works.details.WorksDetailsPresenter;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yiyue.user.widget.filter.FilterAdapter;
import com.yiyue.user.widget.filter.FilterNearbyAdapter;
import com.yiyue.user.widget.filter.FilterView;
import com.yiyue.user.widget.filter.SynthesisAdapter;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务-> 作品
 * Created by wqy on 2018/11/1.
 */
@CreatePresenter(ServiceWorksPresenter.class)
public class ServiceWorksFragment extends BaseMvpFragment<IServiceWorksView, ServiceWorksPresenter>
        implements IServiceWorksView, OnRefreshListener, OnLoadMoreListener, AMapLocationListener {

    private FragmentServiceWorksBinding mBinding;
    private ServiceWorksAdapter worksAdapter;

    private LocationPresenter locationPresenter;

    private ArrayList<BaseRecycleViewAdapter> mAdapters;
    private SmartRefreshLayout mRefreshLayout;
    private int filterType = 2;
    private Object mFilterTypeBean;//不同筛选类型的条件
    private int page = 1;//页数
    private int size = 10;//每页数量
    private double lat;
    private double lng;

    // 筛选
    private FilterAdapter filterAdapter;
    private ArrayList<Object> mObjects = new ArrayList<>();
    private List<Integer> featureList = new ArrayList<>();
    private List<Integer> hairStyleList = new ArrayList<>();

    public static Fragment newInstance() {
        return new ServiceWorksFragment();
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_works;
    }

    @Override
    protected void initView() {
        initRecycleView();

        // 开启定位
        startLocation();

        mBinding.vBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.viewFilter.showView(mBinding.viewFilter.getTempPosition());
            }
        });

        initRefreshLoadLayout();
        initFilterView();
        worksAdapter.setOnItemClickListener(new ServiceWorksAdapter.OnItemClickListener() {
            @Override
            public void onCollectionListener(int position, ServiceOpusBean bean) {
                if (bean.getIsCollection() == 1) {
                    //取消收藏
                    getMvpPresenter().opusCollection(String.valueOf(bean.getOpusId()), 0);
                } else {
                    // 收藏
                    getMvpPresenter().opusCollection(String.valueOf(bean.getOpusId()), 1);
                }
            }

            @Override
            public void onItemClickListener(int position, ServiceOpusBean bean) {
                // 传值到作品详情页
                Bundle bundle = new Bundle();
                bundle.putString(Constants.OPUSID, bean.getOpusId() + "");
                bundle.putString(Constants.HEAD_PORTRAIT, bean.getHeadImg());
                bundle.putString(Constants.NICK_NAME, bean.getStylistName());
                bundle.putString(Constants.STYLIST_POSITION, bean.getPosition());
                bundle.putString(Constants.STYLISTId, bean.getStylistId() + "");
                WorksDetailsActivity.startActivity(getContext(), WorksDetailsActivity.class, bundle);
            }

        });
    }

    private void initRecycleView() {
        mBinding = (FragmentServiceWorksBinding) viewDataBinding;
        mBinding.viewFilter.setFilterVisibility(0);

        mBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        worksAdapter = new ServiceWorksAdapter(getContext());
        mBinding.recycleView.setAdapter(worksAdapter);
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
        filterAdapter = new FilterAdapter(getContext());
        filterAdapter.isMultipleSelection(true);
        //附近
        mAdapters.add(new FilterNearbyAdapter(getContext(), 1));
        //综合排序
        SynthesisAdapter synthesisAdapter = new SynthesisAdapter(getContext());
        ArrayList<SortBean> tempList = new ArrayList<>();
        tempList.add(new SortBean("最新作品", 1,false));
        tempList.add(new SortBean("人气最高", 2,false));
        tempList.add(new SortBean("综合排序", 3,true));
        tempList.add(new SortBean("距离最近", 4,false));
        synthesisAdapter.addDatas(tempList, true);
        synthesisAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                mBinding.viewFilter.showView(FilterView.SORT);

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
                //综合排序数据回调
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
        filterAdapter.setIOkButtonListener(new FilterAdapter.IOkButtonMultipleSelectionListener() {
            @Override
            public void onOkButtonClick(Map<Integer, List<ServiceSettingBean>> screenings) {
                mBinding.viewFilter.showView(FilterView.FILTER);
                hairStyleList.clear();
                List<ServiceSettingBean> hairList = screenings.get(1);
                //已选发长
                if (hairList != null) {
                    for (ServiceSettingBean bean : hairList) {
                        if (bean.isChecked() && bean.getId() != 0) {
                            hairStyleList.add(bean.getId());
                        }
                    }
                }

                featureList.clear();
                List<ServiceSettingBean> faceList = screenings.get(3);
                // 已选脸型
                if (faceList != null) {
                    for (ServiceSettingBean bean : faceList) {
                        if (bean.isChecked() && bean.getId() != 0) {
                            featureList.add(bean.getId());
                        }
                    }
                }
                filterType = Constants.ACTIVITY_FILTER_3;
                mRefreshLayout.autoRefresh();
            }
        });
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
        getMvpPresenter().getHairstyle();
        mRefreshLayout.autoRefresh();
    }

    /**
     * 获取筛选/搜索后的数据
     *
     * @param sortType
     * @param obj
     * @param page
     */
    private void loadFilterTypeData(int sortType, Object obj, int page) {
        switch (sortType) {
            case Constants.ACTIVITY_FILTER_1: //附近
                break;

            case Constants.ACTIVITY_FILTER_2: //综合排序
                getMvpPresenter().getAllWorks(lat, lng, page, size, obj == null ? "1" : String.valueOf(obj));
                break;

            case Constants.ACTIVITY_FILTER_3: //筛选
                getMvpPresenter().getOpusType(featureList, hairStyleList, page, size);
                break;

            default:
                showToast("来源页获取错误");
                break;
        }
    }

    @Override
    public void getListSuccess(ArrayList<ServiceOpusBean> opusBeans) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            worksAdapter.setDatas((ArrayList<ServiceOpusBean>) opusBeans, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            worksAdapter.addDatas((ArrayList<ServiceOpusBean>) opusBeans, true);
        }

        if (opusBeans.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getListFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getHairSuccess(ArrayList<HairListBean> hairListBeans) {
        mObjects.clear();
        mObjects.add(new HairListBean(0, "发长"));
        List<HairListBean> mSubData = new ArrayList<>();
        mSubData.add(new HairListBean(0, "不限"));
        mSubData.addAll(hairListBeans);
        mObjects.add(mSubData);
        getMvpPresenter().getFeature();
    }

    @Override
    public void getFeatureSuccess(ArrayList<HairListBean> hairListBeans) {
        mObjects.add(new HairListBean(0, "脸型"));
        List<HairListBean> mSubData2 = new ArrayList<>();
        mSubData2.add(new HairListBean(0, "不限"));
        mSubData2.addAll(hairListBeans);
        mObjects.add(mSubData2);
        filterAdapter.setDatas(mObjects, true);
    }

    @Override
    public void collectionSuccess() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void collectFail() {

    }

    /**
     * 开启定位
     */
    private void startLocation() {
        if (locationPresenter == null) {
            locationPresenter = new LocationPresenter(getContext());
            locationPresenter.setMapLocationListener(this);
            new RxPermissions(this)
                    .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(grant -> {
                        locationPresenter.startLocation(true);
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        loadFilterTypeData(filterType, mFilterTypeBean, page);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
    }
}
