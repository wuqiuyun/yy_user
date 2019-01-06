package com.yiyue.user.module.mine.stylist;

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
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentStylistBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(MineStylistPresenter.class)
public class MineStylistFragment extends BaseMvpFragment<IMineStylistView, MineStylistPresenter>
        implements IMineStylistView,OnRefreshListener,OnLoadMoreListener{
    private StylistMineAdapter mineAdapter;
    private String mUserId;
    private int page = 1;//页数
    private int size = 10;//每页数量
    private FragmentStylistBinding mStylistBinding;
    private SmartRefreshLayout mRefreshLayout;
    public static Fragment newInstance() {
        return new MineStylistFragment();
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
        mStylistBinding = (FragmentStylistBinding) viewDataBinding;
        // init recycleview
        mStylistBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mStylistBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        initRefreshLoadLayout();
        initStylistMineAdapter();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initStylistMineAdapter() {
        mineAdapter = new StylistMineAdapter(getContext());
        mineAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                //把美发师ID传入下个页面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STYLISTId,String.valueOf(mineAdapter.getDatas().get(position).getStylistId()));
                intent.putExtras(bundle);
                intent.setClass(getContext(),StylistDetailsActivity.class);
                startActivityForResult(intent, CollectActivity.FROMCOLLECTACTIVITY);
            }
        });
        mStylistBinding.recycleView.setAdapter(mineAdapter);
    }
    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void showToast(String message)
    {
        ToastUtils.shortToast(message);
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page=1;
        getMvpPresenter().getStylist(mUserId,page,size);
    }

    @Override
    public void getStylistSuccess(List<StylistBean> stylistBeanList) {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
        if (mRefreshLayout==null||mRefreshLayout.getState()== RefreshState.Refreshing){//刷新
            mineAdapter.setDatas((ArrayList<StylistBean>) stylistBeanList,true);
        }else if(mRefreshLayout.getState()== RefreshState.Loading){//加载
            mineAdapter.addDatas((ArrayList<StylistBean>) stylistBeanList,true);
        }

        if (stylistBeanList.size() < size ) {// 加载的数据不够页面数量 则认为没有下一页
            mRefreshLayout.setNoMoreData(true);
        } else {
            mRefreshLayout.setNoMoreData(false);
        }

    }



    @Override
    public void getStylistFail() {
        RefreshLayoutUtil.finishRefreshLayout(mRefreshLayout);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        getMvpPresenter().getStylist(mUserId,page,size);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CollectActivity.FROMCOLLECTACTIVITY&&resultCode==CollectActivity.COLLECTPAGE1){
            mRefreshLayout.autoRefresh();
        }
    }

}
