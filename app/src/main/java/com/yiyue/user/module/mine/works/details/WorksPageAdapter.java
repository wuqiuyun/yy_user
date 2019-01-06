package com.yiyue.user.module.mine.works.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyue.user.R;
import com.yiyue.user.databinding.ItemWorksListBinding;
import com.yiyue.user.model.vo.bean.OpusDetailsBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/12.
 */
public class WorksPageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<OpusDetailsBean.PictruesBean> pictruesBeans;

    public WorksPageAdapter(Context context, ArrayList<OpusDetailsBean.PictruesBean> pictruesBeans) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.pictruesBeans = pictruesBeans;
    }

    @Override
    public int getCount() {
        return pictruesBeans.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_works_list, container, false);
        ItemWorksListBinding binding = DataBindingUtil.bind(view);
        ImageLoader.loadImage(binding.ivWorks, pictruesBeans.get(position).getPath());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
