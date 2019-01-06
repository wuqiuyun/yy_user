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
import com.yiyue.user.databinding.ItemFilterNearbyBinding;
import com.yiyue.user.model.vo.bean.AreaBean;
import com.yiyue.user.model.vo.bean.SortBean;
import com.yl.core.component.log.DLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *  @描述：    附近的适配器
 */

public class FilterNearbyAdapter extends BaseRecycleViewAdapter {
    private Context mContext;
    private NearbySubAdapter mNearbySubAdapter;
    private List<AreaBean> areaList;
    private ArrayList<String> areaNames;
    private int getIdType;

    public FilterNearbyAdapter(Context context,int getIdType) {
        mContext = context;
        this.getIdType = getIdType;
    }

    @NonNull
    @Override
    public FilterNearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_nearby, parent, false);
        return new FilterNearbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterNearbyViewHolder filterNearbyViewHolder = (FilterNearbyViewHolder) holder;
        filterNearbyViewHolder.mBinding.rvParentMenu.setLayoutManager(new LinearLayoutManager(mContext));

        if (mNearbySubAdapter == null) {
            areaNames = new ArrayList<>();
            for (AreaBean areaBean : areaList) {
                areaNames.add(areaBean.getName());
            }
            mNearbySubAdapter = new NearbySubAdapter(mContext, 1);
            mNearbySubAdapter.setDatas(areaNames, true);
            mNearbySubAdapter.setItemListener(new RecycleViewItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mINearbySelectListener != null) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                if (getIdType==0){
                                    hashMap.put("cityId", String.valueOf(areaList.get(position).getAreaId()));
                                }else {
                                    hashMap.put("cityId", String.valueOf(areaList.get(position).getId()));
                                }
                                mINearbySelectListener.callBack(hashMap);
                                break;
                            default:
                                if (getIdType==0){
                                    hashMap.put("districtId", String.valueOf(areaList.get(position).getAreaId()));
                                }else {
                                    hashMap.put("districtId", String.valueOf(areaList.get(position).getId()));
                                }
                                mINearbySelectListener.callBack(hashMap);
                                break;
                        }
                    }
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {
                }
            });
        }
        filterNearbyViewHolder.mBinding.rvParentMenu.setAdapter(mNearbySubAdapter);
    }

    public void setAreaList(List<AreaBean> areaList) {
        this.areaList = areaList;
        notifyDataSetChanged();
    }

    public List<AreaBean> getAreaList() {
        return areaList;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FilterNearbyViewHolder extends BaseViewHolder {
        private ItemFilterNearbyBinding mBinding;

        public FilterNearbyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rvSubtMenu.setLayoutManager(new LinearLayoutManager(mContext));
            ArrayList<String> distances = new ArrayList<>();
            distances.add("0.5km");
            distances.add("1km");
            distances.add("2km");
            distances.add("3km");
            distances.add("4km");
            distances.add("5km");
            NearbySubAdapter nearbySubAdapter = new NearbySubAdapter(mContext, 0);
            nearbySubAdapter.setItemListener(new RecycleViewItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mINearbySelectListener != null) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("distance", distances.get(position).replace("km", ""));
                        DLog.e("distance : " + distances.get(position).replace("km", ""));
                        mINearbySelectListener.callBack(hashMap);
                    }
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            mBinding.rvSubtMenu.setAdapter(nearbySubAdapter);
            nearbySubAdapter.setDatas(distances, true);
        }
    }

    private INearbySelectListener mINearbySelectListener;

    public interface INearbySelectListener {
        void callBack(Map<String, String> screenings);
    }

    public void setINearbySelectListener(INearbySelectListener INearbySelectListener) {
        mINearbySelectListener = INearbySelectListener;
    }
}
