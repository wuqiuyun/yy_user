package com.yiyue.user.module.home.evaluation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.databinding.ActivityEvaluationManagerBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.EvaluationBean;
import com.yiyue.user.model.vo.bean.daobean.RankBean;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

import static com.yiyue.user.YLApplication.getContext;
import static com.yiyue.user.model.constant.Constants.STORE_ID;
import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 评价管理
 * <p>
 * Created by lvlong on 2018/10/11.
 */
@CreatePresenter(EvaluationManagerPresenter.class)
public class EvaluationManagerActivity extends BaseMvpAppCompatActivity<IEvaluationManagerView, EvaluationManagerPresenter>
        implements IEvaluationManagerView, ClickHandler, OnLoadMoreListener, OnRefreshListener {

    ActivityEvaluationManagerBinding mBinding;
    private SmartRefreshLayout mRefreshLayout;

    private String stylistId = "";
    private String storeId = "";
    private String from = ""; //  来自哪个页面
    private int page = 1;
    private int size = 10;
    private EvaluationManagerAdapter adapter;
    private String evaluationCount = "0"; //评价数量

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_evaluation_manager;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        hasExtras();
        mBinding = (ActivityEvaluationManagerBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.stylistId = bundle.getString(STYLISTId);
            this.storeId = bundle.getString(STORE_ID);
            this.evaluationCount = bundle.getString("count");
            this.from = bundle.getString("from");
        }

    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> finish());
        if (!TextUtils.isEmpty(evaluationCount)) {
            int count = Integer.parseInt(evaluationCount);
            mBinding.titleView.setTitleText(String.format(getString(R.string.evaluation_manager),
                    count > 99 ? "99+" : count + ""));
        }

        //evaluationCount
        RecyclerView recyclerView = mBinding.recycleView;
        adapter = new EvaluationManagerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        initRefreshLoadLayout();
    }

    private void loadData() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showToast(String message) {

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

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        if ("store".equals(from)) {
            // 门店评价
            getMvpPresenter().getStoreCommentList(storeId, page, size,this);
        } else {
            // 美发师评价
            getMvpPresenter().getStylistCommentList(stylistId, page, size,this);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        if ("store".equals(from)) {
            // 门店评价
            getMvpPresenter().getStoreCommentList(storeId, page, size,this);
        } else {
            // 美发师评价
            getMvpPresenter().getStylistCommentList(stylistId, page, size,this);
        }
    }

    @Override
    public void getEvaluationSuccess(ArrayList<EvaluationBean> evaluationBeans) {
        if (mRefreshLayout == null || mRefreshLayout.getState() == RefreshState.Refreshing) {//刷新
            adapter.setDatas(evaluationBeans, true);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {//加载
            adapter.addDatas(evaluationBeans, true);
        }

        if (evaluationBeans.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);

    }

    @Override
    public void getEvaluationFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }
}
