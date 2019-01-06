package com.yiyue.user.model.vo.bean;

/**
 * Created by zm on 2018/10/27.
 */
public class OrderDetailsBean {
    private String id;
    private String userId;
    private String storeId;
    private String stylistId;
    private int servicemodel;
    private String serviceId;
    private int packageId;
    private String ordername;
    private String orderno;
    private double orderamount;
    private double payamount;
    private int status;
    private double handlingfee;
    private long createtime;
    private long starttime;
    private long endtime;
    private long canceltime;
    private long refundtime;
    private int stylistUserId;
    private String stylistPath;
    private String stylistNickname;
    private String stylistImusername;
    private String stylistMobile;
    private int stylistGender;
    private String stylistLabel;
    private String serviceCategory;
    private float stylistScore;
    private String storeName;
    private StoreLocationBean storeLocation;
    private OrderAddMoneyBean orderAddMoney;
    private OrderRefundBean orderRefund;
    private long ordertime;
    private String datename;
    private String storePath;
    private long pendingTime;
    private long oldordertime;
    private String remark;

    private int coupontype; // 1 满减 2折扣 3抵扣
    private Double couponamount; // 优惠金额和折扣
    private int coupondirection; // 1 平台 2 美发师

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getOldordertime() {
        return oldordertime;
    }

    public void setOldordertime(long oldordertime) {
        this.oldordertime = oldordertime;
    }

    public int getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(int coupontype) {
        this.coupontype = coupontype;
    }

    public Double getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(Double couponamount) {
        this.couponamount = couponamount;
    }

    public int getCoupondirection() {
        return coupondirection;
    }

    public void setCoupondirection(int coupondirection) {
        this.coupondirection = coupondirection;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public int getServicemodel() {
        return servicemodel;
    }

    public void setServicemodel(int servicemodel) {
        this.servicemodel = servicemodel;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(double orderamount) {
        this.orderamount = orderamount;
    }

    public double getPayamount() {
        return payamount;
    }

    public void setPayamount(double payamount) {
        this.payamount = payamount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getHandlingfee() {
        return handlingfee;
    }

    public void setHandlingfee(double handlingfee) {
        this.handlingfee = handlingfee;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public long getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(long canceltime) {
        this.canceltime = canceltime;
    }

    public long getRefundtime() {
        return refundtime;
    }

    public void setRefundtime(long refundtime) {
        this.refundtime = refundtime;
    }

    public int getStylistUserId() {
        return stylistUserId;
    }

    public void setStylistUserId(int stylistUserId) {
        this.stylistUserId = stylistUserId;
    }

    public String getStylistPath() {
        return stylistPath;
    }

    public void setStylistPath(String stylistPath) {
        this.stylistPath = stylistPath;
    }

    public String getStylistNickname() {
        return stylistNickname;
    }

    public void setStylistNickname(String stylistNickname) {
        this.stylistNickname = stylistNickname;
    }

    public String getStylistImusername() {
        return stylistImusername;
    }

    public void setStylistImusername(String stylistImusername) {
        this.stylistImusername = stylistImusername;
    }

    public String getStylistMobile() {
        return stylistMobile;
    }

    public void setStylistMobile(String stylistMobile) {
        this.stylistMobile = stylistMobile;
    }

    public int getStylistGender() {
        return stylistGender;
    }

    public void setStylistGender(int stylistGender) {
        this.stylistGender = stylistGender;
    }

    public String getStylistLabel() {
        return stylistLabel;
    }

    public void setStylistLabel(String stylistLabel) {
        this.stylistLabel = stylistLabel;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public float getStylistScore() {
        return stylistScore;
    }

    public void setStylistScore(float stylistScore) {
        this.stylistScore = stylistScore;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public StoreLocationBean getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocationBean storeLocation) {
        this.storeLocation = storeLocation;
    }

    public OrderAddMoneyBean getOrderAddMoney() {
        return orderAddMoney;
    }

    public void setOrderAddMoney(OrderAddMoneyBean orderAddMoney) {
        this.orderAddMoney = orderAddMoney;
    }

    public OrderRefundBean getOrderRefund() {
        return orderRefund;
    }

    public void setOrderRefund(OrderRefundBean orderRefund) {
        this.orderRefund = orderRefund;
    }

    public long getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(long ordertime) {
        this.ordertime = ordertime;
    }

    public String getDatename() {
        return datename;
    }

    public void setDatename(String datename) {
        this.datename = datename;
    }

    public long getPendingTime() {
        return pendingTime;
    }

    public void setPendingTime(long pendingTime) {
        this.pendingTime = pendingTime;
    }
}
