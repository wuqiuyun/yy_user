package com.yiyue.user.model.local.preferences;

import com.yiyue.user.YLApplication;
import com.yiyue.user.model.vo.bean.BasicDataBean;

/**
 * Created by zm on 2018/10/23.
 */
public class CommonSharedPreferences {

    private static CommonSharedPreferences sInstance;

    private SharedPfUtils mSharedPfUtils;
    // 基础信息
    private static final String BASIC_DATA = "BASIC_DATA";
    // 领取礼包弹窗
    private static final String SHOW_REWARD = "show_reward";
    // 邀请码
    private static final String INVITATION_CODE = "invitation_code";

    private CommonSharedPreferences() {
        mSharedPfUtils = new SharedPfUtils(YLApplication.getContext());
    }


    public static synchronized CommonSharedPreferences getInstance() {
        if (sInstance == null) {
            sInstance = new CommonSharedPreferences();
        }
        return sInstance;
    }

    /**
     * 保存基本信息
     *
     * @param bsicDataBean
     */
    public void saveBasicData(BasicDataBean bsicDataBean) {
        mSharedPfUtils.saveObject(BASIC_DATA, bsicDataBean);
    }

    public BasicDataBean getBasicDataBean() {
        return mSharedPfUtils.getObject(BASIC_DATA, BasicDataBean.class);
    }

    /**
     * 领取礼包弹窗
     *
     * @param isShow
     */
    public void saveShowReward(boolean isShow) {
        mSharedPfUtils.saveBoolean(SHOW_REWARD, isShow);
    }

    public boolean getShowReward() {
        return mSharedPfUtils.getBoolean(SHOW_REWARD, false);
    }


    /**
     * 邀请码
     */
    public void saveInvitationCode(String code) {
        mSharedPfUtils.saveString(INVITATION_CODE,code);
    }

    public String getInvitationCode() {
        return mSharedPfUtils.getString(INVITATION_CODE, "");
    }
}
