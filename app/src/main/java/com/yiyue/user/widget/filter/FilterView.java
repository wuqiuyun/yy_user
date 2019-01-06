package com.yiyue.user.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ItemFilterBinding;
import com.yiyue.user.util.FormatUtil;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

/**
 * 筛选
 * <p>
 * Created by zm on 2018/10/12.
 */
public class FilterView extends LinearLayout implements ClickHandler {
    private ItemFilterBinding mFilterBinding;
    private Context mContext;
    private ArrayList<TextView> mTextViews;
    private int tempPosition = -1;
    private boolean isShow = true;
    public static final int NEARBY = 0;
    public static final int SORT = 1;
    public static final int FILTER = 2;
    private ArrayList<BaseRecycleViewAdapter> adapters;

    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFilterBinding = DataBindingUtil.inflate(inflater, R.layout.item_filter, this, true);
        mTextViews = new ArrayList<>();
        mTextViews.add(mFilterBinding.tvNearby);
        mTextViews.add(mFilterBinding.tvSort);
        mTextViews.add(mFilterBinding.tvFilter);

        CustomLinearLayoutManager mCustomLinearLayoutManager = new CustomLinearLayoutManager(mContext);
//                禁止滑动
        mCustomLinearLayoutManager.setScrollEnabled(false);
        mFilterBinding.rvFilterCondition.setLayoutManager(mCustomLinearLayoutManager);

        mFilterBinding.setClick(this);
    }

    // 隐藏筛选条件
    public void setFilterVisibility(int type) {
        if (type == 0) {
            mFilterBinding.llNearby.setVisibility(GONE);
        } else if (type == 1) {
            mFilterBinding.llSort.setVisibility(GONE);
        } else if (type == 3) {
            mFilterBinding.llFilter.setVisibility(GONE);
        }
    }

    private void setDrawableRight(TextView textView, @NotNull Drawable drawableRight) {
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    public void setAdapters(ArrayList<BaseRecycleViewAdapter> adapters) {
        this.adapters = adapters;
    }

    public ArrayList<BaseRecycleViewAdapter> getAdapters() {
        return adapters;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nearby: // 附近
                FilterNearbyAdapter filterNearbyAdapter = (FilterNearbyAdapter) adapters.get(NEARBY);
                if (filterNearbyAdapter.getAreaList() != null && filterNearbyAdapter.getAreaList().size() != 0) {
                    mFilterBinding.rvFilterCondition.setAdapter(adapters.get(NEARBY));
                    showView(NEARBY);
                } else {
                    ToastUtils.shortToast("区域加载异常");
                }
                break;
            case R.id.tv_sort: // 综合排序
                if (adapters.get(SORT) != null && adapters.get(SORT).getDatas().size() != 0) {
                    mFilterBinding.rvFilterCondition.setAdapter(adapters.get(SORT));
                    showView(SORT);
                }
                break;
            case R.id.tv_filter: // 筛选
                if (adapters.get(FILTER) != null) {
                    mFilterBinding.rvFilterCondition.setAdapter(adapters.get(FILTER));
                    showView(FILTER);
                }
                break;
        }
    }

    public int getTempPosition() {
        return tempPosition;
    }

    public void showView(int p) {
        for (int i = 0; i < mTextViews.size(); i++) {
            if (p == i) {
                if (tempPosition == p) {
                    isShow = !isShow;
                } else {
                    isShow = true;
                }
                if (isShow) {
//                show
                    if (mIFilterViewCallBack != null) mIFilterViewCallBack.isHide(true);
                    mFilterBinding.rvFilterCondition.setVisibility(VISIBLE);
                    setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_up));
                } else {
//                hide
                    if (mIFilterViewCallBack != null) mIFilterViewCallBack.isHide(false);
                    mFilterBinding.rvFilterCondition.setVisibility(GONE);
                    setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_down));
                }
            } else {
                setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_down));
            }
        }
        tempPosition = p;
    }

    public void setIFilterViewCallBack(IFilterViewCallBack IFilterViewCallBack) {
        mIFilterViewCallBack = IFilterViewCallBack;
    }

    private IFilterViewCallBack mIFilterViewCallBack;

    public interface IFilterViewCallBack {
        void isHide(boolean b);
    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }

    /**
     * 附近名称
     * @param nearbyText
     */
    public void setNearbyText(String nearbyText) {
        if (nearbyText==null||!TextUtils.isEmpty(nearbyText))return;
        mFilterBinding.tvNearby.setText(nearbyText);
    }

    /**
     * 排序名称
     * @param sortText
     */
    public void setSortText(String sortText) {
        if (sortText==null||TextUtils.isEmpty(sortText)) return;
        mFilterBinding.tvSort.setText(sortText);
    }

}
