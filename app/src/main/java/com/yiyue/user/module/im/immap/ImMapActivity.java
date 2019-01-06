package com.yiyue.user.module.im.immap;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseAppCompatActivity;
import com.yiyue.user.util.FragmentManagerUtil;
import com.yl.core.util.StatusBarUtil;

/**
 * Created by zhangzz on 2018/9/30
 * 好友资料页面
 */

public class ImMapActivity extends BaseAppCompatActivity {
    FragmentManagerUtil fragmentManagerUtil;

    ImMapFragment imMapFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_friend_info;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        fragmentManagerUtil = new FragmentManagerUtil(this, R.id.layout_frame);
        imMapFragment = new ImMapFragment();
        imMapFragment.setArguments(getIntent().getExtras());

        fragmentManagerUtil.chAddFrag(imMapFragment, "", false);
    }

}
