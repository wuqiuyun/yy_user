package com.yiyue.user.module.mine.stylist;

import android.annotation.SuppressLint;
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
import com.yiyue.user.databinding.ItemStylistMineBinding;
import com.yiyue.user.model.vo.bean.StylistBean;
import com.yiyue.user.util.FormatKmUtil;
import com.yiyue.user.util.FormatUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;


/**
 * Created by Lizhuo on 2018/10/23.
 */
public class StylistMineAdapter extends BaseRecycleViewAdapter<StylistBean> {
    private Context context;

    public StylistMineAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistMineAdapter.StylistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist_mine, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistMineAdapter.StylistViewHolder viewHolder = (StylistMineAdapter.StylistViewHolder) holder;
        StylistBean item = getDatas().get(position);
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
        viewHolder.stylistBinding.ratingBar.setRating(item.getStar() != 0.0 ? item.getStar() : item.getGrade());
        viewHolder.stylistBinding.tvGrade.setText(item.getStar() != 0.0 ? item.getStar() + "分" : item.getGrade() + "分 ");

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setAsBitmap(true)
                .setPlaceHolderResId(R.drawable.meizi)
                .setErrorResId(R.drawable.meizi)
                .build();
        ImageLoader.loadImage(viewHolder.stylistBinding.ivPhoto, item.getHeadPortrait() != null ? item.getHeadPortrait() : item.getStylistCover(), config, null);
        viewHolder.stylistBinding.tvName.setText(FormatUtil.Formatstring(item.getStylistName() != null ? item.getStylistName() : item.getNickname()));
        String mPosition = item.getPosition() != null ? item.getPosition() : item.getProfessor();
        if (!TextUtils.isEmpty(mPosition) && mPosition.length() >= 2) {
            viewHolder.stylistBinding.tvProfessor.setText(mPosition.substring(0, 2));
        } else {
            viewHolder.stylistBinding.tvProfessor.setText("");
        }
        int count = item.getReceiptCount() != 0 ? item.getReceiptCount() : item.getMonthOrder();
        viewHolder.stylistBinding.tvCount.setText(String.format(context.getString(R.string.stylist_receipt_count), count));
        viewHolder.stylistBinding.tvMaxSalesItem.setText(FormatUtil.Formatstring(item.getMaxSalesItem() != null ? item.getMaxSalesItem() : item.getLable()));

        if (item.getDistance() != null) {
            double d = Double.valueOf(item.getDistance());
            viewHolder.stylistBinding.trLocationDistance.setVisibility(View.VISIBLE);
            viewHolder.stylistBinding.tvLocationDistance.setText(FormatKmUtil.FormatKmStr(d));
        }
    }

    public class StylistViewHolder extends BaseViewHolder {
        private ItemStylistMineBinding stylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            stylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
