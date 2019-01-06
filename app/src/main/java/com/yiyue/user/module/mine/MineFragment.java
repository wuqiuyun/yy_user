package com.yiyue.user.module.mine;

import android.support.v4.app.Fragment;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.fragment.BaseMvpFragment;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.FragmentMeBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.MineCouponStatus;
import com.yiyue.user.model.constant.OrderStatus;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.collect.CollectActivity;
import com.yiyue.user.module.mine.coupon.CouponActivity;
import com.yiyue.user.module.mine.info.UserInfoActivity;
import com.yiyue.user.module.mine.order.OrderCenterActivity;
import com.yiyue.user.module.mine.pplarz.PopularizeActivity;
import com.yiyue.user.module.mine.settings.SettingsActivity;
import com.yiyue.user.module.mine.wallet.WalletActivity;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;

/**
 * 我的
 * <p>
 * Created by zm on 2018/9/19.
 */
@CreatePresenter(MinePresenter.class)
public class MineFragment extends BaseMvpFragment<IMineView, MinePresenter> implements IMineView, ClickHandler {

    FragmentMeBinding mineBinding;
    private ImageLoaderConfig config;

    public static Fragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        mineBinding = (FragmentMeBinding) viewDataBinding;
        mineBinding.setClick(this);
        mineBinding.titleView.setRightTextClickListener(v -> {
            if (AccountManager.getInstance().isLogin()) {
                SettingsActivity.startActivity(getContext(), SettingsActivity.class);
            } else {
                LoginActivity.startActivity(getActivity(), LoginActivity.class);
            }
        });

        config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().isLogin()) {
            mineBinding.tvUserName.setText(AccountManager.getInstance().getNickname());
            mineBinding.tvUserId.setText("ID:" + AccountManager.getInstance().getIdNo());
            ImageLoader.loadImage(mineBinding.ivPhoto, AccountManager.getInstance().getAccount().getHeadImg(), config, null);
            mineBinding.tvUserId.setVisibility(View.VISIBLE);
        } else {
            mineBinding.tvUserName.setText("登录");
            mineBinding.tvUserId.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onClick(View view) {
        if (!AccountManager.getInstance().isLogin()) {
            if (view.getId() == R.id.btn_user_info) {
                LoginActivity.startActivity(getActivity(), LoginActivity.class);
                return;
            } else {
                ToastUtils.shortToast("请先登录");
                return;
            }
        }

        switch (view.getId()) {
            case R.id.mine_wallet: // 我的钱包
                WalletActivity.startActivity(getContext(), WalletActivity.class);
                break;
            case R.id.mine_order: // 服务订单
                OrderCenterActivity.startActivity(getContext(), OrderStatus.ORDER_PAYMENT);
                break;
            case R.id.mine_coupon: // 我的优惠券
                CouponActivity.startActivity(getContext(), MineCouponStatus.COUPON_FULL_REDUCTION);
                break;
            case R.id.mine_collect: // 我的收藏
                CollectActivity.startActivity(getContext(), CollectActivity.class);
                break;
            case R.id.mine_recommend: // 推荐用户
                PopularizeActivity.startActivity(getContext(), PopularizeActivity.class);
                break;
            case R.id.btn_user_info: // 个人资料
                UserInfoActivity.startActivity(getContext(), UserInfoActivity.class);
                break;
        }
    }
}
