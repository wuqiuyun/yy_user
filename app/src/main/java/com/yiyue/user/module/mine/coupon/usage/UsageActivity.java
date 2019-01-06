package com.yiyue.user.module.mine.coupon.usage;

/*
 * Create by lvlong on  2018/12/4
 *
 * 套餐劵使用记录
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityUsageBinding;
import com.yiyue.user.model.vo.bean.UsageBean;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

@CreatePresenter(UsagePresenter.class)
public class UsageActivity extends BaseMvpAppCompatActivity<UsageView, UsagePresenter>
        implements UsageView, OnLoadMoreListener, OnRefreshListener {

    ActivityUsageBinding mBinding;
    private UsageAdapter mAdapter;

    int page = 1;
    int size = 10;

    private RefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_usage;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityUsageBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());

        mBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mBinding.refreshLayout.setOnLoadMoreListener(this);
        mBinding.refreshLayout.setOnRefreshListener(this);

        mAdapter = new UsageAdapter(this);
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycleView.setAdapter(mAdapter);

        loadData();
    }

    private void loadData() {
        getMvpPresenter().getPackageUseRecorder(page, size);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void setData(List<UsageBean> usageBeans) {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
        if (page == 1) {
            mAdapter.setDatas((ArrayList<UsageBean>) usageBeans, true);
        }else {
            mAdapter.addDatas((ArrayList<UsageBean>) usageBeans, true);
        }
    }

    @Override
    public void onLoadFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        page ++;
        loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        page = 1;
        loadData();
    }
}
