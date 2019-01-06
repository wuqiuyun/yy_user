package com.yiyue.user.module.home.stylist;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentStylistBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.module.mine.stylist.IUpDataFragment;
import com.yiyue.user.module.mine.stylist.StylistMineAdapter;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StylistPresenter.class)
public class StylistFragment extends BaseMvpFragment<IStylistView, StylistPresenter>
        implements IStylistView, OnRefreshListener, OnLoadMoreListener, IUpDataFragment {
    private StylistMineAdapter mineAdapter;
    private String mUserId;
    private int page = 1;//页数
    private int size = 10;//每页数量
    private int categoryId;//类目ID
    private String categoryName = "";//类目名称

    private int fromActivity;//从哪个页面来的
    private FragmentStylistBinding mStylistBinding;
    private int filterType = 2;
    private Object mFilterTypeBean;//不同筛选类型的条件
    private InputMethodManager mImm;
    private String stylistId = "";//美发师ID
    private SmartRefreshLayout mRefreshLayout;
    private String mStoreId;

    public static Fragment newInstance(int from) {
        StylistFragment stylistFragment = new StylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        stylistFragment.setArguments(bundle);
        return stylistFragment;
    }

    public static Fragment newInstance(int from, String storeId) {
        StylistFragment stylistFragment = new StylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("storeId", storeId);
        stylistFragment.setArguments(bundle);
        return stylistFragment;
    }

    protected void initRefreshLoadLayout() {
        mRefreshLayout = mStylistBinding.refreshLayout;
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            mRefreshLayout.setOnLoadMoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stylist;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        fromActivity = bundle.getInt("from", 0);
        mStoreId = bundle.getString(Constants.STORE_ID);
        mStylistBinding = (FragmentStylistBinding) viewDataBinding;
        // init recycleview
        mStylistBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mStylistBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        initRefreshLoadLayout();
        initStylistMineAdapter();
        switch (fromActivity) {
            case Constants.ACTIVITY_SEARCH:// 搜索
                SearchStylistActivity searchStylistActivity = (SearchStylistActivity) getActivity();
                searchStylistActivity.setIUpDataFragment(this);
                mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                break;
            case Constants.ACTIVITY_HOME_MORE:// 首页更多
                StylistActivity stylistActivity = (StylistActivity) getActivity();
                stylistActivity.setIUpDataFragment(this);
                break;
            case Constants.ACTIVITY_CATEGORY_1:// 洗剪吹
                mStylistBinding.tvCategoryType.setVisibility(View.VISIBLE);
                categoryId = Constants.SERVER_CATEGORY_ID1;
                categoryName = Constants.SERVER_CATEGORY_NAME1;
                break;
            case Constants.ACTIVITY_CATEGORY_2:// 染烫拉
                mStylistBinding.tvCategoryType.setVisibility(View.VISIBLE);
                categoryId = Constants.SERVER_CATEGORY_ID2;
                categoryName = Constants.SERVER_CATEGORY_NAME2;
                break;
            case Constants.ACTIVITY_CATEGORY_3:// 接发
                mStylistBinding.tvCategoryType.setVisibility(View.VISIBLE);
                categoryId = Constants.SERVER_CATEGORY_ID3;
                categoryName = Constants.SERVER_CATEGORY_NAME3;
                break;
            case Constants.ACTIVITY_CATEGORY_4:// 护理
                mStylistBinding.tvCategoryType.setVisibility(View.VISIBLE);
                categoryId = Constants.SERVER_CATEGORY_ID4;
                categoryName = Constants.SERVER_CATEGORY_NAME4;
                break;
            case 0:
                showToast("来源页获取错误");
                break;
        }
        mStylistBinding.tvCategoryType.setText("以下美发师支持" + categoryName + "项目");
    }

    private void initStylistMineAdapter() {
        mineAdapter = new StylistMineAdapter(getContext());
        mineAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                stylistId = mineAdapter.getDatas().get(position).getStylistId() + "";
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STYLISTId, stylistId);
                StylistDetailsActivity.startActivity(getContext(), StylistDetailsActivity.class, bundle);
            }
        });
        mStylistBinding.recycleView.setAdapter(mineAdapter);
    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        if (fromActivity != Constants.ACTIVITY_SEARCH) mRefreshLayout.autoRefresh();

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        goRequestData();
    }

    @Override
    public void getStylistSuccess(List<StylistBean> stylistBeanList) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            mineAdapter.setDatas((ArrayList<StylistBean>) stylistBeanList, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            mineAdapter.addDatas((ArrayList<StylistBean>) stylistBeanList, true);
        }

        if (stylistBeanList.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }
        //隐藏键盘
        if (fromActivity == Constants.ACTIVITY_SEARCH)
            mImm.hideSoftInputFromWindow(mStylistBinding.refreshLayout.getWindowToken(), 0);
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }


    @Override
    public void getStylistFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void getArea(ArrayList<AreaBean> areaBeans) {

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        goRequestData();
    }

    /**
     * 请求数据
     */
    private void goRequestData() {
        switch (fromActivity) {
            //首页
            case Constants.ACTIVITY_HOME_MORE:
            case Constants.ACTIVITY_SEARCH:
                loadFilterTypeData(filterType, mFilterTypeBean, page);
                break;
            // 首页-类目
            case Constants.ACTIVITY_CATEGORY_1:
            case Constants.ACTIVITY_CATEGORY_2:
            case Constants.ACTIVITY_CATEGORY_3:
            case Constants.ACTIVITY_CATEGORY_4:
                getMvpPresenter().getStylistByCategoryId(categoryId, categoryName, mUserId, page);
                break;
            case Constants.ACTIVITY_STORE_STYLIST:
                getMvpPresenter().storeStylist(mStoreId, mUserId);
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
    }

    /**
     * @param filterType 筛选类型
     * @param o          筛选条件
     */
    @Override
    public void onUpData(int filterType, Object o) {
        this.filterType = filterType;
        mFilterTypeBean = o;
        page = 1;
        mRefreshLayout.autoRefresh();
    }

    /**
     * 获取筛选/搜索后的数据
     *
     * @param sortType
     * @param o
     * @param page
     */
    private void loadFilterTypeData(int sortType, Object o, int page) {
        switch (sortType) {
            case Constants.ACTIVITY_FILTER_1:
                //附近
                Map<String, String> screenings = (Map<String, String>) o;
                getMvpPresenter().inviteNear(screenings.get("cityId")
                        , screenings.get("districtId")
                        , screenings.get("distance")
                        , mUserId, page);
                break;
            case Constants.ACTIVITY_FILTER_2:
                //综合排序
                getMvpPresenter().inviteSort(o == null ? "0" : String.valueOf(o), mUserId, page);
                break;
            case Constants.ACTIVITY_FILTER_3:
                //筛选
                Map<String, String> map = (Map<String, String>) o;
                DLog.d(map.toString());
                getMvpPresenter().inviteScreen(map.get("coupon"), map.get("position"), mUserId, page);
                break;
            case Constants.ACTIVITY_FILTER_4:
                //搜索
                HashMap<String, String> map2 = (HashMap<String, String>) o;
                String content = map2.get("content");
                if (map2.get(Constants.SEARCH_TYPE).equals(Constants.SEARCH_FROM_HOME) ) {
                    getMvpPresenter().search(mUserId, content, page);
                } else if (map2.get(Constants.SEARCH_TYPE).equals(Constants.SEARCH_FROM_STYLIST)) {
                    getMvpPresenter().getStylistByStylistName(content, mUserId, page);
                } else {
                    getMvpPresenter().storeStylistSearch(content, mStoreId, mUserId);
                }
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
    }

}
