package com.yiyue.user.model.vo.bean;

import java.io.Serializable;

/**
 * Created by Lizhuo on 2018/11/9.
 */
public class GetApplyStatusBean implements Serializable{
    /**
     * "data": {
     "realName": "",
     "cardNo": "",
     "applyStatus": 3, 审核状态 1 通过 0 待审核 -1 驳回 3未申请
     "tip": "特别声明：/n  1、审核通过后个人信息不可修改；/n  2、平台对真实个人信息保密；/n  3、审核时间为1-3个工作日；"
     }
     */
    private String realName;
    private String cardNo;
    private String tip;
    private int applyStatus;//审核状态

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }
}
