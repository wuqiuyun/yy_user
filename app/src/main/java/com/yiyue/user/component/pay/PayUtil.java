package com.yiyue.user.component.pay;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yiyue.user.R;
import com.yiyue.user.YLApplication;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.model.constant.Constants;

/**
 * Created by zm on 2018/10/24.
 */

public class PayUtil {

    /**
     * 是否支持微信支付
     * @return
     */
    public static boolean isCanPayWithWechat() {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(YLApplication.getContext(), Constants.WEIXIN_APPID);
        return wxApi.isWXAppInstalled() && wxApi.isWXAppSupportAPI();
    }

    /**
     * 检测是否支持微信支付，不支持弹窗提醒，支持返回true
     * @return
     */
    public static boolean checkCanPayWithWechat() {
        if(isCanPayWithWechat()) {
            return true;
        }
        ToastUtils.shortToast(YLApplication.getContext().getString(R.string.pay_error_wechat_noclient));
        return false;
    }
}
