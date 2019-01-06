package com.yiyue.user.module.mine.works.details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityWorksDetailsBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.share.ShareUtils;
import com.yiyue.user.model.vo.bean.OpusDetailsBean;
import com.yiyue.user.model.vo.bean.ReCodeBean;
import com.yiyue.user.module.home.store.StoreManagerActivity;
import com.yiyue.user.module.im.sharetofriend.ShareToFriendActivity;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.stylist.project.ProjectListActivity;
import com.yiyue.user.util.StringUtil;
import com.yiyue.user.widget.dialog.BaseEasyDialog;
import com.yiyue.user.widget.dialog.EasyDialog;
import com.yiyue.user.widget.dialog.ViewConvertListener;
import com.yiyue.user.widget.dialog.ViewHolder;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static com.yiyue.user.model.constant.Constants.HEAD_PORTRAIT;
import static com.yiyue.user.model.constant.Constants.NICK_NAME;
import static com.yiyue.user.model.constant.Constants.OPUSID;
import static com.yiyue.user.model.constant.Constants.STYLISTId;
import static com.yiyue.user.model.constant.Constants.STYLIST_POSITION;

/**
 * 作品详情
 * <p>
 * Create by wqy on 2018/11/10
 */
@CreatePresenter(WorksDetailsPresenter.class)
public class WorksDetailsActivity extends BaseMvpAppCompatActivity<IWorksDetailsView, WorksDetailsPresenter>
        implements IWorksDetailsView, ClickHandler {

    private ActivityWorksDetailsBinding mBinding;
    private WorksPageAdapter mWorksPageAdapter;

    private ArrayList<OpusDetailsBean.PictruesBean> mOpusBeans = new ArrayList<>();
    private String opusId = "";
    private String userId = "";
    private boolean isCollection = false;
    private int collectionNum = 0;//收藏数
    private String headPortrait = "";//头像
    private String nickName = "";//昵称
    private String stylistPosition = ""; // 职级
    private String stylistId = "";

    private String inviteCode = null;//邀请码

    private OpusDetailsBean mOpusDetailsBean;
    private boolean mIsAppointment = false;//是否可以预约

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_works_details;
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
            this.opusId = bundle.getString(OPUSID);
            this.headPortrait = bundle.getString(HEAD_PORTRAIT);
            this.nickName = bundle.getString(NICK_NAME);
            this.stylistPosition = bundle.getString(STYLIST_POSITION);
            this.stylistId = bundle.getString(STYLISTId);
        } else {
            showToast("获取作品信息出错");
            finish();
        }
        userId = AccountManager.getInstance().getUserId();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityWorksDetailsBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        mBinding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        showShareDialog();
                    }
                }.start();
            }
        });

        mBinding.titleView.setSubRightImgClickListener(v -> {
            if (!AccountManager.getInstance().isLogin()) {
                LoginActivity.startActivity(WorksDetailsActivity.this, LoginActivity.class);
                return;
            }

            if (isCollection) {
                //取消收藏
                getMvpPresenter().opusCollection(opusId, 0);
            } else {
                // 收藏
                getMvpPresenter().opusCollection(opusId, 1);
            }
        });

        if (TextUtils.isEmpty(nickName)) {
            mBinding.tvName.setVisibility(View.INVISIBLE);
        } else {
            mBinding.tvName.setText(nickName);//昵称
        }

        if (TextUtils.isEmpty(stylistPosition)) {
            mBinding.tvRank.setVisibility(View.INVISIBLE);
        } else {
            mBinding.tvRank.setText(stylistPosition);// 职级
        }


        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(mBinding.ivAvatar, headPortrait, config, null);//  头像

        mBinding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mOpusBeans.size() == 0) {
                    return;
                }
                mBinding.tvPageNum.setText(String.format(getString(R.string.opus_page), position + 1, mOpusBeans.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void loadData() {
        getMvpPresenter().opusPageview(opusId);//增加查看数
        getMvpPresenter().getOpusDetail(opusId, userId);//查询作品详情
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reservation: // 预约
                if (!AccountManager.getInstance().isLogin()) {
                    LoginActivity.startActivity(WorksDetailsActivity.this, LoginActivity.class);
                    return;
                }
                if (mIsAppointment) {
                    Bundle bundle = new Bundle();
                    bundle.putString(STYLISTId, stylistId);
                    ProjectListActivity.startActivity(WorksDetailsActivity.this, ProjectListActivity.class, bundle);
                }
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getOpusDetailSuccess(OpusDetailsBean opusDetailsBean) {
        //是否收藏
        mOpusDetailsBean = opusDetailsBean;
        isCollection = opusDetailsBean.getIsCollection();
        collectionNum = opusDetailsBean.getCollection();
        if (isCollection) {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_selected));
        } else {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
        }

        mOpusBeans.addAll(opusDetailsBean.getPictrues());
        // 图片页数
        mBinding.tvPageNum.setText(String.format(getString(R.string.opus_page), 1, mOpusBeans.size()));
        mWorksPageAdapter = new WorksPageAdapter(getBaseContext(), mOpusBeans);
        mBinding.viewPage.setAdapter(mWorksPageAdapter);
        mBinding.viewPage.setCurrentItem(0);

        //收藏量
        mBinding.tvCollectionNum.setText(String.valueOf(opusDetailsBean.getCollection()));
        //转发量
        mBinding.tvForwardNum.setText(String.valueOf(opusDetailsBean.getReposted()));
        //查看量
        mBinding.tvLookNum.setText(String.valueOf(opusDetailsBean.getPageview()));
        //作品描述
        mBinding.tvDesc.setText(opusDetailsBean.getDescribe());
        //是否可以预约
        mIsAppointment = opusDetailsBean.isAppointment();
        if (mIsAppointment) {
            mBinding.tvReservation.setEnabled(true);
        } else {
            mBinding.tvReservation.setEnabled(false);
        }
    }

    // 收藏成功
    @Override
    public void collectionSuccess(BaseResponse baseResponse) {
        //返回我的收藏
        setResult(CollectActivity.COLLECTPAGE3);
        if (baseResponse.isSuccess()) {
            if (isCollection) {
                showToast("取消收藏成功");
                isCollection = false;
                collectionNum--;
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
            } else {
                showToast("收藏成功");
                isCollection = true;
                collectionNum++;
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_selected));
            }
            mBinding.tvCollectionNum.setText(String.valueOf(collectionNum));
        }
    }

    //收藏失败
    @Override
    public void collectFail() {
        if (isCollection) {
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
                        String picImg = mOpusBeans.get(mBinding.viewPage.getCurrentItem()).getPath();
                        String name = String.format(getString(R.string.dialog_share_title_appworks), nickName);
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            if (!AccountManager.getInstance().isLogin()) {
                                ToastUtils.shortToast("登录才能分享给好友");
                                return;
                            }
                            //分享的作品的相关信息传递 没有传null 此页面门店的storeId必传
                            if (mOpusDetailsBean != null) {
                                ShareToFriendActivity.startShareToFriendActivity(
                                        WorksDetailsActivity.this,
                                        102,
                                        "",
                                        nickName,
                                        mOpusDetailsBean.getOpusId() + "",
                                        "",
                                        headPortrait,
                                        mOpusDetailsBean.getDescribe());
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
            getMvpPresenter().opusRepost(opusId);//增加分享数
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

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_OPUS_ID).append(opusId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_STYLIST_ID).append(stylistId);
//            param.append("&").append(Constants.WEB_STORE_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_OPUS_ID).append(opusId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_STYLIST_ID).append(stylistId);
//            param.append("?").append(Constants.WEB_STORE_ID).append("");
        }

        return Constants.WEB_WORK_DETAILS + param.toString();
    }

    @Override
    public void findReCodeSuc(ReCodeBean reCode) {
        inviteCode = reCode.getInvitecode();
    }

}
