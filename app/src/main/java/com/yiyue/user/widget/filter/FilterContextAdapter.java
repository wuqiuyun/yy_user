package com.yiyue.user.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemFilterContextBinding;
import com.yiyue.user.databinding.ItemFilterHeadBinding;
import com.yiyue.user.model.vo.bean.HairListBean;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yl.core.component.log.DLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/18 20:03
 *  @描述：    筛选内容的适配器
 */

public class FilterContextAdapter extends BaseRecycleViewAdapter<Object> {

    public static final int HEAD = 0;     //标题
    public static final int CHILD = 1;    //内容
    private Context mContext;
    private HashMap<Integer, String> selects = new HashMap<>();
    private HashMap<Integer, List<ServiceSettingBean>> multipleSelections = new HashMap<>();
    private boolean mIsMultipleSelection = false;
    private ArrayList<ArrayList<ServiceSettingBean>> mItems;
    private ArrayList<FilterSubAdapter> filterSubAdapters;

    public FilterContextAdapter(Context context) {
        mItems = new ArrayList<>();
        filterSubAdapters = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mItems.add(new ArrayList<>());
            filterSubAdapters.add(new FilterSubAdapter(context));
        }
        mContext = context;
    }

    public HashMap<Integer, String> getSelects() {
        return selects;
    }

    public HashMap<Integer, List<ServiceSettingBean>> getMultipleSelects() {
        return multipleSelections;
    }

    public void clearItems() {
        mItems.get(1).clear();
        mItems.get(3).clear();
    }

    public void isMultipleSelection(boolean isMultipleSelection) {
        this.mIsMultipleSelection = isMultipleSelection;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);   //获取mInflater对象
        switch (viewType) {  //根据viewtyupe来区分，是标题还是数据项
            case HEAD:  //标题
                HeadViewHolder headViewHolder = new HeadViewHolder(mInflater.inflate(R.layout.item_filter_head, parent, false));
                return headViewHolder;
            case CHILD:
                ChildViewHolder childViewHolder = new ChildViewHolder(mInflater.inflate(R.layout.item_filter_context, parent, false));
                return childViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {  //根据viewtype来区分，是标题还是数据项
            case HEAD:  //标题
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (mIsMultipleSelection) {
                    HairListBean bean = (HairListBean) mDatas.get(position);
                    headViewHolder.mBinding.cbLabel.setText(bean.getName());
                } else {
                    headViewHolder.mBinding.cbLabel.setText((String) mDatas.get(position));
                }
                break;
            case CHILD:
                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                FilterSubAdapter filterSubAdapter = filterSubAdapters.get(position);
                if (mItems.get(position).size() == 0) {
                    filterSubAdapter.setItemListener(new SimpleRecycleViewItemListener() {
                        @Override
                        public void onItemClick(View view, int position2) {
                            ArrayList<ServiceSettingBean> serviceSettingBeans = mItems.get(position);
                            if (mIsMultipleSelection) {
                                if (position2 == 0) {
                                    for (ServiceSettingBean serviceSettingBean : serviceSettingBeans) {
                                        serviceSettingBean.setChecked(false);
                                    }
                                    serviceSettingBeans.get(position2).setChecked(true);
                                    DLog.d(serviceSettingBeans.toString());
                                    mItems.set(position, serviceSettingBeans);
                                    filterSubAdapter.setDatas(serviceSettingBeans, true);
                                    multipleSelections.put(position, serviceSettingBeans);
                                } else {
                                    serviceSettingBeans.get(0).setChecked(false);
                                    boolean isCheck = serviceSettingBeans.get(position2).isChecked();
                                    serviceSettingBeans.get(position2).setChecked(!isCheck);
                                    DLog.d(serviceSettingBeans.toString());
                                    mItems.set(position, serviceSettingBeans);
                                    filterSubAdapter.setDatas(serviceSettingBeans, true);
                                    multipleSelections.put(position, serviceSettingBeans);
                                }
                            } else {
                                for (ServiceSettingBean serviceSettingBean : serviceSettingBeans) {
                                    serviceSettingBean.setChecked(false);
                                }
                                serviceSettingBeans.get(position2).setChecked(true);
                                DLog.d(serviceSettingBeans.toString());
                                mItems.set(position, serviceSettingBeans);
                                filterSubAdapter.setDatas(serviceSettingBeans, true);
                                selects.put(position, serviceSettingBeans.get(position2).getLabel());
                            }
                        }
                    });
                    ArrayList<ServiceSettingBean> tempList = new ArrayList<>();
                    if (mIsMultipleSelection) {
                        ArrayList<HairListBean> subItem = (ArrayList<HairListBean>) mDatas.get(position);
                        for (int i = 0; i < subItem.size(); i++) {
                            tempList.add(new ServiceSettingBean(subItem.get(i).getName(), false, subItem.get(i).getId()));
                        }
                    } else {
                        ArrayList<String> subItem = (ArrayList<String>) mDatas.get(position);
                        for (int i = 0; i < subItem.size(); i++) {
                            tempList.add(new ServiceSettingBean(subItem.get(i), false));
                        }
                    }
                    mItems.set(position, tempList);
                    filterSubAdapter.setDatas(tempList, true);
                } else {
                    filterSubAdapter.setDatas(mItems.get(position), true);
                }
                childViewHolder.mBinding.rvType.setLayoutManager(new GridLayoutManager(mContext, 3));
                childViewHolder.mBinding.rvType.setAdapter(filterSubAdapter);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class HeadViewHolder extends BaseViewHolder {

        private ItemFilterHeadBinding mBinding;

        public HeadViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class ChildViewHolder extends BaseViewHolder {

        private ItemFilterContextBinding mBinding;

        public ChildViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
