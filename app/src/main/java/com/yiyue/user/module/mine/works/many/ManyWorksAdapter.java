package com.yiyue.user.module.mine.works.many;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.databinding.ItemWorksBinding;
import com.yiyue.user.model.vo.bean.OpusBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

/**
 * Created by zm on 2018/10/10.
 */
public class ManyWorksAdapter extends BaseRecycleViewAdapter<OpusBean.OpusListBean> {
    private Context context;

    public ManyWorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemWorksBinding binding = ((WorksViewHolder) holder).worksBinding;
        OpusBean.OpusListBean bean = getDatas().get(position);
        if (!TextUtils.isEmpty(bean.getStylistOpusCovers())){
            ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                    .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                    .setAsBitmap(true)
                    .setPlaceHolderResId(R.drawable.meizi)
                    .setErrorResId(R.drawable.meizi)
                    .build();
            ImageLoader.loadImage(binding.ivWorks,  bean.getStylistOpusCovers(), config, null);
        }
    }

    public class WorksViewHolder extends BaseViewHolder {
        ItemWorksBinding worksBinding;

        public WorksViewHolder(View itemView) {
            super(itemView);
            worksBinding = DataBindingUtil.bind(itemView);
        }
    }
}
