package com.yiyue.user.module.mine.stylist.project;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemServiceContentBinding;
import com.yiyue.user.model.vo.bean.ProjectBean;
import com.yiyue.user.util.FormatUtil;



/*
    服务内容适配器
 * Create by lvlong on  2018/10/23
 */

public class ServiceContenAdapter extends BaseRecycleViewAdapter<ProjectBean> {

    private Context mContext;
    private int type;

    public ServiceContenAdapter(Context context,int type) {
        mContext = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (type==0){
             view = LayoutInflater.from(mContext).inflate(R.layout.item_service_content, parent, false);
        }else {
             view = LayoutInflater.from(mContext).inflate(R.layout.item_service_content2, parent, false);
        }
            return new ServiceContenViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceContenViewHolder viewHolder = (ServiceContenViewHolder) holder;
        ProjectBean bean = mDatas.get(position);
        viewHolder.mCheckBox.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mCheckBox.setChecked(bean.isChecked());
    }
    public class ServiceContenViewHolder extends BaseViewHolder{
        private  CheckBox mCheckBox;
        public ServiceContenViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.cb_label);
        }
    }
}
