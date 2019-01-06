package com.yiyue.user.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemFilterFilterBinding;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;

import java.util.List;
import java.util.Map;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/18 16:14
 *  @描述：    筛选的适配器
 */

public class FilterAdapter extends BaseRecycleViewAdapter<Object> {
    private Context mContext;
    private FilterContextAdapter mFilterContextAdapter;
    private IOkButtonListener mIOkButtonListener;
    private IOkButtonMultipleSelectionListener mIOkButtonMultipleSelectionListener;
    private boolean mIsMultipleSelection = false;// 是否需要多选

    public interface IOkButtonListener {
        void onOkButtonClick(Map<Integer, String> screenings);
    }

    public interface IOkButtonMultipleSelectionListener {
        void onOkButtonClick(Map<Integer, List<ServiceSettingBean>> screenings);
    }

    public void setIOkButtonListener(IOkButtonListener IOkButtonListener) {
        mIOkButtonListener = IOkButtonListener;
    }

    public void setIOkButtonListener(IOkButtonMultipleSelectionListener iOkButtonMultipleSelectionListener) {
        mIOkButtonMultipleSelectionListener = iOkButtonMultipleSelectionListener;
    }

    public void isMultipleSelection(boolean isMultipleSelection) {
        this.mIsMultipleSelection = isMultipleSelection;
    }

    public FilterAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_filter, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterViewHolder viewHolder = (FilterViewHolder) holder;
        RecyclerView recyclerView = viewHolder.mBinding.contextRecycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mFilterContextAdapter == null) {
            mFilterContextAdapter = new FilterContextAdapter(mContext);
            mFilterContextAdapter.setDatas(mDatas, true);
            mFilterContextAdapter.isMultipleSelection(mIsMultipleSelection);
        }
        recyclerView.setAdapter(mFilterContextAdapter);


        //确定按钮事件监听
        if (null != mIOkButtonListener) {
            viewHolder.mBinding.tvOk.setOnClickListener(v -> {
                mIOkButtonListener.onOkButtonClick(mFilterContextAdapter.getSelects());
            });
        }
        if (null != mIOkButtonMultipleSelectionListener) {
            viewHolder.mBinding.tvOk.setOnClickListener(v -> {
                mIOkButtonMultipleSelectionListener.onOkButtonClick(mFilterContextAdapter.getMultipleSelects());
            });
        }
        //重置
        viewHolder.mBinding.tvReset.setOnClickListener(view -> {
            mFilterContextAdapter.clearItems();
            if (mIsMultipleSelection) {
                mFilterContextAdapter.getMultipleSelects().clear();
            } else {
                mFilterContextAdapter.getSelects().clear();
            }
            mFilterContextAdapter.setDatas(mDatas, true);
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class FilterViewHolder extends BaseViewHolder {
        private ItemFilterFilterBinding mBinding;

        public FilterViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
