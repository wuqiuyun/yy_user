package com.yiyue.user.module.home.service.works;

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
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ItemServiceWorksBinding;
import com.yiyue.user.model.vo.bean.ServiceOpusBean;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.model.vo.bean.StylistRecommendBean;
import com.yiyue.user.module.home.stylist.HomeStylistPageAdapter;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.toast.ToastUtil;

/**
 * 作品(服务)
 * Created by wqy on 2018/11/1.
 */

public class ServiceWorksAdapter extends BaseRecycleViewAdapter<ServiceOpusBean> {

    private LayoutInflater mInflater;
    private Context context;

    public ServiceWorksAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(mInflater.inflate(R.layout.item_service_works, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorksViewHolder viewHolder = (WorksViewHolder) holder;
        ItemServiceWorksBinding mBinding = viewHolder.worksBinding;
        ServiceOpusBean bean = getDatas().get(position);

        // 作品图
        ImageLoader.loadImage(mBinding.ivPhoto, bean.getImgPath());
        //头像
        ImageLoader.loadImage(mBinding.ivAvatar, bean.getHeadImg());
        //作品描述
        mBinding.tvOpusName.setText(bean.getDescribe());
        //美发师名称
        mBinding.tvNick.setText(bean.getStylistName());
        //浏览量
        mBinding.tvLookNum.setText(bean.getPageview() + "");
        if (bean.getIsCollection() == 0) { // 未收藏
            mBinding.ivCollection.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_collection_nor));
        } else {
            mBinding.ivCollection.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_collection_selected));
        }

        mBinding.ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCollectionListener(position, bean);
            }
        });

        mBinding.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(position, bean);
            }
        });

    }

    public class WorksViewHolder extends BaseViewHolder {
        private ItemServiceWorksBinding worksBinding;

        public WorksViewHolder(View itemView) {
            super(itemView);
            worksBinding = DataBindingUtil.bind(itemView);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onCollectionListener(int position, ServiceOpusBean bean);

        void onItemClickListener(int position, ServiceOpusBean bean);
    }
}
