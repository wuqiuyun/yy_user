package com.yiyue.user.module.mine.works;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemWorksBinding;
import com.yiyue.user.model.vo.bean.WorksBean;
import com.yl.core.component.image.ImageLoader;

/**
 * Created by zm on 2018/10/10.
 */
public class WorksAdapter extends BaseRecycleViewAdapter<WorksBean>{
    private Context context;

    public WorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorksViewHolder worksViewHolder= (WorksViewHolder) holder;
        String opusPath = mDatas.get(position).getOpusPath();
        ImageLoader.loadImage(worksViewHolder.mItemWorksBinding.ivWorks,opusPath);
    }

    public class WorksViewHolder extends BaseViewHolder {

        private final ItemWorksBinding mItemWorksBinding;

        public WorksViewHolder(View itemView) {
            super(itemView);
            mItemWorksBinding = DataBindingUtil.bind(itemView);
        }
    }
}
