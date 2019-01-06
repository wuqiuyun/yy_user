package com.yiyue.user.module.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.amap.LocationPresenter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentHomeBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.ServiceStatus;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.BannerBean;
import com.yiyue.user.model.vo.bean.StoreRecommendBean;
import com.yiyue.user.model.vo.bean.StylistRecommendBean;
import com.yiyue.user.model.vo.requestbody.SaveLocationBody;
import com.yiyue.user.module.common.WebActivity;
import com.yiyue.user.module.home.qrcode.ScanCodeActivity;
import com.yiyue.user.module.home.service.ServiceActivity;
import com.yiyue.user.module.home.store.StoreManagerActivity;
import com.yiyue.user.module.home.stores.StoreActivity;
import com.yiyue.user.module.home.stylist.HomeStylistPageAdapter;
import com.yiyue.user.module.home.stylist.SearchStylistActivity;
import com.yiyue.user.module.home.stylist.StylistActivity;
import com.yiyue.user.module.im.chat.ChatActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.util.DeviceUtils;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.ImageLoaderUtil;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yiyue.user.widget.viewpage.transforms.ZoomOutPageTransformer;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yiyue.user.model.constant.Constants.STORE_ID;
import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 首页
 * <p>
 * Created by zm on 2018/9/19.
 */
@CreatePresenter(HomePresenter.class)
public class HomeFragment extends BaseMvpFragment<IHomeView, HomePresenter>
        implements IHomeView, ClickHandler, OnRefreshListener, AMapLocationListener {

    private FragmentHomeBinding mBinding;
    private HomeStylistPageAdapter mHomeStylistAdapter;
    private HomeStoreAdapter mHomeStoreAdapter;
    private LocationPresenter locationPresenter;
    private SaveLocationBody.Builder mSaveLocationBody;
    private RefreshLayout reshLayout;
    private ArrayList<StylistRecommendBean> mData = new ArrayList<>();
    private int mPosition = 0;
    private List<HotCity> hotCities;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentHomeBinding) viewDataBinding;
        mBinding.titleView.setLeftIcon(getResources().getDrawable(R.drawable.icon_location));
        mBinding.titleView.setLeftText("正在获取定位");
        mBinding.titleView.setLeftClickListener(v -> {
            if (AccountManager.getInstance().isLogin()) {
                selectCity();
            } else {
                ToastUtils.shortToast("请先登录");
            }

        });
        mBinding.setClick(this);
        mBinding.titleView.setSearch();
        mBinding.titleView.setCenterClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_TYPE, Constants.SEARCH_FROM_HOME);
            SearchStylistActivity.startActivity(getContext(), SearchStylistActivity.class, bundle);
        });
        mBinding.titleView.setRightImgClickListener(view -> {
            if (!AccountManager.getInstance().isLogin()) {
                ToastUtils.shortToast("请先登录");
                return;
            }
            new RxPermissions(this)
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    .subscribe(granted -> {
                        if (granted) {
                            ScanCodeActivity.startActivity(getContext(), ScanCodeActivity.class);
                        } else {
                            ToastUtils.shortToast(getString(R.string.permissions_camera_state));
                        }
                    });
        });
        initRefreshLoadLayout();
        //门店推荐
        setStoreListAdapter();
        //美发师推荐
        initGalleyView(new ArrayList<>());
    }

    protected void initRefreshLoadLayout() {
        mBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mBinding.refreshLayout.setEnableLoadMore(false);
        mBinding.refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        //推荐门店/美发师
        loadRecommend();
        mSaveLocationBody = new SaveLocationBody.Builder();
        // 定位
        startLocation();
        getMvpPresenter().getBanner();
    }

    private void loadRecommend() {
        getMvpPresenter().getStoreRecommend();
        getMvpPresenter().getStylistRecommend();
    }

    /**
     * 开启定位
     */
    @SuppressLint("CheckResult")
    private void startLocation() {
        if (locationPresenter != null) {
            locationPresenter.stopLocation();
        } else {
            locationPresenter = new LocationPresenter(getContext());
            locationPresenter.setMapLocationListener(this);
        }

        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    if (grant) {
                        locationPresenter.startLocation();
                    } else {
                        mBinding.titleView.setLeftText("未能获取定位");
                    }
                });
    }

    private void selectCity() {
        if (hotCities == null) {
            hotCities = new ArrayList<>();
            hotCities.add(new HotCity("北京", "110000"));
            hotCities.add(new HotCity("上海", "310000"));
            hotCities.add(new HotCity("广州", "440100"));
            hotCities.add(new HotCity("深圳", "440300"));
            hotCities.add(new HotCity("杭州", "330100"));
        }
        CityPicker.from(getActivity())
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerTheme)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        mBinding.titleView.setLeftText(data.getName());
                        getMvpPresenter().changesave(data.getCode(), data.getName());
                    }

                    @Override
                    public void onLocate() {
                        startLocation();
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    private void setStoreListAdapter() {
        mHomeStoreAdapter = new HomeStoreAdapter(getContext());
        mHomeStoreAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                StoreRecommendBean data = mHomeStoreAdapter.getDatas().get(position);
                Bundle bundle = new Bundle();
                bundle.putString(STORE_ID, String.valueOf(data.getStoreId()));
                StoreManagerActivity.startActivity(getContext(), StoreManagerActivity.class, bundle);
            }
        });

        mBinding.storeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.shap_divider_line_1dp)));
        mBinding.storeList.addItemDecoration(divider);
        mBinding.storeList.setAdapter(mHomeStoreAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wash_blow:// 洗剪吹
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_WASH_BLOW);
                break;
            case R.id.btn_hot_dyeing:// 烫染拉
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_HOT_DYEING);
                break;
            case R.id.btn_hair_extension:// 接发
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_HAIR_EXTENSION);
                break;
            case R.id.btn_nursing:// 护理
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_NURSING);
                break;
            case R.id.btn_leader_board:// 排行榜
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_LEADERBOARD);
                break;
            case R.id.btn_works:// 作品
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_WORKS);
                break;
            case R.id.btn_package_coupon:// 套餐券
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_PACKAGE_COUPON);
                break;
            case R.id.btn_package:// 套餐
                ServiceActivity.startActivity(getContext(), ServiceStatus.SERVICE_PACKAGE);
                break;
            case R.id.tv_look_more_stylist:// 更多推荐-推荐美发师
                StylistActivity.startActivity(getContext(), StylistActivity.class);
                break;
            case R.id.tv_look_more_store: // 更多推荐-推荐理发店
                StoreActivity.startActivity(getContext(), StoreActivity.class);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onGetStoreListSuccess(ArrayList<StoreRecommendBean> list) {
        mHomeStoreAdapter.setDatas(list, true);
        RefreshLayoutUtil.finishRefreshLayout(mBinding.refreshLayout);
    }

    @Override
    public void onGetStylistListSuccess(ArrayList<StylistRecommendBean> list) {
        mData = list;
        initGalleyView(mData);
        RefreshLayoutUtil.finishRefreshLayout(reshLayout);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initGalleyView(ArrayList<StylistRecommendBean> list) {
        // 设置ViewPager的布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                DeviceUtils.getWindowWidth(getActivity()) * 7 / 10,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBinding.vpStylist.setLayoutParams(params);
        // 构造适配器并传入数据源
        mHomeStylistAdapter = new HomeStylistPageAdapter(getContext(), list);
        // 设置适配器
        mBinding.vpStylist.setAdapter(mHomeStylistAdapter);
        // 设置预加载数量
        if (list.size() > 3) {
            mBinding.vpStylist.setOffscreenPageLimit(3);
        } else {
            mBinding.vpStylist.setOffscreenPageLimit(list.size());
        }
        // 设置每页之间的左右间隔
        mBinding.vpStylist.setPageMargin(0);
        // 设置展示第几页
        if (mPosition != 0 && list.size() > mPosition) {
            mBinding.vpStylist.setCurrentItem(mPosition, true);
        } else {
            mBinding.vpStylist.setCurrentItem(1, true);
        }

        // 设置切换动画
        mBinding.vpStylist.setPageTransformer(true, new ZoomOutPageTransformer());
        // 设置ViewPager左右两边滑动无效的处理
        mBinding.llStylist.setOnTouchListener((v, event) -> {
            // 传递给ViewPager进行滑动处理
            return mBinding.vpStylist.dispatchTouchEvent(event);
        });
        // 解决嵌套滑动卡顿问题
        mBinding.storeList.setNestedScrollingEnabled(false);

        // 优秀美发师推荐
        mHomeStylistAdapter.setOnItemClickListener(new HomeStylistPageAdapter.OnItemClickListener() {

            @Override
            public void IsAttentionListener(int position, StylistRecommendBean bean) {
                mPosition = position;
                if (bean.isCollection()) {
                    // 取消关注
                    getMvpPresenter().stylistCollection(String.valueOf(bean.getStylistId()), 0);
                } else {
                    // 关注
                    getMvpPresenter().stylistCollection(String.valueOf(bean.getStylistId()), 1);
                }
            }

            @Override
            public void StylistChatListener(int position, StylistRecommendBean bean) {
                ChatActivity.startFromZIxunActivity(getActivity(), bean.getImusername(),
                        String.valueOf(bean.getUserId()), bean.getNickname(), bean.getHeadportrait());
            }

            @Override
            public void StylistServiceListener(int position, StylistRecommendBean stylistRecommendBean) {
                Bundle bundle = new Bundle();
                bundle.putString(STYLISTId, String.valueOf(stylistRecommendBean.getStylistId()));
                StylistDetailsActivity.startActivity(getActivity(), StylistDetailsActivity.class, bundle);
            }

            @Override
            public void ItemClickListener(int position, StylistRecommendBean stylistRecommendBean) {

            }
        });
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        CityPicker.from(getActivity()).locateComplete(new LocatedCity(aMapLocation.getDistrict(), aMapLocation.getAdCode()), LocateState.SUCCESS);
        mSaveLocationBody.userId(AccountManager.getInstance().getUserId())
                .latitude(String.valueOf(aMapLocation.getLatitude()))
                .longitude(String.valueOf(aMapLocation.getLongitude()))
                .location(aMapLocation.getAddress())
                .districtId(aMapLocation.getAdCode())
                .city(aMapLocation.getCity())
                .province(aMapLocation.getProvince());
        if (aMapLocation.getAdCode() != null && !TextUtils.isEmpty(aMapLocation.getAdCode())) {
            getMvpPresenter().saveLocation(mSaveLocationBody.build());
        }
        mBinding.titleView.setLeftText(aMapLocation.getDistrict());
    }

    /**
     * 获取所有区域与定位信息匹配,再将用户定位信息保存后,可调用/user-api/area/getAreaByUserId
     * 接口直接获取用户所在城市区域列表
     *
     * @param areaBeans
     */
    @Override
    public void onGetAreaSuccess(ArrayList<AreaBean> areaBeans) {
        for (AreaBean areaBean : areaBeans) {
            if (mSaveLocationBody.build().getProvince().equals(areaBean.getName())) {
                mSaveLocationBody.provinceId(String.valueOf(areaBean.getAreaId()));
                for (AreaBean city : areaBean.getAreaList()) {//市集合
                    if (mSaveLocationBody.build().getCity().equals(city.getName())) {
                        mSaveLocationBody.cityId(String.valueOf(city.getAreaId()));
                        break;
                    }
                }
                break;
            }
        }
        getMvpPresenter().saveLocation(mSaveLocationBody.build());
    }

    @Override
    public void onGetListFail() {
        RefreshLayoutUtil.finishRefreshLayout(reshLayout);
    }

    @Override
    public void onGetLocationSuccess() {
        //保存地理位置成功
        if (mHomeStylistAdapter.getCount() <= 0) {
            getMvpPresenter().getStylistRecommend();
        }
        if (mHomeStoreAdapter.getDatas().size() <= 0) {
            getMvpPresenter().getStoreRecommend();
        }
    }

    @Override
    public void collectionSuccess() {
        getMvpPresenter().getStylistRecommend();
    }

    @Override
    public void collectFail() {
    }

    @Override
    public void onGetBannerSuccess(ArrayList<BannerBean> bannerBean) {
        startBanner(bannerBean);
    }

    @Override
    public void onGetBannerFail() {

    }

    /**
     * 修改城市成功
     */
    @Override
    public void onChangesaveSuccess() {
        loadRecommend();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        this.reshLayout = refreshLayout;
        getMvpPresenter().getStoreRecommend();
        getMvpPresenter().getStylistRecommend();
        getMvpPresenter().getBanner();
    }

    // bannner
    private void startBanner(ArrayList<BannerBean> list) {
        List<String> banderImages = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (BannerBean banner : list) {
                banderImages.add(banner.getImage());
            }
        }

        //设置banner样式(显示圆形指示器)
        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居中）
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        mBinding.banner.setImageLoader(new ImageLoaderUtil());
        //设置图片集合
        mBinding.banner.setImages(banderImages);
        //设置banner动画效果
        //  mBinding.banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        // mBinding.banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        // mBinding.banner.isAutoPlay(false);
        //设置轮播时间
        mBinding.banner.setDelayTime(5000);

        // 点击监听
        mBinding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bean = list.get(position);
                String title = bean.getTitle();
                String url = bean.getUrl();
                if (!TextUtils.isEmpty(url.trim())) {
                    WebActivity.startActivity(getActivity(), url, title);
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBinding.banner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().isLogin()) {
            getMvpPresenter().getStylistRecommend();
        }
    }
}