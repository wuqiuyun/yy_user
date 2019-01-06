package com.yiyue.user.module.home.service;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.databinding.FragmentServiceBinding;
import com.yiyue.user.model.constant.ServiceStatus;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;

import java.util.ArrayList;

/**
 * 服务-> 洗剪吹、烫染拉、接发、护理fragment
 * Created by wqy on 2018/11/1.
 */

public class ServiceFragment extends BaseMvpFragment<IServiceView, ServicePresenter>
        implements IServiceView {

    FragmentServiceBinding mBinding;
    private ServiceStylistAdapter adapter;
    private ArrayList<StylistBean> data = new ArrayList<>();

    public static Fragment newInstance(@ServiceStatus.ServiceType int serviceType) {
        return new ServiceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentServiceBinding) viewDataBinding;
        mBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        adapter = new ServiceStylistAdapter(getContext());
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                StylistDetailsActivity.startActivity(getActivity(), StylistDetailsActivity.class);
            }
        });
        mBinding.recycleView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        for (int i = 0; i <= 15; i++) {
            data.add(new StylistBean("测试"+i));
        }
        adapter.setDatas(data, true);
    }

    @Override
    public void showToast(String message) {

    }
}
