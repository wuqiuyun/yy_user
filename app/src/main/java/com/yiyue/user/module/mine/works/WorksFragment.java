package com.yiyue.user.module.mine.works;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.databinding.FragmentWorksBinding;
import com.yiyue.user.model.vo.bean.WorksBean;
import com.yiyue.user.module.mine.works.details.WorksDetailsActivity;

import java.util.ArrayList;

/**
 * 作品(我的收藏)
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(WorksPresenter.class)
public class WorksFragment extends BaseMvpFragment<IWorksView, WorksPresenter> implements IWorksView, OnRefreshListener, OnLoadMoreListener {
    FragmentWorksBinding binding;
    private WorksAdapter adapter;
    private ArrayList<WorksBean> data = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private String userId;
    private int page;
    private int size = 10;//每页数量

    public static Fragment newInstance() {
        return new WorksFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_works;
    }

    @Override
    protected void initView() {
        binding = (FragmentWorksBinding) viewDataBinding;
        // init recycleview
        binding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        adapter = new WorksAdapter(getContext());
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                WorksBean bean = adapter.getDatas().get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                bundle.putString(Constants.OPUSID, String.valueOf(bean.getOpusId()));
                bundle.putString(Constants.HEAD_PORTRAIT, bean.getStylistHeadImg());
                bundle.putString(Constants.NICK_NAME, bean.getStylistNickname());
                bundle.putString(Constants.STYLIST_POSITION, bean.getProfessor());
                bundle.putString(Constants.STYLISTId, bean.getStylistId() + "");
                intent.putExtras(bundle);
                intent.setClass(getContext(), WorksDetailsActivity.class);
                startActivityForResult(intent, CollectActivity.FROMCOLLECTACTIVITY);
            }
        });
        binding.recycleView.setAdapter(adapter);
        initRefreshLoadLayout();
    }

    protected void initRefreshLoadLayout() {
        mRefreshLayout = binding.refreshLayout;
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            mRefreshLayout.setOnLoadMoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void loadData() {
        userId = AccountManager.getInstance().getUserId();

        mRefreshLayout.autoRefresh();

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        getMvpPresenter().getOpus(userId, String.valueOf(page), String.valueOf(size));
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        getMvpPresenter().getOpus(userId, String.valueOf(page), String.valueOf(size));
    }

    @Override
    public void onSuccess(ArrayList<WorksBean> worksBeans) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            adapter.setDatas(worksBeans, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            adapter.addDatas(worksBeans, true);
        }

        if (worksBeans.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }

        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CollectActivity.FROMCOLLECTACTIVITY && resultCode == CollectActivity.COLLECTPAGE3) {
            mRefreshLayout.autoRefresh();
        }
    }
}
