package com.yiyue.user.model.vo.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2018/11/20
 */
public class AliPayBean implements Serializable {

    /**
     * orderNo : 20181120114602269
     * payurl : https://qr.alipay.com/bax06702c8gduwaeru4s4002
     */

    private String orderNo;
    private String codeUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayurl() {
        return codeUrl;
    }

    public void setPayurl(String payurl) {
        this.codeUrl = payurl;
    }
}
