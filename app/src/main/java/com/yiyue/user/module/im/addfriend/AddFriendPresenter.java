package com.yiyue.user.module.im.addfriend;

import android.text.TextUtils;

import com.yiyue.user.api.ContactsApi;
import com.yiyue.user.base.mvp.BasePresenter;
import com.yiyue.user.component.net.YLRxSubscriberHelper;
import com.yiyue.user.model.vo.result.UserFriendsResult;

/**
 * Created by zhangzz on 2018/9/27
 */
public class AddFriendPresenter extends BasePresenter<AddFriendView> {
    /**
     * 搜索好友
     *
     * @param param
     */
    public void searchUser(String param) {
        if (TextUtils.isEmpty(param)) {
            getMvpView().showToast("搜索内容不能为空");
            return;
        }

        requestAddFriend(param);
    }

    /**
     * 搜索请求
     */
    private void requestAddFriend(String param) {
        new ContactsApi().requestSearchUser(param, new YLRxSubscriberHelper<UserFriendsResult>() {
            @Override
            public void _onNext(UserFriendsResult result) {
                getMvpView().onSearchUserSuccess(result.getData());
            }
        });
    }
}
