package com.yiyue.user.module.home.store;

import android.Manifest;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.amap.LocationPresenter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStoreManagerBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.share.ShareUtils;
import com.yiyue.user.model.vo.bean.ContactBean;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.model.vo.bean.StoreInfoBean;
import com.yiyue.user.model.vo.bean.StoreScoreBean;
import com.yiyue.user.model.vo.bean.StoreServerScopeBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.model.vo.result.ContactResult;
import com.yiyue.user.model.vo.result.StoreScoreResult;
import com.yiyue.user.module.home.evaluation.EvaluationManagerActivity;
import com.yiyue.user.module.home.store.reservation.StoreReservationActivity;
import com.yiyue.user.module.im.chat.ChatActivity;
import com.yiyue.user.module.im.sharetofriend.ShareToFriendActivity;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.store.join.StoreStylistActivity;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yiyue.user.util.FormatKmUtil;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.util.StringUtil;
import com.yiyue.user.widget.bottomview.BottomViewFactory;
import com.yiyue.user.widget.bottomview.MapSelectView;
import com.yiyue.user.widget.dialog.BaseEasyDialog;
import com.yiyue.user.widget.dialog.EasyDialog;
import com.yiyue.user.widget.dialog.ViewConvertListener;
import com.yiyue.user.widget.dialog.ViewHolder;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 门店管理
 * <p>
 * Create by wqy on 2018/11/10
 */
@CreatePresenter(StoreManagerPresenter.class)
public class StoreManagerActivity extends BaseMvpAppCompatActivity<IStoreManagerView, StoreManagerPresenter>
        implements IStoreManagerView, ClickHandler, AMapLocationListener {

    private ActivityStoreManagerBinding mBinding;
    private static final String STORE_ID = "storeId";

    private double latitude;
    private double longitude;
    private String address;

    private String storeId;
    private String userId;
    private int isCollection = 0;

    private String inviteCode = null;//邀请码

    // 门店联系方式
    private ContactBean contactBean;
    StoreInfoBean storeInfoBean;

    private StylistAdapter mStylistAdapter;
    private ServiceScopeAdapter mServiceAdapter;
    private StorePageAdapter mStorePageAdapter;
    private ArrayList<String> mPhotos = new ArrayList<>();
    private LocationPresenter locationPresenter;
    private String count = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
        getMvpPresenter().findReCode();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.storeId = bundle.getString(STORE_ID);
        }
        userId = AccountManager.getInstance().getUserId();
    }

    private void initView() {
        mBinding = (ActivityStoreManagerBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setLeftClickListener(v -> finish());

        mBinding.titleView.setRightImgClickListener(v -> {
            // 分享
            showShareDialog();
        });

        mBinding.titleView.setSubRightImgClickListener(v -> {
            if (!AccountManager.getInstance().isLogin()) {
                LoginActivity.startActivity(StoreManagerActivity.this, LoginActivity.class);
                return;
            }
            // 门店收藏/取消 isCollection 是否收藏 1收藏 0未收藏
            if ("1".equals(isCollection)) {
                // 取消收藏
                getMvpPresenter().updateCollectionType(isCollection, storeId, userId);
            } else {
                // 收藏
                getMvpPresenter().updateCollectionType(isCollection, storeId, userId);
            }
        });

        // stylist list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.stylistList.setLayoutManager(linearLayoutManager);
        mBinding.stylistList.addItemDecoration(new SpaceItemHorizontalDecoration(20));
        mStylistAdapter = new StylistAdapter(getBaseContext());
        mBinding.stylistList.setAdapter(mStylistAdapter);
        mStylistAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                long stylistId = mStylistAdapter.getDatas().get(position).getStylistId();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STYLISTId, String.valueOf(stylistId));
                StylistDetailsActivity.startActivity(StoreManagerActivity.this, StylistDetailsActivity.class, bundle);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

        // service scope list
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getBaseContext());
        LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.serviceScopeList.setLayoutManager(LayoutManager);
        mBinding.serviceScopeList.addItemDecoration(new SpaceItemHorizontalDecoration(30));
        mServiceAdapter = new ServiceScopeAdapter(getBaseContext());
        mBinding.serviceScopeList.setAdapter(mServiceAdapter);

        if (mPhotos.size() > 0) {
            mBinding.tvCornerMark.setText("1/" + mPhotos.size());
        }

        // banner
        mStorePageAdapter = new StorePageAdapter(getBaseContext(), mPhotos);
        mBinding.viewPage.setAdapter(mStorePageAdapter);
        mBinding.viewPage.setCurrentItem(0);
        mBinding.viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.tvCornerMark.setText(++position + "/" + mPhotos.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData() {
        locationPresenter = new LocationPresenter(this);
        locationPresenter.setMapLocationListener(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    locationPresenter.startLocation(true);// 传true定位一次,不传2秒一次
                });

        // 入驻美发师
        getMvpPresenter().getNexusStyScrool("3", storeId, userId);
        // 门店联系方式
        getMvpPresenter().contact(storeId, userId);
        // 门店顾客评价
        getMvpPresenter().getStoreScore(userId, storeId);
        // 门店服务范围
        getMvpPresenter().getStoreServerScope(userId, storeId);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_more: // 查看更多
                Bundle b = new Bundle();
                b.putString(Constants.STORE_ID, storeId);
                StoreStylistActivity.startActivity(StoreManagerActivity.this, StoreStylistActivity.class, b);
                break;
            case R.id.tv_look_comment: // 查看全部评价
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STORE_ID, storeId);
                bundle.putString("from", "store");
                bundle.putString("count", count);
                EvaluationManagerActivity.startActivity(getBaseContext(), EvaluationManagerActivity.class, bundle);
                break;
            case R.id.tv_call: // 电话
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StoreManagerActivity.this, LoginActivity.class);
                    return;
                }
                if (!TextUtils.isEmpty(contactBean.getTelPhone())) {
                    PhoneUtil.toCallPhone(StoreManagerActivity.this, contactBean.getTelPhone());
                }
                break;
            case R.id.tv_advisory: // 咨询
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StoreManagerActivity.this, LoginActivity.class);
                    return;
                }
                ChatActivity.startFromZIxunActivity(this, contactBean.getStoreImName(), contactBean.getStoreUserId(), contactBean.getStoreNickname(), contactBean.getStoreHeadImg());
                break;
            case R.id.tv_reservation: // 预约到店
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StoreManagerActivity.this, LoginActivity.class);
                    return;
                }
                StoreReservationActivity.startActivity(StoreManagerActivity.this, storeId);
                break;
            case R.id.ll_address:
                if (storeInfoBean == null) {
                    ToastUtils.shortToast("门店位置异常.");
                    new RxPermissions(this)
                            .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                            .subscribe(grant -> {
                                locationPresenter.startLocation(true);// 传true定位一次,不传2秒一次
                            });
                    return;
                }
                // 选择地图
                MapSelectView mapView = (MapSelectView) BottomViewFactory.create(this, BottomViewFactory.Type.Map);
                if (latitude > 0 && longitude > 0) {
                    mapView.setStartLocation(latitude, longitude, address);
                }
                mapView.setEndLocation(storeInfoBean.getLat(), storeInfoBean.getLng(), storeInfoBean.getLocation());
                mapView.showBottomView(true);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        latitude = aMapLocation.getLatitude();
        longitude = aMapLocation.getLongitude();
        address = aMapLocation.getAddress();
        // 门店基本信息
        getMvpPresenter().getStoreInfo(latitude, longitude, storeId, userId);
    }

    // 店铺基本信息
    @Override
    public void getInfoSuccess(StoreInfoBean storeInfoBean) {
        this.storeInfoBean = storeInfoBean;
        // banner
        mPhotos.addAll(storeInfoBean.getStorePhotos());
        mStorePageAdapter.notifyDataSetChanged();
        if (mPhotos.size() > 0) {
            mBinding.tvCornerMark.setText("1/" + mPhotos.size());
        }

        mBinding.tvStoreName.setText(storeInfoBean.getStoreName());
        mBinding.tvRatingNum.setText(storeInfoBean.getGrade() + "分");

        // 地址
        mBinding.tvAddressArea.setText(storeInfoBean.getLocation());
        // 距离
        double d = Double.valueOf(storeInfoBean.getDistance());
        mBinding.tvAddress.setText("(距您" + FormatKmUtil.FormatKmStr(d) + ")");

        mBinding.ratingBar.setOnTouchListener((v, evevt) -> true);
        mBinding.ratingBar.setRating(Float.parseFloat(String.valueOf(storeInfoBean.getGrade())));
        //是否收藏
        isCollection = storeInfoBean.getIsCollection();
        if (isCollection == 1) {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_selected));
        } else {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
        }
        //总接单
        mBinding.orderNumber.setText(String.format(getString(R.string.total_order_number) ,
                String.valueOf(storeInfoBean.getServers())));//总接单
    }

    // 入驻美发师
    @Override
    public void getStylistSuccess(List<StylistBean> stylistBeans) {
        ArrayList<StylistBean> list = (ArrayList<StylistBean>) stylistBeans;
        mStylistAdapter.setDatas(list, true);
    }

    // 门店联系方式
    @Override
    public void getContactSuccess(ContactResult contactResult) {
        contactBean = contactResult.getData();
    }

    // 门店顾客评价
    @Override
    public void getStoreScoreSuccess(StoreScoreResult result) {
        StoreScoreBean bean = result.getData();
        //综合评分
        mBinding.mrScore.setOnTouchListener((v, event) -> true);
        mBinding.mrScore.setRating(Float.valueOf(result.getData().getScore()));
        // 环境评分
        mBinding.tvEnvironmentScore.setText(String.format(getString(R.string.comment_score_skill), bean.getEnvironmentScore()));
        // 服务态度
        mBinding.tvServiceScore.setText(String.format(getString(R.string.comment_score_service), bean.getServerScore()));
        // 服务水平
        if ("10".equals(bean.getPareServerAvg())) {
            mBinding.tvServiceGrade.setVisibility(View.GONE);
        } else {
            mBinding.tvServiceGrade.setVisibility(View.VISIBLE);
        }
        if ("-1".equals(bean.getPareServerAvg())) {
            mBinding.tvServiceGrade.setText("低于平均水平");
        } else if ("0".equals(bean.getPareServerAvg())) {
            mBinding.tvServiceGrade.setText("低于平均水平");
        } else if ("1".equals(bean.getPareServerAvg())) {
            mBinding.tvServiceGrade.setText("大于平均水平");
        }

        // 环境水平
        if ("10".equals(bean.getPareEnvirtAvg())) {
            mBinding.tvEnvironmentGrade.setVisibility(View.GONE);
        } else {
            mBinding.tvEnvironmentGrade.setVisibility(View.VISIBLE);
        }
        if ("-1".equals(bean.getPareEnvirtAvg())) {
            mBinding.tvEnvironmentGrade.setText("低于平均水平");
        } else if ("0".equals(bean.getPareEnvirtAvg())) {
            mBinding.tvEnvironmentGrade.setText("低于平均水平");
        } else if ("1".equals(bean.getPareEnvirtAvg())) {
            mBinding.tvEnvironmentGrade.setText("大于平均水平");
        }

        // 查看全部评价
        count = bean.getScoretimes();
        mBinding.tvLookComment.setText(String.format(getString(R.string.comment_look_all2), count));
        mBinding.tvLookComment.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    // 门店服务范围
    @Override
    public void getStoreServerScopeSuccess(StoreServerScopeBean storeServerScopeBean) {
        ArrayList<String> list = (ArrayList<String>) storeServerScopeBean.getCatergoryNames();
        mServiceAdapter.setDatas(list, true);
    }

    // 门店收藏/取消
    @Override
    public void collectionSuccess(BaseResponse response) {
        //返回我的收藏
        setResult(CollectActivity.COLLECTPAGE2);
        if (response.isSuccess()) {
            // 收藏 isCollection 是否收藏，1收藏，0未收藏
            if (isCollection == 1) {
                showToast("取消收藏成功");
                isCollection = 0;
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
            } else {
                showToast("收藏成功");
                isCollection = 1;
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_selected));
            }
        }
    }

    // 收藏失败
    @Override
    public void collectFail() {
        if (isCollection == 1) {
            showToast("取消收藏失败");
        } else {
            showToast("收藏失败");
        }
    }

    //分享弹框
    private void showShareDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            if (!AccountManager.getInstance().isLogin()) {
                                ToastUtils.shortToast("登录才能分享给好友");
                                return;
                            }
                            //分享的店铺的相关信息传递 没有传null 此页面门店的storeId必传
                            if (storeInfoBean != null) {
                                ShareToFriendActivity.startShareToFriendActivity(StoreManagerActivity.this, 101, "", storeInfoBean.getStoreName(), storeInfoBean.getStoreId() + "",
                                        "", storeInfoBean.getStoreHeadImg(), storeInfoBean.getLocation());
                            }
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.BOTTOM)
                .setMargin(0)
                .setOutCancel(true)
                .show(this.getSupportFragmentManager());
    }

    /**
     * 分享回调
     */
    PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            DLog.e("kid", "分享成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            DLog.e("kid", "分享失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            DLog.e("kid", "分享取消");
        }
    };

    @Override
    public void findReCodeSuc(ReCodeBean recode) {
        inviteCode = recode.getInvitecode();
    }

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_STORE_ID).append(storeId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_OPUS_ID).append("");
//            param.append("&").append(Constants.WEB_STYLIST_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_STORE_ID).append(storeId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_OPUS_ID).append("");
//            param.append("?").append(Constants.WEB_STYLIST_ID).append("");
        }

        return Constants.WEB_STORE_DETAILS + param.toString();
    }

}
