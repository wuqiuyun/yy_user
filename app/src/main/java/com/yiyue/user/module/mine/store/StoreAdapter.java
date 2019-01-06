package com.yiyue.user.module.mine.store;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;

import com.yiyue.user.databinding.ItemHomeStoreBinding;
import com.yiyue.user.model.vo.bean.StoreBean;
import com.yiyue.user.util.FormatKmUtil;
import com.yl.core.component.image.ImageLoader;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public class StoreAdapter extends BaseRecycleViewAdapter<StoreBean> {
    private Context context;
    private int type;

    public StoreAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        StoreViewHolder holder = (StoreViewHolder) viewHolder;
        StoreBean item = getDatas().get(position);
        ArrayList<String> list = item.getServes();
        ArrayList<String> categorys = item.getCategorys();

        String serves;
        if (null != list && list.size() > 0) {
            serves = getServesType(list);
        } else {
            serves = getServesType(categorys);
        }
        ImageLoader.loadImage(holder.storeBinding.ivStore, item.getStoreCover());
        holder.storeBinding.tvName.setText(item.getStoreName());
        holder.storeBinding.tvLocationDistance.setText(FormatKmUtil.FormatKmStr(Double.valueOf(item.getDistance())));
        holder.storeBinding.tvAddress.setText(item.getLocation());
        holder.storeBinding.tvServiceType.setText(serves);
        holder.storeBinding.tvGrade.setText(String.valueOf(item.getGrade()));
        holder.storeBinding.ratingBar.setRating((float) item.getGrade());
        holder.storeBinding.ratingBar.setEnabled(false);
        holder.storeBinding.tvOrderNumber.setText(String.format(context.getString(R.string.month_order_number), item.getMonthServer()));
    }

    public class StoreViewHolder extends BaseViewHolder {
        private ItemHomeStoreBinding storeBinding;

        public StoreViewHolder(View itemView) {
            super(itemView);
            storeBinding = DataBindingUtil.bind(itemView);
        }
    }

    private String getServesType(List<String> list) {
        StringBuffer sb = new StringBuffer();
        if (null == list || list.size() == 0) {
            return "暂无";
        } else {
            for (String serve : list) {
                sb.append(serve).append("、");
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
    }
}
