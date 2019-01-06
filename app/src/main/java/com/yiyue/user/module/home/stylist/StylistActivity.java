package com.yiyue.user.module.home.stylist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStylistBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.SortBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.module.mine.stylist.IUpDataFragment;
import com.yiyue.user.widget.filter.FilterAdapter;
import com.yiyue.user.widget.filter.FilterNearbyAdapter;
import com.yiyue.user.widget.filter.FilterView;
import com.yiyue.user.widget.filter.SynthesisAdapter;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更多美发师
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StylistPresenter.class)
public class StylistActivity extends BaseMvpAppCompatActivity<IStylistView, StylistPresenter>
        implements IStylistView{
    private static final String BUNDLE_FRAGMENT = "stylistFragment";
    private Fragment fragment;
    private ActivityStylistBinding binding;
    private IUpDataFragment mIUpDataFragment;
    private ArrayList<BaseRecycleViewAdapter> mAdapters;
    private HashMap<String, String> areaMap = new HashMap<>();

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        mIUpDataFragment = IUpDataFragment;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, BUNDLE_FRAGMENT, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stylist;
    }

    @Override
    protected void init() {
        initView();
        initData();
        StatusBarUtil.setLightMode(this);
    }

    private void initData() {
        String userId = AccountManager.getInstance().getUserId();
        getMvpPresenter().getAreaByUserId(userId);
    }

    private void initView() {
        binding = (ActivityStylistBinding) viewDataBinding;
        binding.titleView.setLeftClickListener(v -> finish());
        //搜索
        binding.titleView.setRightImgClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_TYPE,Constants.SEARCH_FROM_STYLIST);
            startActivity(StylistActivity.this,SearchStylistActivity.class,bundle);
        });


        binding.vBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFilter.showView(binding.viewFilter.getTempPosition());
            }
        });
        initFragment();
        initFilterView();
    }

    private void initFilterView() {
        mAdapters = new ArrayList<>();
        //附近
        mAdapters.add(new FilterNearbyAdapter(this,1));
        //综合排序
        SynthesisAdapter synthesisAdapter = new SynthesisAdapter(this);
        ArrayList<SortBean> tempList = new ArrayList<>();
        tempList.add(new SortBean("综合排序",0,true));
        tempList.add(new SortBean("距离最近",1,false));
        tempList.add(new SortBean("月接单最多",2,false));
        tempList.add(new SortBean("评论量最多",3,false));
        tempList.add(new SortBean("价格最低",4,false));
        tempList.add(new SortBean("价格最高",5,false));
        synthesisAdapter.addDatas(tempList,true);
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
                //综合排序数据回调
                if (mIUpDataFragment!=null){
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_2,synthesisAdapter.getDatas().get(position).getId());
                }
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        mAdapters.add(synthesisAdapter);
        //筛选
        FilterAdapter filterAdapter = new FilterAdapter(this);
        ArrayList<Object>  mObjects = new ArrayList<>();
        ArrayList<String>  mSubData = new ArrayList<>();
        ArrayList<String>  mSubData2 = new ArrayList<>();
        mObjects.add("美发师等级");
        mSubData.add("高级");
        mSubData.add("资深");
        mSubData.add("首席");
        mSubData.add("总监");
        mSubData.add("督导");
        mObjects.add(mSubData);
        mObjects.add("优惠活动");
        mSubData2.add("优惠卷");
        mSubData2.add("套餐卷");
        mObjects.add(mSubData2);
        filterAdapter.setDatas(mObjects,true);
        filterAdapter.setIOkButtonListener(new FilterAdapter.IOkButtonListener() {
            @Override
            public void onOkButtonClick(Map<Integer, String> screenings) {
                binding.viewFilter.showView(FilterView.FILTER);
                HashMap<String, String> temp = new HashMap<>();
                if(screenings.get(1)!=null){
                    temp.put("position",screenings.get(1));
                }
                if(screenings.get(3)!=null){
                    String coupon =screenings.get(3);
                    if (coupon.equals("优惠卷")){
                        temp.put("coupon", "1");//活动类型 1 优惠卷 2 套餐卷
                    }else {
                        temp.put("coupon", "2");
                    }
                }
                if (mIUpDataFragment!=null){
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_3,temp);
                }
            }
        });
        mAdapters.add(filterAdapter);
        binding.viewFilter.setAdapters(mAdapters);
        binding.viewFilter.setIFilterViewCallBack(new FilterView.IFilterViewCallBack() {
            @Override
            public void isHide(boolean b) {
                //背景变暗
                if (b){
                    binding.vBg.setVisibility(View.VISIBLE);
                }else {
                    binding.vBg.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (fragment == null) {
            fragment = StylistFragment.newInstance(Constants.ACTIVITY_HOME_MORE);
            fragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }



    @Override
    public void getStylistSuccess(List<StylistBean> stylistBeanList) {

    }

    @Override
    public void getStylistFail() {

    }

    @Override
    public void getArea(ArrayList<AreaBean> areaBeans) {
        if (areaBeans.size()!=0){
            areaBeans.get(0).setName("全部区域");
        }
        areaBeans.add(0,new AreaBean(0,"附近"));
        FilterNearbyAdapter filterNearbyAdapter = (FilterNearbyAdapter) mAdapters.get(0);
        filterNearbyAdapter.setINearbySelectListener(new FilterNearbyAdapter.INearbySelectListener() {
            @Override
            public void callBack(Map<String, String> screenings) {
                binding.viewFilter.showView(FilterView.NEARBY);
                if (mIUpDataFragment!=null){
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_FILTER_1,screenings);
                }
            }
        });
        filterNearbyAdapter.setAreaList(areaBeans);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
