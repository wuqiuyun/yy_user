package com.yiyue.user.helper;

import android.content.Intent;
import android.text.TextUtils;

import com.hyphenate.EMCallBack;
import com.yiyue.user.YLApplication;
import com.yiyue.user.api.UserApi;
import com.yiyue.user.base.data.BaseResponse;
import com.yiyue.user.component.greendao.DaoManager;
import com.yiyue.user.component.net.TokenManager;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.component.push.AliPushManager;
import com.yiyue.user.model.local.preferences.AccountSharedPreferences;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.util.easyutils.EasyHelper;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;

/**
 * 用户管理器
 * <p>
 * Created by zm on 2018/10/15.
 */
public class AccountManager {

    private static AccountManager sInstance;

    private AccountManager() {
    }

    private UserBean mUserBean;

    public synchronized static AccountManager getInstance() {
        if (sInstance == null) {
            sInstance = new AccountManager();
        }
        return sInstance;
    }

    public boolean initAccount() {
        UserBean userBean = AccountSharedPreferences.getInstance().getAccountData();
        return updateAccount(userBean, false);
    }

    /**
     * 更新用户信息
     *
     * @param userBean
     * @return
     */
    public boolean updateAccount(UserBean userBean) {
        return updateAccount(userBean, true);
    }

    /**
     * 更新账户信息
     *
     * @param userBean
     * @param isUpdateLocal 是否同时更新持久化
     * @return
     */
    public boolean updateAccount(UserBean userBean, boolean isUpdateLocal) {
        if (userBean == null) {
            return false;
        }
        if (mUserBean == null) {
            mUserBean = userBean;
        } else {
            // 修改内存
            mUserBean = mUserBean.updateSelf(userBean);
        }
        // 修改本地
        if (isUpdateLocal) {
            AccountSharedPreferences.getInstance().updateDataBase(mUserBean);
        }
        return true;
    }

    public UserBean getAccount() {
        if (mUserBean == null || TextUtils.isEmpty(mUserBean.getMobile())) {
            initAccount();
        }
        if (mUserBean == null) {
            return null;
        }
        UserBean userBean = null;
        try {
            userBean = mUserBean.clone();
        } catch (CloneNotSupportedException e) {
            DLog.e(e, e.getMessage());
        }
        return userBean;
    }

    public void setUserStatus(int userStatus) {
        if (null == mUserBean) {
            initAccount();
        }
        if (mUserBean != null) {
            mUserBean.setUserStatus(userStatus);
            updateAccount(mUserBean, true);
        }
    }

    public void setUserShutdown(int shutdown) {
        if (null == mUserBean) {
            initAccount();
        }
        if (mUserBean != null) {
            mUserBean.setShutdown(shutdown);
            updateAccount(mUserBean, true);
        }
    }

    /**
     * 更新本地user的头像
     *
     * @param headImg
     */
    public void setHeadImg(String headImg) {
        if (null == mUserBean) {
            initAccount();
        }
        if (mUserBean != null) {
            mUserBean.setHeadImg(headImg);
            updateAccount(mUserBean, true);
        }
    }

    /**
     * 更新昵称
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        if (null == mUserBean) {
            initAccount();
        }
        if (mUserBean != null) {
            mUserBean.setNickname(nickname);
            updateAccount(mUserBean, true);
        }
    }

    public String getMobile() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? "" : cloneUser.getMobile();
    }

    public String getUserId() {
        UserBean cloneUser = getAccount();
        String userId;
        //未登录时，userId默认为1
        if (!AccountManager.getInstance().isLogin()) {
            userId = "1";
        } else {
            userId = cloneUser == null ? "" : cloneUser.getId();
        }
        return userId;
    }

    public String getUsername() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? "" : cloneUser.getUsername();
    }

    public String getNickname() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? "" : cloneUser.getNickname();
    }

    public int getUserStatus() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? 1 : cloneUser.getUserStatus();
    }

    public String getUserHeadImg() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? "" : cloneUser.getHeadImg();
    }

    public String getIdNo() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? "" : cloneUser.getIdNo();
    }

    public int getUserShutdown() {
        UserBean cloneUser = getAccount();
        return cloneUser == null ? 0 : cloneUser.getShutdown();
    }

    /**
     * token 存在
     * phone 存在
     *
     * @return true -> logined false unlogin
     */
    public boolean isLogin() {
        // 手机为空 -> 判断未登陆
        if (TextUtils.isEmpty(getMobile())) {
            return false;
        }

        if (TextUtils.isEmpty(TokenManager.getToken())) {
            return false;
        }

        if (getUserStatus() != 0) {
            return false;
        }
        return true;
    }

    boolean logoutFlag = false;

    public void setLogoutFlag(boolean logoutflag) {
        this.logoutFlag = logoutflag;
    }

    /**
     * 权限失效登出
     */
    public void logout() {
        if (logoutFlag || !AccountManager.getInstance().isLogin()) {
            return;
        }
        logoutFlag = true;
        Intent intent = new Intent();
        intent.setClass(YLApplication.getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        YLApplication.getContext().startActivity(intent);
        clear();
    }

    /**
     * 单点登录登出
     */
    public void insertingLogout() {
        if (!isLogin()) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(YLApplication.getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        YLApplication.getContext().startActivity(intent);
        EasyHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
            }
        });
        new UserApi().logout(new YLRxSubscriberHelper<BaseResponse>() {

            @Override
            public void _onNext(BaseResponse baseResponse) {
                clear();
            }

            @Override
            public void onError(Throwable throwable) {
                clear();
            }

            @Override
            protected void onShowMessage(ApiException apiException) {
                clear();
            }

            @Override
            protected void onPermissionError(ApiException apiException) {
                clear();
            }
        });
    }

    /**
     * 登出后清除数据
     */
    public void clear() {
        new DaoManager().dropTables(YLApplication.getContext(), AccountManager.getInstance().getUserId(), false);
        AliPushManager.getInstance().unbindAccount();//解绑阿里云推送账号
        TokenManager.clearToken();
        AccountSharedPreferences.getInstance().clearAccountData();
        mUserBean = null;
    }
}
