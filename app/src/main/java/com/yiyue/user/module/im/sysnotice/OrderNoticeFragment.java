package com.yiyue.user.module.im.sysnotice;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.module.mine.order.details.OrderDetailsActivity;
import com.yl.core.component.log.DLog;
import com.yiyue.user.R;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.greendao.DaoCallBackInterface;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentSystemMsgBinding;
import com.yiyue.user.model.vo.bean.daobean.OrderMessageBean;
import com.yiyue.user.module.im.daoutil.OrderMessageDaoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lz on 2018/11/13
 * 订单交易系统消息fragment
 */
public class OrderNoticeFragment extends BaseMvpFragment<SysNoticeView, SysNoticePresenter> implements SysNoticeView {
    private FragmentSystemMsgBinding mBinding;
    private OrderMessageAdapter mAdapter;

    private OrderMessageDaoUtils orderMessageDaoUtils;

    private ArrayList<OrderMessageBean> showList = new ArrayList<>();

    public static OrderNoticeFragment getInstance() {
        return new OrderNoticeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system_msg;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mBinding = (FragmentSystemMsgBinding) viewDataBinding;
        mBinding.rvSysMsgs.setHasFixedSize(true);
        mBinding.rvSysMsgs.setNestedScrollingEnabled(false);
        mBinding.rvSysMsgs.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new OrderMessageAdapter(getContext());
        mBinding.rvSysMsgs.setAdapter(mAdapter);

        //点击订单消息跳转到订单详情
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mAdapter.getDatas() != null && mAdapter.getDatas().size() > 0){
                    OrderDetailsActivity.startActivity(getContext(),mAdapter.getDatas().get(position).getOrderId()+"");
                }
            }
        });

        orderMessageDaoUtils = new OrderMessageDaoUtils(getContext());
        orderMessageDaoUtils.setOnQueryAllInterface(new DaoCallBackInterface.OnQueryAllInterface<OrderMessageBean>() {
            @Override
            public void onQueryAllBatchInterface(List<OrderMessageBean> list) {
                if (null != list && list.size() > 0) {
                    showList.clear();
                    Collections.reverse(list);//倒序排放，使时间最后的排上面
                    showList.addAll(list);
                    mAdapter.setDatas(showList,true);
//                    showList.addAll(list);
                }
            }

            @Override
            public void onQueryAllBatchFailInterface() {
                DLog.e("OrderMessageDao", "--------------------onQueryAllBatchFailInterface");
            }
        });
        orderMessageDaoUtils.setOnDeleteInterface(new DaoCallBackInterface.OnDeleteInterface() {
            @Override
            public void onDeleteInterface(boolean type) {
                //删除成功
                if (type){
                    showList.clear();
                    mAdapter.setDatas(showList,true);
                }
            }
        });

    }

    /**
     * 收到新的推送 刷新显示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewOrderMessage event) {
        if (event != null) {
            loadData();
        }
    }

    @Override
    protected void loadData() {
        orderMessageDaoUtils.queryAll();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != orderMessageDaoUtils){
            orderMessageDaoUtils.closeConnection();
        }
    }

    //清空数据
    public void clearData(){
        if (null != orderMessageDaoUtils){
            orderMessageDaoUtils.deleteAll();
        }
    }
}
