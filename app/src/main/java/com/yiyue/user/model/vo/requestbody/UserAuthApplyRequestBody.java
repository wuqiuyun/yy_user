package com.yiyue.user.model.vo.requestbody;

import android.text.TextUtils;

import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.util.AccountValidatorUtil;


/**
 * Created by zm on 2018/10/18.
 */
public class UserAuthApplyRequestBody {

    private String cardFront; // 身份证正面
    private String cardReverse; // 身份证反面
    private String cardNo; // 身份证号码
    private String hardCard; // 手执证件照
    private String realName; // 真实姓名
    private String userId;//用户id

    public boolean checkParams() {
        if (TextUtils.isEmpty(realName)) {
            ToastUtils.shortToast("真实姓名不能为空.");
            return false;
        }
        if (TextUtils.isEmpty(cardNo)) {
            ToastUtils.shortToast("身份证号码不能为空.");
            return false;
        }
        if (!AccountValidatorUtil.isIDCard(cardNo)) {
            ToastUtils.shortToast("请输入正确的身份证号码");
            return false;
        }
        if (TextUtils.isEmpty(hardCard)) {
            ToastUtils.shortToast("请上传手持证件照.");
            return false;
        }
        if (TextUtils.isEmpty(cardFront)) {
            ToastUtils.shortToast("请上传身份证正面.");
            return false;
        }
        if (TextUtils.isEmpty(cardReverse)) {
            ToastUtils.shortToast("请上传身份证反面.");
            return false;
        }
        return true;
    }


    private UserAuthApplyRequestBody(Builder builder) {
        cardFront = builder.cardFront;
        cardReverse = builder.cardReverse;
        cardNo = builder.cardNo;
        hardCard = builder.hardCard;
        realName = builder.realName;
        userId = builder.userId;
    }


    public static final class Builder {
        private String cardFront;
        private String cardReverse;
        private String cardNo;
        private String hardCard;
        private String realName;
        private String userId;

        public Builder() {
        }

        public Builder cardFront(String val) {
            cardFront = val;
            return this;
        }

        public Builder cardReverse(String val) {
            cardReverse = val;
            return this;
        }

        public Builder cardNo(String val) {
            cardNo = val;
            return this;
        }

        public Builder hardCard(String val) {
            hardCard = val;
            return this;
        }

        public Builder realname(String val) {
            realName = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public UserAuthApplyRequestBody build() {
            return new UserAuthApplyRequestBody(this);
        }
    }
}
