package com.yiyue.user.module.mine.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.fragment.BasePageMvpFragment;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentOrderBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.constant.OrderStatus;
import com.yiyue.user.model.vo.bean.CreateOrderBean;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yiyue.user.model.vo.bean.OrderAddMoneyBean;
import com.yiyue.user.model.vo.bean.OrderBean;
import com.yiyue.user.module.mine.order.certificate.OrderCertificateActivity;
import com.yiyue.user.module.mine.order.comment.CommentActivity;
import com.yiyue.user.module.mine.order.details.OrderDetailsActivity;
import com.yiyue.user.module.mine.order.time.UpdateTimeActivity;
import com.yiyue.user.module.mine.wallet.buy.PayActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yiyue.user.widget.dialog.YLDialog;
import com.yl.core.component.mvp.factory.CreatePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Objects;

import static com.yiyue.user.model.constant.Constants.EXTRA_ORDER_TYPE;

/**
 * 我的订单
 * <p>
 * Created by zm on 2018/9/20.
 */
@CreatePresenter(OrderPresenter.class)
public class OrderFragment extends BasePageMvpFragment<IOrderView, OrderPresenter, OrderBean> implements IOrderView {
    @OrderStatus.OrderType
    private int orderType;
    private OrderAdapter orderAdapter;

    private RefreshLayout refreshLayout;
    private FragmentOrderBinding orderBinding;

    private long SEND_REMIND_SPACE_TIME = 60 * 1000L; // 订单提交间隔时间
    private long SEND_REMIND_TIME = 0L; // 记录订单提交时间

    /**
     *
     * @param orderType 订单类型
     * @return
     */
    public static Fragment newInstance(@OrderStatus.OrderType int orderType) {
        Fragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ORDER_TYPE, orderType);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        hasExtras();
        // 初始化加载刷新控件
        initRefreshLoadLayout();
        initRecycleView();
    }

    private void hasExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            orderType = bundle.getInt(EXTRA_ORDER_TYPE);
        }
    }

    private void initRecycleView() {
        orderBinding = (FragmentOrderBinding) viewDataBinding;
        orderBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.shap_divider_line)));
        orderBinding.recycleView.addItemDecoration(divider);
        orderAdapter = new OrderAdapter(getContext(), orderType);
        orderAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                OrderDetailsActivity.startActivity(getContext(), orderAdapter.getDatas().get(position).getId());
            }
        });
        orderAdapter.setOrderOperaListener(new OrderAdapter.OnOrderOperaListener(){

            @Override
            public void onCompleteClick(View view, OrderBean orderBean) {
                new YLDialog.Builder(getContext())
                        .setTitle("完成订单提示")
                        .setMessage("该订单已服务" + calServiceTime(orderBean.getStarttime(), System.currentTimeMillis()) + "，请确认是否已完成该服务？")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            getMvpPresenter().completeOrder(getContext(), orderBean.getId());
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }

            @Override
            public void onUpdateDateClick(View view, OrderBean orderBean) {
                UpdateTimeActivity.startActivity(getContext(), orderBean.getStylistId(), orderBean.getStoreId(), orderBean.getId());
            }

            @Override
            public void onRefuseAddPriceClick(View view, OrderBean orderBean) {
                new YLDialog.Builder(getContext())
                        .setTitle("拒绝加价提示")
                        .setMessage("请确认是否拒绝本次加价")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            getMvpPresenter().addMoneyRefuse(getContext(), orderBean.getId());
                        })
                        .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }

            @Override
            public void onAgreeAddPriceClick(View view, OrderBean orderBean) {
                new YLDialog.Builder(getContext())
                        .setTitle("同意加价提示")
                        .setMessage("请确认是否同意本次加价")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            OrderAddMoneyBean addMoneyBean = orderBean.getOrderAddMoney();
                            if (addMoneyBean == null) return;

                            Bundle bundle = new Bundle();
                            bundle.putInt(Constants.ISORDER, 2);
                            bundle.putString("orderName", addMoneyBean.getAdddesc());
                            bundle.putString("payAmount", String.valueOf(addMoneyBean.getAddmoney()));
                            bundle.putString("orderNo", orderBean.getOrderno());
                            bundle.putString("orderId",orderBean.getId());
                            PayActivity.startActivity(getContext(), PayActivity.class, bundle);
                        })
                        .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }

            @Override
            public void onOrderCertificateClick(View view, OrderBean orderBean) {
                OrderCertificateActivity.startActivity(getContext(), orderBean);
            }

            @Override
            public void onCancelOrderClick(View view, OrderBean orderBean) {
                String tips;
                if (orderBean.getStatus()==3 || orderBean.getStatus()==1){
                    tips = "是否确认取消该订单？";
                }else {
                    tips = "预约时间开始前2小时以外，可无责任取消订单，是否确定取消该订单？";
                }
                new YLDialog.Builder(getContext())
                        .setTitle("取消订单提示")
                        .setMessage(tips)
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            getMvpPresenter().cancelOrder(getContext(), orderBean.getId());
                        })
                        .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }

            @Override
            public void onCancelApplyClick(View view, OrderBean orderBean) {
                new YLDialog.Builder(getContext())
                        .setTitle("取消申请提示")
                        .setMessage("请确认是否取消本次申请")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                            getMvpPresenter().cancelRequestOrder(getContext(), orderBean.getId());
                        })
                        .setNegativeButton("我再想想", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }

            @Override
            public void onSendRemindClick(View view, OrderBean orderBean) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - SEND_REMIND_TIME < SEND_REMIND_SPACE_TIME) {
                    ToastUtils.shortToast("已通知美发师，请耐心等待！");
                    return;
                }
                SEND_REMIND_TIME = currentTime; // 更新提醒发送时间
                getMvpPresenter().remindStylist(getContext(),
                        orderBean.getId(), orderBean.getStatus() == 3 ? "1" : "2");
            }

            @Override
            public void onCommentClick(View view, OrderBean orderBean) {
                CommentActivity.startActivity(getContext(), CommentActivity.COMMENT_EDIT, orderBean);
            }

            @Override
            public void onLookCommentClick(View view, OrderBean orderBean) {
                CommentActivity.startActivity(getContext(), CommentActivity.COMMENT_LOOK, orderBean);
            }

            @Override
            public void goPayment(View view, OrderBean orderBean) {
                CreateOrderBean createOrderBean = new CreateOrderBean();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ISORDER, 1);
                createOrderBean.setOrdername(orderBean.getOrdername());
                createOrderBean.setPayamount(orderBean.getPayamount());
                createOrderBean.setOrderno(orderBean.getOrderno());
                createOrderBean.setId(Integer.valueOf(orderBean.getId()));
                createOrderBean.setServicemodel(orderBean.getServicemodel());
                createOrderBean.setPackageId(orderBean.getPackageId());
                createOrderBean.setTimes(30 * 60 - (int) ((System.currentTimeMillis() - orderBean.getCreatetime()) / 1000));
                bundle.putParcelable(Constants.STYLIST_ORDER_RESULT, createOrderBean);
                PayActivity.startActivity(getContext(), PayActivity.class, bundle);
            }
        });
        orderBinding.recycleView.setAdapter(orderAdapter);
    }

    /**
     * 计算服务时间
     */
    private String calServiceTime(long startTime, long endTime) {
        long spacingTime = (endTime - startTime) / 1000;
        if (spacingTime <= 0) {
            return "00小时00分";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(spacingTime/60);
        sb.append("小时");
        sb.append(spacingTime%60);
        sb.append("分");
        return sb.toString();
    }

    @Override
    protected void loadData() {
//        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageIndx ++;
        this.refreshLayout = refreshLayout;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageIndx = 1;
        this.refreshLayout = refreshLayout;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onGetOrderListSuccess(ArrayList<OrderBean> orderBeans) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        if (refreshLayout == null || refreshLayout.getState() == RefreshState.Refreshing) {
            orderAdapter.clearData(false);
            orderAdapter.setDatas(orderBeans, true);
        }else if (refreshLayout.getState() ==  RefreshState.Loading) {
            orderAdapter.addDatas(orderBeans, true);
        }

        if (orderBeans.size() == 0 && pageIndx == 1){
            orderBinding.ivNoDate.setVisibility(View.VISIBLE);
        }else {
            orderBinding.ivNoDate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetOrderListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
    }

    @Override
    public void onCompleteOrderSuccess() {
        orderBinding.refreshLayout.autoRefresh();
        this.refreshLayout = null;
        getMvpPresenter().getOrderList(orderType+1, 1, orderAdapter.getDatas().size());
    }

    @Override
    public void onCancelOrderSuccess() {
        orderBinding.refreshLayout.autoRefresh();
        this.refreshLayout = null;
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onAddMoneyAgreeSuccess() {
        orderBinding.refreshLayout.autoRefresh();
        this.refreshLayout = null;
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onAddMoneyRefuseSuccess() {
        orderBinding.refreshLayout.autoRefresh();
        this.refreshLayout = null;
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    /**
     * 加价支付成功
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.AddMoneyPaySuccess event) {
        this.refreshLayout = null;
        orderBinding.refreshLayout.autoRefresh();
        getMvpPresenter().getOrderList(orderType+1, 1, orderAdapter.getDatas().size());
        // TODO 加价成功之后通知美发师
    }

    /**
     * 订单消息通知
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewMessage message) {
        orderBinding.refreshLayout.autoRefresh();
        this.refreshLayout = null;
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onRemindStylistSuccess() {
        showToast("发送提醒成功.");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndx = 1;
        this.refreshLayout = null;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
