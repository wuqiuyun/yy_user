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
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.databinding.ItemFilterFilterBinding;
import com.yiyue.user.model.vo.bean.ServerTypeBean;
import com.yiyue.user.model.vo.bean.ServiceSettingBean;
import com.yiyue.user.module.home.service.ServiceTypeAdapter;

import java.util.ArrayList;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/18 16:14
 *  @描述：    筛选的适配器
 */

public class FilterAdapter2 extends BaseRecycleViewAdapter {
    private Context mContext;
    private IOkButtonListener mIOkButtonListener;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private ArrayList<ServiceSettingBean> mServiceSettingBeans = new ArrayList<>();
    private ArrayList<ServerTypeBean> mServerTypeBeans = new ArrayList<>();
    private ArrayList<String> categoryIds  = new ArrayList<>();

    public interface IOkButtonListener {
        void onOkButtonClick(ArrayList<String> categoryIds);
    }

    public void setIOkButtonListener(IOkButtonListener IOkButtonListener) {
        mIOkButtonListener = IOkButtonListener;
    }

    public FilterAdapter2(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_filter, parent, false);
        return new FilterAdapter2.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterViewHolder viewHolder = (FilterViewHolder) holder;
        RecyclerView recyclerView = viewHolder.mBinding.contextRecycle;
        if (mServiceTypeAdapter ==null) mServiceTypeAdapter = new ServiceTypeAdapter(mContext);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(mServiceTypeAdapter);
        if (recyclerView.getItemDecorationCount()<=0){
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));
        }
        mServiceTypeAdapter.setItemListener(new RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                mServiceTypeAdapter.getDatas().get(position).setChecked(!mServiceTypeAdapter.getDatas().get(position).isChecked());
                mServiceTypeAdapter.notifyDataSetChanged();
            }
            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });


        if (mServerTypeBeans!=null&&mServiceSettingBeans.size()==0){
            for (ServerTypeBean serverTypeBean:mServerTypeBeans) {
                mServiceSettingBeans.add(new ServiceSettingBean(serverTypeBean.getName(), false,serverTypeBean.getId()));
            }
            mServiceTypeAdapter.setDatas(mServiceSettingBeans, true);
            mServiceTypeAdapter.notifyDataSetChanged();
        }


        //确定按钮事件监听
        if (null != mIOkButtonListener) {
            viewHolder.mBinding.tvOk.setOnClickListener(v -> {
                categoryIds.clear();
                for (ServiceSettingBean serviceSettingBean: mServiceTypeAdapter.getDatas()) {
                    if (serviceSettingBean.isChecked()){
                        categoryIds.add(String.valueOf(serviceSettingBean.getId()));
                    }
                }
                mIOkButtonListener.onOkButtonClick( categoryIds);
            });
        }
        //重置
        viewHolder.mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ServiceSettingBean serviceSettingBean: mServiceTypeAdapter.getDatas()) {
                    serviceSettingBean.setChecked(false);
                }
                mServiceTypeAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 初始化筛选类型
     * @param serverTypeBeans
     */
    public void setServerTypeBeans(ArrayList<ServerTypeBean> serverTypeBeans) {
        mServerTypeBeans = serverTypeBeans;
        notifyDataSetChanged();
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
