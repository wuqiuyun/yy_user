package com.yiyue.user.module.home.stores;

import android.os.Bundle;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ActivityStoreBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.ServerTypeBean;
import com.yiyue.user.model.vo.bean.SortBean;
import com.yiyue.user.module.mine.store.StoreFragment;
import com.yiyue.user.module.mine.stylist.IUpDataFragment;
import com.yiyue.user.widget.filter.FilterAdapter2;
import com.yiyue.user.widget.filter.FilterNearbyAdapter;
import com.yiyue.user.widget.filter.FilterView;
import com.yiyue.user.widget.filter.SynthesisAdapter;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 更多门店
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StorePresenter.class)
public class StoreActivity extends BaseMvpAppCompatActivity<IStoreView, StorePresenter>
        implements IStoreView {
    private static final String BUNDLE_FRAGMENT = "StoreFragment";

    private StoreFragment fragment;
    private ActivityStoreBinding binding;
    private HashMap<String, String> areaMap = new HashMap<>();
    private ArrayList<BaseRecycleViewAdapter> mAdapters;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, BUNDLE_FRAGMENT, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store;
    }

      @Override
    protected void init() {
        initView();
        StatusBarUtil.setLightMode(this);
    }

    private void initView() {
        binding = (ActivityStoreBinding) viewDataBinding;
        binding.titleView.setLeftClickListener(v -> finish());
        //搜索
        binding.titleView.setRightImgClickListener(view -> {
            startActivity(StoreActivity.this, SearchStoresActivity.class);
        });
        binding.vBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFilter.showView(binding.viewFilter.getTempPosition());
            }
        });
        initFragment();
        initFilterView();
        loadData();
    }

    private void loadData() {
        String userId = AccountManager.getInstance().getUserId();
        getMvpPresenter().getAll();
        getMvpPresenter().getAreaByUserId(userId);
    }


    private void initFilterView() {
        mAdapters = new ArrayList<>();
        //附近
        mAdapters.add(new FilterNearbyAdapter(this,1));
        //综合排序
        SynthesisAdapter synthesisAdapter = new SynthesisAdapter(this);
        ArrayList<SortBean> tempList = new ArrayList<>();
        tempList.add(new SortBean("综合排序", 0,true));
        tempList.add(new SortBean("距离最近", 1,false));
        tempList.add(new SortBean("月接单最多", 2,false));
        tempList.add(new SortBean("评论量最多", 3,false));
        synthesisAdapter.addDatas(tempList, true);
        synthesisAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                binding.viewFilter.showView(FilterView.SORT);
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
                binding.viewFilter.setSortText(tempList.get(position).getTitle());

                if (mIUpDataFragment != null)
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_2, synthesisAdapter.getDatas().get(position).getId());
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        mAdapters.add(synthesisAdapter);
        //筛选
        FilterAdapter2 filterAdapter = new FilterAdapter2(this);
        filterAdapter.setIOkButtonListener(new FilterAdapter2.IOkButtonListener() {
            @Override
            public void onOkButtonClick(ArrayList<String> categoryIds) {
                binding.viewFilter.showView(FilterView.FILTER);
                if (mIUpDataFragment != null)
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_3, categoryIds);
            }
        });
        mAdapters.add(filterAdapter);
        binding.viewFilter.setAdapters(mAdapters);
        binding.viewFilter.setIFilterViewCallBack(new FilterView.IFilterViewCallBack() {
            @Override
            public void isHide(boolean b) {
                //背景变暗
                if (b) {
                    binding.vBg.setVisibility(View.VISIBLE);
                } else {
                    binding.vBg.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            fragment = (StoreFragment) getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (fragment == null) {
            fragment = (StoreFragment) StoreFragment.newInstance(Constants.ACTIVITY_HOME_MORE);
            fragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }


    @Override
    public void showToast(String message) {
    }

    private IUpDataFragment mIUpDataFragment;

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        mIUpDataFragment = IUpDataFragment;
    }

    @Override
    public void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans) {
        FilterAdapter2 filterAdapter2 = (FilterAdapter2) binding.viewFilter.getAdapters().get(2);
        filterAdapter2.setServerTypeBeans(serverTypeBeans);
    }

    @Override
    public void getArea(ArrayList<AreaBean> areaBeans) {
        if (areaBeans.size() != 0) {
            areaBeans.get(0).setName("全部区域");
            areaMap.put("cityId", String.valueOf(areaBeans.get(0).getId()));
            areaMap.put("provinceId", String.valueOf(areaBeans.get(0).getParentId()));
            areaBeans.add(0, new AreaBean(0, "附近"));
        }
        FilterNearbyAdapter filterNearbyAdapter = (FilterNearbyAdapter) mAdapters.get(0);
        filterNearbyAdapter.setINearbySelectListener(new FilterNearbyAdapter.INearbySelectListener() {
            @Override
            public void callBack(Map<String, String> screenings) {
                binding.viewFilter.showView(FilterView.NEARBY);
                if (mIUpDataFragment != null) {
                    areaMap.put("districtId", screenings.get("districtId"));
                    areaMap.put("maxDistance", screenings.get("distance"));
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_1, areaMap);
                }
            }
        });
        filterNearbyAdapter.setAreaList(areaBeans);
        areaMap.put("districtId", "0");
        if (mIUpDataFragment != null) {
            //筛选接口省市id必传
            mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_1, areaMap);
        }
    }


}
