package com.yiyue.user.module.home.stylist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ItemHomeStylistBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.StylistRecommendBean;
import com.yiyue.user.module.mine.stylist.details.StylistDetailsActivity;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

import java.util.ArrayList;

import static com.yiyue.user.model.constant.Constants.STYLISTId;

/**
 * 首页推荐美发师适配器
 * Created by wqy on 2018/10/31.
 */

public class HomeStylistPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<StylistRecommendBean> mStylistBeans;
    private ItemHomeStylistBinding mBinding;

    public HomeStylistPageAdapter(Context context, ArrayList<StylistRecommendBean> list) {
        this.context = context;
        this.mStylistBeans = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mStylistBeans.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void update() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        StylistRecommendBean stylistRecommendBean = mStylistBeans.get(position);
        View view = mInflater.inflate(R.layout.item_home_stylist, container, false);
        mBinding = DataBindingUtil.bind(view);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .build();
        ImageLoader.loadImage(mBinding.ivAvatar, stylistRecommendBean.getHeadportrait(), config, null);
        mBinding.tvName.setText(stylistRecommendBean.getNickname());
        mBinding.tvInfo.setText(stylistRecommendBean.getLable());
        mBinding.tvServiceType.setText(stylistRecommendBean.getServerType());
        if (stylistRecommendBean.isCollection()) {
            mBinding.tvAttention.setText("已关注");
        } else {
            mBinding.tvAttention.setText("+ 关注");
        }

        // 关注
        mBinding.tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    if (AccountManager.getInstance().isLogin()) {
                        listener.IsAttentionListener(position, stylistRecommendBean);// type  1:关注 0:取消关注
                    } else {
                        ToastUtils.shortToast("先登录才能关注哦");
                    }
                }
            }
        });

        // 聊天/打招呼
        mBinding.tvStylistChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    if (AccountManager.getInstance().isLogin()) {
                        listener.StylistChatListener(position, stylistRecommendBean);
                    } else {
                        ToastUtils.shortToast("先登录才能聊天哦");
                    }
                }
            }
        });

        // 他的服务
        mBinding.tvStylistService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.StylistServiceListener(position, stylistRecommendBean);
                }
            }
        });

        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ItemClickListener(position, stylistRecommendBean);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void IsAttentionListener(int position, StylistRecommendBean stylistRecommendBean);

        void StylistChatListener(int position, StylistRecommendBean stylistRecommendBean);

        void StylistServiceListener(int position, StylistRecommendBean stylistRecommendBean);

        void ItemClickListener(int position, StylistRecommendBean stylistRecommendBean);
    }
}
