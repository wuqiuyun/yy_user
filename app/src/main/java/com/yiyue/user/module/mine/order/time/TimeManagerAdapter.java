package com.yiyue.user.module.mine.order.time;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.model.vo.bean.TimeBean;
import com.yiyue.user.util.ColorUtil;

/**
 * Created by zm on 2018/11/12.
 */
public class TimeManagerAdapter extends BaseRecycleViewAdapter<TimeBean>{
    private LayoutInflater mInflater;
    private Context mContext;

    public TimeManagerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeManagerViewHolder(mInflater.inflate(R.layout.item_time_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TimeManagerViewHolder viewHolder = (TimeManagerViewHolder) holder;
        TimeBean timeBean = mDatas.get(position);

        if (timeBean.isLock()) {
            viewHolder.cb_time.setText(timeBean.getTime()+"\n"+"已预约");
            viewHolder.cb_time.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_bg_efefef));
            viewHolder.cb_time.setTextColor(ColorUtil.getColor(R.color.dark_gray));
        }else {
            viewHolder.cb_time.setText(timeBean.getTime());
            viewHolder.cb_time.setBackground(ContextCompat.getDrawable(mContext, R.drawable.text_bg_white_selector));
            viewHolder.cb_time.setTextColor(ColorUtil.getColor(timeBean.isChecked() ? R.color.white : R.color.color_343434));
        }
        viewHolder.cb_time.setChecked(timeBean.isChecked());
        viewHolder.cb_time.setEnabled(!timeBean.isLock());
    }

    public class TimeManagerViewHolder extends BaseViewHolder {
        private CheckBox cb_time;

        public TimeManagerViewHolder(View itemView) {
            super(itemView);
            cb_time = itemView.findViewById(R.id.cb_time);
        }
    }

    public String getSelectTime() {
        for (TimeBean timeBean : mDatas) {
            if (timeBean.isChecked()) {
                return timeBean.getTime();
            }
        }
        return "";
    }
}
