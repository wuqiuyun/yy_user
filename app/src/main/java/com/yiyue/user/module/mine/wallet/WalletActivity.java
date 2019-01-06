package com.yiyue.user.module.mine.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityMineWalletBinding;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.local.preferences.CommonSharedPreferences;
import com.yiyue.user.model.vo.bean.CashInfoAccountBean;
import com.yiyue.user.model.vo.bean.CashInfoBean;
import com.yiyue.user.model.vo.bean.CoinInfoAccountBean;
import com.yiyue.user.model.vo.bean.CoinInfoBean;
import com.yiyue.user.module.common.WebActivity;
import com.yiyue.user.module.mine.wallet.address.AddressActivity;
import com.yiyue.user.module.mine.wallet.cochain.CochainActivity;
import com.yiyue.user.module.mine.wallet.recharge.RechargeActivity;
import com.yiyue.user.module.mine.wallet.transfer.TransferActivity;
import com.yiyue.user.module.mine.wallet.withdraw.WithdrawActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.util.MathematicsUtils;
import com.yiyue.user.util.RefreshLayoutUtil;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * 我的钱包
 * <p>
 * Create by zm on 2018/9/29
 */
@CreatePresenter(WalletPresenter.class)
public class WalletActivity extends BaseMvpAppCompatActivity<IWalletView, WalletPresenter>
        implements IWalletView, OnLoadMoreListener, OnRefreshListener, ClickHandler {

    public static final int WALLET_TIXIAN = 0x008;
    public static final int WALLET_ZHUANZENG = 0x009;

    protected int pageIndx = 1; //第几页
    protected int pageSize = 10; // 每页数量
    private RefreshLayout refreshLayout;

    private  ActivityMineWalletBinding mBinding;

    private WalletAdapter adapter;
    private WalletCoinAdapter coinadapter;
    private ArrayList<CashInfoAccountBean> cashInfoAccountBeans = new ArrayList<>();
    private ArrayList<CoinInfoAccountBean> coinInfoAccountBeans = new ArrayList<>();

    private String coinBalance = "0.00",cashBalance = "0.00";//余额
    private double cost = 0.00;//代币参考价
    private double coinRate;//代币总价值
    private double coinBalances;//代币数量
    private int type = 0; // 0余额，1代币
    private boolean isShow = false;//显示余额是否
    private String mMoneyFree;//冻结金额
    private double mMoneyNow;//可提现金额
    private String coindefault;
    private NumberFormat nf;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_wallet;
    }

    @Override
    protected void init() {

        initView();
    }

    @SuppressLint("StringFormatInvalid")
    private void initView() {

        mBinding = (ActivityMineWalletBinding) viewDataBinding;
        mBinding.setClick(this);

        // 返回
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightTextClickListener(v -> {
            jumpRightClick();
        });
        // refreshLayout
        mBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mBinding.refreshLayout.setHeaderHeight(0);
        mBinding.refreshLayout.setOnLoadMoreListener(this);
        // recycleview
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycleView1.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WalletAdapter(this);

        coinadapter = new WalletCoinAdapter(this);

        mBinding.recycleView.setAdapter(adapter);

        mBinding.recycleView1.setAdapter(coinadapter);

        mBinding.recycleView.setVisibility(View.VISIBLE);

        mBinding.recycleView1.setVisibility(View.GONE);
        coindefault = FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getSysCoinDefault());
        mBinding.rbCurrency.setText(coindefault);
        // type
        setType(0);
    }

    private void setType(int type) {
        this.type = type;
        mBinding.titleView.setRightText(type == 0 ? getString(R.string.description) : getString(R.string.address));
//        mBinding.tvCurrencyPrice.setVisibility(type == 0 ? View.INVISIBLE : View.VISIBLE);
//        mBinding.tvWallet.setText(getString(type == 0 ? R.string.wallet_balance : R.string.wallet_currency));
        mBinding.tvRecharge.setText(getString(type == 0 ? R.string.wallet_recharge : R.string.wallet_cochain));
        mBinding.tvWithdraw.setText(getString(type == 0 ? R.string.wallet_withdraw : R.string.wallet_transfer));
        pageIndx = 1;
        //获取余额或代币余额
        if (type == 0){
            mBinding.llWalletOne.setVisibility(View.VISIBLE);
            mBinding.llWalletTwo.setVisibility(View.GONE);
            mBinding.recycleView.setVisibility(View.VISIBLE);
            mBinding.recycleView1.setVisibility(View.GONE);
            //获取余额
            getMvpPresenter().getCashInfo();
            getMvpPresenter().getCashInfoAccount(1,10,this);
        }else{
            mBinding.llWalletOne.setVisibility(View.GONE);
            mBinding.llWalletTwo.setVisibility(View.VISIBLE);
            mBinding.tvWallet.setText(coindefault);

            mBinding.recycleView.setVisibility(View.GONE);

            mBinding.recycleView1.setVisibility(View.VISIBLE);
            //获取代币余额
            getMvpPresenter().getCoinInfo();
            getMvpPresenter().getCoinInfoAccount(1,10,this);
        }
    }

    private void jumpRechargeClick() {
        if (type==0){
            //充值
            startActivity(WalletActivity.this,RechargeActivity.class);
        }else {
            //上链
            Intent intent = new Intent().putExtra("currency", mBinding.tvBalance1.getText().toString());
            intent.setClass(WalletActivity.this,CochainActivity.class);
            startActivity(intent);
        }
    }

    private void jumpWithdrawClick() {
        if (type==0){
            if (mMoneyNow>0){//余额减去冻结余额要大于100才能提现
                //提现
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.WITHDRAW_MONEY,mMoneyNow);
                intent.setClass(this,WithdrawActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,WALLET_TIXIAN);
            }else {
                ToastUtils.shortToast("可提现余额不足");
            }
        }else {
            //转赠
            if (coinBalances>0){
                Intent intent = new Intent().putExtra("currency", mBinding.tvBalance1.getText().toString()).putExtra("coincost",String.valueOf(cost));
                intent.setClass(WalletActivity.this,TransferActivity.class);
                startActivityForResult(intent,WALLET_ZHUANZENG);
            }else {
                ToastUtils.shortToast("可转赠"+FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getSysCoinDefault())+"不足");
            }
        }
    }

    private void jumpRightClick() {
        if (type == 0){
            String url = Constants.WEB_MONEYEXOLAIN;
            WebActivity.startActivity(WalletActivity.this,url,"钱包说明");
        }else {
            startActivity(WalletActivity.this, AddressActivity.class);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0,null);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageIndx ++;
        this.refreshLayout = refreshLayout;
        //获取余额或代币余额
        if (type == 0){
            //获取余额
            getMvpPresenter().getCashInfoAccount(pageIndx,pageSize,this);
        }else{
            //获取代币余额
            getMvpPresenter().getCoinInfoAccount(pageIndx,pageSize,this);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageIndx = 1;
        this.refreshLayout = refreshLayout;
        //获取余额或代币余额
        if (type == 0){
            //获取余额
            getMvpPresenter().getCashInfoAccount(pageIndx,pageSize,this);
        }else{
            //获取代币余额
            getMvpPresenter().getCoinInfoAccount(pageIndx,pageSize,this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_balance: // 余额
                setType(0);
                break;
            case R.id.rb_currency: // 代币
                setType(1);
                break;
            case R.id.tv_recharge: // 充值
                jumpRechargeClick();
                break;
            case R.id.tv_withdraw: // 提现
                jumpWithdrawClick();
                break;
        }
    }

    @Override
    public void onGetCashInfoSuccess(CashInfoBean bean) {
        cashBalance = FormatUtil.Formatstring(bean.getBalance());//余额
        mMoneyFree = FormatUtil.Formatstring(bean.getFreezeBalance());//冻结余额
        try {
            if (!TextUtils.isEmpty(cashBalance)&&!TextUtils.isEmpty(mMoneyFree)){
                double mMoney = Double.parseDouble(cashBalance);
                double mMoney1 = Double.parseDouble(mMoneyFree);
                mMoneyNow = MathematicsUtils.sub(mMoney,mMoney1);//可用余额

                mBinding.tvMoenyBalance.setText(FormatUtil.FormatDouble((mMoneyNow)));
                mBinding.tvMoenyEnbalance.setText(FormatUtil.FormatDouble(mMoney1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onGetCoinInfoSuccess(CoinInfoBean bean) {
        coinRate = bean.getRate();//代币总价值
        coinBalances = bean.getBalance();
        if (coinRate>0){
            cost = MathematicsUtils.div(coinRate,coinBalances);
        }
        coinBalance = FormatUtil.Formatstring(String.valueOf(coinBalances));
        mBinding.tvBalance1.setText(FormatUtil.FormatDouble(coinBalances));
    }

    @Override
    public void onGetCashInfoListSuccess(ArrayList<CashInfoAccountBean> bean) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        if (bean!=null&&bean.size()>0){
            if (pageIndx==1){
                cashInfoAccountBeans.clear();
            }
            cashInfoAccountBeans.addAll(bean);
            adapter.setDatas(cashInfoAccountBeans, true);
        }
    }

    @Override
    public void onGetCoinInfoListSuccess(ArrayList<CoinInfoAccountBean> bean) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        if (bean!=null&&bean.size()>0){
            if (pageIndx==1){
                coinInfoAccountBeans.clear();
            }
            coinInfoAccountBeans.addAll(bean);
            coinadapter.setDatas(coinInfoAccountBeans, true);

        }
    }

    @Override
    public void onGetCashInfoListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
    }

    @Override
    public void onGetCoinInfoListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case WALLET_TIXIAN: // 提现
                    // type
                    setType(0);
                    break;
                case WALLET_ZHUANZENG: // 转赠
                    // type
                    setType(1);
                    break;
            }
        }
    }

}
