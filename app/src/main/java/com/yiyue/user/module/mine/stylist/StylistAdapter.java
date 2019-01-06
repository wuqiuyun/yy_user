package com.yiyue.user.module.mine.stylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemStylistBinding;
import com.yiyue.user.model.vo.bean.StylistBean;

/**
 * Created by zm on 2018/10/10.
 */
public class StylistAdapter extends BaseRecycleViewAdapter<StylistBean>{
    private Context context;

    public StylistAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistViewHolder viewHolder = (StylistViewHolder) holder;
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
    }

    public class StylistViewHolder extends BaseViewHolder {
        private ItemStylistBinding stylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            stylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
