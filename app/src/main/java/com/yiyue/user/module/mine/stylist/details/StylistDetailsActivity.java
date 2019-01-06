package com.yiyue.user.module.mine.stylist.details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.recycleview.SpaceItemDecoration;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityStylistDetailsBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.share.ShareUtils;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.model.vo.bean.StylistDetailsBean;
import com.yiyue.user.module.home.evaluation.EvaluationManagerActivity;
import com.yiyue.user.module.home.service.bundle.detail.BundleDetailActivity;
import com.yiyue.user.module.home.store.StoreManagerActivity;
import com.yiyue.user.module.im.chat.ChatActivity;
import com.yiyue.user.module.im.sharetofriend.ShareToFriendActivity;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.stylist.introduction.StylistIntroductionActivity;
import com.yiyue.user.module.mine.stylist.project.ProjectListActivity;
import com.yiyue.user.module.mine.works.many.ManyWorksActivity;
import com.yiyue.user.util.CacheActivity;
import com.yiyue.user.util.PhoneUtil;
import com.yiyue.user.util.StringUtil;
import com.yiyue.user.widget.dialog.BaseEasyDialog;
import com.yiyue.user.widget.dialog.EasyDialog;
import com.yiyue.user.widget.dialog.ViewConvertListener;
import com.yiyue.user.widget.dialog.ViewHolder;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.component.toast.ToastUtil;
import com.yl.core.util.StatusBarUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static com.yiyue.user.model.constant.Constants.HEAD_PORTRAIT;
import static com.yiyue.user.model.constant.Constants.NICK_NAME;
import static com.yiyue.user.model.constant.Constants.SERVICEID;
import static com.yiyue.user.model.constant.Constants.STORE_ID;
import static com.yiyue.user.model.constant.Constants.STYLISTId;
import static com.yiyue.user.model.constant.Constants.STYLIST_POSITION;

/**
 * 美发师详情
 * <p>
 */
@CreatePresenter(StylistDetailsPresenter.class)
public class StylistDetailsActivity extends BaseMvpAppCompatActivity<IStylistDetailsView, StylistDetailsPresenter>
        implements IStylistDetailsView, ClickHandler {

    ActivityStylistDetailsBinding mBinding;

    private StylistCouponAdapter couponAdapter;
    private ArrayList<StylistDetailsBean.CardCouponDTOs> couponBeans = new ArrayList<>(); // 优惠券列表

    private ServiceProjectAdapter projectAdapter;
    private ArrayList<StylistDetailsBean.CardServerItemsBean> projectBeans = new ArrayList<>(); // 服务项目列表

    private ServiceBundleAdapter bundleAdapter;
    private ArrayList<StylistDetailsBean.CardPackagesBean> bundleBeans = new ArrayList<>(); // 套餐列表

    private StoreAdapter storeAdapter;
    private ArrayList<StylistDetailsBean.CardStoreDTOsBean> storeBeans = new ArrayList<>(); // 入驻门店列表

    private WorksAdapter worksAdapter;
    private ArrayList<StylistDetailsBean.CardOpusDTOsBean> worksBeans = new ArrayList<>(); // 作品列表
    private String stylistId = "";
    private boolean isCollection = false; //是否收藏
    private String tel = "";//电话
    private String description = "";//简介
    private String headPortrait = "";//头像
    private String nickName = "";//昵称
    private String stylistPosition = ""; // 职级

    private String inviteCode = null;//邀请码

    //以下为前往聊天窗使用参数
    private String chatId, chatUserId, chatName, chatPath;
    private String count = "0"; // 评价总数
    private StylistDetailsBean stylistDetailsBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stylist_details;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.stylistId = bundle.getString(STYLISTId);
        }
    }

    private void initView() {
        if (!CacheActivity.activityList.contains(StylistDetailsActivity.this)) {
            CacheActivity.addActivity(StylistDetailsActivity.this);
        }
        mBinding = (ActivityStylistDetailsBinding) viewDataBinding;
        mBinding.setClick(this);

        // 优惠券
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.couponList.setLayoutManager(linearLayoutManager);
        mBinding.couponList.addItemDecoration(new SpaceItemHorizontalDecoration(20));
        mBinding.couponList.setHasFixedSize(true);
        mBinding.couponList.setNestedScrollingEnabled(false);
        couponAdapter = new StylistCouponAdapter(getBaseContext());
        mBinding.couponList.setAdapter(couponAdapter);
        couponAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                StylistDetailsBean.CardCouponDTOs bean = couponAdapter.getDatas().get(position);
                if (bean.getUsePercent() == 1) {
                    return;
                }
                getMvpPresenter().drawCoupon(String.valueOf(bean.getCouponId()), bean.getType());
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

        // 服务项目
        mBinding.projectList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.projectList.addItemDecoration(new SpaceItemDecoration(20));
        projectAdapter = new ServiceProjectAdapter(getBaseContext());
        mBinding.projectList.setHasFixedSize(true);
        mBinding.projectList.setNestedScrollingEnabled(false);
        mBinding.projectList.setAdapter(projectAdapter);
        projectAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(STYLISTId, stylistId);
                ProjectListActivity.startActivity(StylistDetailsActivity.this, ProjectListActivity.class, bundle);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

        // 套餐
        mBinding.listService.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.listService.addItemDecoration(new SpaceItemDecoration(30));
        bundleAdapter = new ServiceBundleAdapter(getBaseContext());
        mBinding.listService.setHasFixedSize(true);
        mBinding.listService.setNestedScrollingEnabled(false);
        mBinding.listService.setAdapter(bundleAdapter);
        bundleAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                int serviceId = bundleAdapter.getDatas().get(position).getServiceId();
                Bundle bundle = new Bundle();
                bundle.putString(SERVICEID, String.valueOf(serviceId));
                BundleDetailActivity.startActivity(StylistDetailsActivity.this, BundleDetailActivity.class, bundle);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

        // 入驻门店
        mBinding.storeList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.storeList.addItemDecoration(new SpaceItemDecoration(20));
        mBinding.storeList.setHasFixedSize(true);
        mBinding.storeList.setNestedScrollingEnabled(false);
        storeAdapter = new StoreAdapter(getBaseContext());
        mBinding.storeList.setAdapter(storeAdapter);
        storeAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                String storeId = String.valueOf(storeAdapter.getDatas().get(position).getStoreId());
                Bundle bundle = new Bundle();
                bundle.putString(STORE_ID, storeId);
                StoreManagerActivity.startActivity(StylistDetailsActivity.this, StoreManagerActivity.class, bundle);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

        // 作品
        mBinding.worksList.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        mBinding.worksList.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        mBinding.worksList.setHasFixedSize(true);
        mBinding.worksList.setNestedScrollingEnabled(false);
        worksAdapter = new WorksAdapter(getBaseContext());
        mBinding.worksList.setAdapter(worksAdapter);

    }

    private void loadData() {
        getMvpPresenter().getStylistDetails(stylistId, AccountManager.getInstance().getUserId());
        getMvpPresenter().findReCode();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {

            case R.id.iv_look_more: // 查看更多作品
                bundle = new Bundle();
                bundle.putString(STYLISTId, stylistId);
                bundle.putString(HEAD_PORTRAIT, headPortrait);
                bundle.putString(NICK_NAME, nickName);
                bundle.putString(STYLIST_POSITION, stylistPosition);
                ManyWorksActivity.startActivity(getBaseContext(), ManyWorksActivity.class, bundle);
                break;

            case R.id.tv_look_comment: // 查看更多评论
                bundle = new Bundle();
                bundle.putString(STYLISTId, stylistId);
                bundle.putString("from", "stylist");
                bundle.putString("count", count);
                EvaluationManagerActivity.startActivity(getBaseContext(), EvaluationManagerActivity.class, bundle);
                break;

            case R.id.btn_tell_phone: // 电话
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StylistDetailsActivity.this, LoginActivity.class);
                    return;
                }
                if (!TextUtils.isEmpty(tel)) {
                    PhoneUtil.toCallPhone(StylistDetailsActivity.this, tel);
                }
                break;

            case R.id.btn_send_msg: // 咨询
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StylistDetailsActivity.this, LoginActivity.class);
                    return;
                }
                ChatActivity.startFromZIxunActivity(StylistDetailsActivity.this, chatId, chatUserId, chatName, chatPath);
                break;

            case R.id.btn_introduction: // 查看简介
                StylistIntroductionActivity.startActivity(this, description);
                break;

            case R.id.btn_stylist_reservation: // 立即预约
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StylistDetailsActivity.this, LoginActivity.class);
                    return;
                }
                if (stylistDetailsBean != null) {
                    Bundle b = new Bundle();
                    b.putString(Constants.STYLISTId, stylistId);
                    ProjectListActivity.startActivity(this, ProjectListActivity.class, b);
                }
                break;

            case R.id.iv_back: // 返回
                finish();
                break;

            case R.id.iv_collection: // 收藏  type 1:关注  0：取消关注
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(StylistDetailsActivity.this, LoginActivity.class);
                    return;
                }
                if (isCollection) {
                    getMvpPresenter().stylistCollection(stylistId, 0);
                } else {
                    getMvpPresenter().stylistCollection(stylistId, 1);
                }
                break;

            case R.id.iv_share: // 分享
                showShareDialog();
                break;
        }
    }

    @Override
    public void getDetailsSuccess(StylistDetailsBean bean) {
        if (bean != null) {
            stylistDetailsBean = bean;
            chatId = bean.getImusername();
            chatUserId = bean.getUserId() + "";
            chatName = bean.getNickname();
            chatPath = bean.getHeadPortrait();

            // 优惠券
            if (bean.getCardCouponDTOs() != null && bean.getCardCouponDTOs().size() > 0) {
                couponBeans.clear();
                couponBeans.addAll(bean.getCardCouponDTOs());
                couponAdapter.setDatas(couponBeans, true);
            }

            //服务项目
            if (bean.getCardServerItems() != null && bean.getCardServerItems().size() > 0) {
                projectBeans.clear();
                projectBeans.addAll(bean.getCardServerItems());
                projectAdapter.setDatas(projectBeans, true);
            }


            // 套餐
            if (bean.getCardPackages() != null && bean.getCardPackages().size() > 0) {
                bundleBeans.clear();
                bundleBeans.addAll(bean.getCardPackages());
                bundleAdapter.setDatas(bundleBeans, true);
            }

            // 入驻门店
            if (bean.getCardStoreDTOs() != null && bean.getCardStoreDTOs().size() > 0) {
                storeBeans.clear();
                storeBeans.addAll(bean.getCardStoreDTOs());
                storeAdapter.setDatas(storeBeans, true);
                mBinding.btnStylistReservation.setEnabled(true);
            } else {
                // 未入驻门店的美发师不能预约
                mBinding.btnStylistReservation.setEnabled(false);
            }

            // 作品
            if (bean.getCardOpusDTOs() != null && bean.getCardOpusDTOs().size() > 0) {
                worksBeans.clear();
                worksBeans.addAll(bean.getCardOpusDTOs());
                worksAdapter.setDatas(worksBeans, true);
            }

            tel = bean.getMobile();
            isCollection = bean.isCollection();
            headPortrait = bean.getHeadPortrait();
            nickName = bean.getNickname();
            stylistPosition = bean.getPosition();

            // 是否已收藏
            if (isCollection) {
                mBinding.ivCollection.setImageResource(R.drawable.icon_collection_selected);
            } else {
                mBinding.ivCollection.setImageResource(R.drawable.icon_collection_nor);
            }

            //头像
            ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                    .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                    .setAsBitmap(true)
                    .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                    .setErrorResId(R.drawable.icon_head_pic_def)
                    .setBlur(true)
                    .build();
            ImageLoader.loadImage(mBinding.ivPhoto, bean.getHeadPortrait(), config, null);
            mBinding.tvName.setText(bean.getNickname());// 昵称
            int gender = bean.getGender(); // 性别

            if (gender == 2) {//1 男 2 女 3 人妖
                mBinding.ivGender.setImageResource(R.drawable.icon_girl);//女
            } else {
                mBinding.ivGender.setImageResource(R.drawable.icon_boy);//男
            }

            // 标签
            mBinding.tvLabel.setText(bean.getYearbirth() + "/" + bean.getPosition());
            mBinding.tvServiceType.setText(String.format(getString(R.string.store_service_type) +
                    bean.getServerTypes())); // 服务类别
            mBinding.ratingStylist.setOnTouchListener((v, event) -> true);
            mBinding.ratingStylist.setRating(bean.getStar()); // 星级
            mBinding.ratingNum.setText(String.valueOf(bean.getPoint())); // 分数
            description = bean.getDescription();
            mBinding.tvIntroduction.setText(bean.getDescription());//简介
            mBinding.ratingBar.setOnTouchListener((v, event) -> true);
            mBinding.ratingBar.setRating(bean.getStar());//  综合评分
            mBinding.orderNumber.setText(String.format(getString(R.string.total_order_number) ,
                    String.valueOf(bean.getOrderNumer())));//总接单

            List<StylistDetailsBean.CardGradeDTOsBean> gradeBean = bean.getCardGradeDTOs();

            String descrip0 = gradeBean.get(0).getGradeDescrip() != null ? gradeBean.get(0).getGradeDescrip() : "";
            String descrip1 = gradeBean.get(1).getGradeDescrip() != null ? gradeBean.get(1).getGradeDescrip() : "";

            if (!TextUtils.isEmpty(gradeBean.get(0).getGradeType())) {
                mBinding.tvSkillScore.setText(gradeBean.get(0).getGradeType() + "：" + gradeBean.get(0).getPoint() + descrip0);
            } else {
                mBinding.tvSkillScore.setText("");
            }

            if (!TextUtils.isEmpty(gradeBean.get(1).getGradeType())) {
                mBinding.tvServiceScore.setText(gradeBean.get(1).getGradeType() + "：" + gradeBean.get(1).getPoint() + descrip1);
            } else {
                mBinding.tvServiceScore.setText("");
            }

            // 查看全部评价
            count = String.valueOf(bean.getEvaluatenumer());
            mBinding.tvLookComment.setText(String.format(getString(R.string.comment_look_all2), count));

            ImageLoaderConfig headConfig = new ImageLoaderConfig.Builder()
                    .setCropType(ImageLoaderConfig.CENTER_CROP)
                    .setAsBitmap(true)
                    .setPlaceHolderResId(R.drawable.home_bg)
                    .setErrorResId(R.drawable.home_bg)
                    .setBlur(true)
                    .build();
            ImageLoader.loadImage(mBinding.ivBg, bean.getBackGroundImg(), headConfig, null);
        }
    }

    @Override
    public void getDetailsFail() {
        showToast("获取美发师详情失败");
        finish();
    }

    // 收藏成功
    @Override
    public void collectionSuccess(BaseResponse baseResponse) {
        //返回我的收藏
        setResult(CollectActivity.COLLECTPAGE1);
        if (isCollection) {
            showToast("取消收藏成功");
            isCollection = false;
            mBinding.ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.icon_collection_nor));
        } else {
            showToast("收藏成功");
            isCollection = true;
            mBinding.ivCollection.setImageDrawable(getResources().getDrawable(R.drawable.icon_collection_selected));
        }
    }

    // 收藏失败
    @Override
    public void collectFail() {
        if (isCollection) {
            showToast("取消收藏失败");
        } else {
            showToast("收藏失败");
        }
    }

    @Override
    public void drawCouponSuccess() {
        loadData();
        showToast("领取成功");
    }

    @Override
    public void drawCouponFail() {

    }

    //分享弹框
    private void showShareDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        String name = String.format(getString(R.string.dialog_share_title_appteacher));
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    chatPath,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    chatPath,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    chatPath,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            if (!AccountManager.getInstance().isLogin()) {
                                ToastUtils.shortToast("登录才能分享给好友");
                                return;
                            }
                            //分享的美发师的相关信息传递 没有传null 此页面门店的storeId必传
                            if (stylistDetailsBean != null) {
                                ShareToFriendActivity.startShareToFriendActivity(
                                        StylistDetailsActivity.this,
                                        103,
                                        stylistDetailsBean.getImusername(),
                                        stylistDetailsBean.getNickname(),
                                        stylistDetailsBean.getStylistId() + "",
                                        stylistDetailsBean.getUserId() + "",
                                        stylistDetailsBean.getHeadPortrait(),
                                        stylistDetailsBean.getYearbirth());
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
    public void findReCodeSuc(ReCodeBean reCode) {
        inviteCode = reCode.getInvitecode();
    }

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_STYLIST_ID).append(stylistId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_OPUS_ID).append("");
//            param.append("&").append(Constants.WEB_STORE_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_STYLIST_ID).append(stylistId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_OPUS_ID).append("");
//            param.append("?").append(Constants.WEB_STORE_ID).append("");
        }

        return Constants.WEB_HAIRDRESSER_DETAILS + param.toString();
    }

}
