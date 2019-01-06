package com.yiyue.user.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemSynthesisBinding;





/*
 *  附近
 */

public class NearbySubAdapter extends BaseRecycleViewAdapter<String> {

    private Context mContext;
    private int mType;
    private int tempPosition;
    public NearbySubAdapter(Context context, int type) {
        mContext = context;
        mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mType==0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_synthesis, parent, false);
           return new NearbySubViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nearby_sub, parent, false);
            return new NearbySubViewHolder(view);
        }
    }

    public void setTempPosition(int tempPosition) {
        this.tempPosition = tempPosition;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            NearbySubViewHolder viewHolder = (NearbySubViewHolder) holder;
            viewHolder.tv_type.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NearbySubViewHolder extends BaseViewHolder {
        private TextView tv_type;

        public NearbySubViewHolder(View itemView) {
            super(itemView);
              tv_type=itemView.findViewById(R.id.tv_type);

        }
    }
}
