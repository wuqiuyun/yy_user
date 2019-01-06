package com.yiyue.user.model.vo.requestbody;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lvlong on 2018/11/12.
 */
public class CreateOrderBody implements Parcelable{
    protected CreateOrderBody(Parcel in) {
        isResult = in.readString();
        amount = in.readString();
        couponId = in.readString();
        couponamount = in.readString();
        coupondirection = in.readString();
        coupontype = in.readString();
        day = in.readString();
        orderamount = in.readString();
        packageId = in.readString();
        serviceId = in.readString();
        servicemodel = in.readString();
        storeId = in.readString();
        storeName = in.readString();
        stylistId = in.readString();
        usetime = in.readString();
        userId = in.readString();
        optionId = in.readString();
        serviceName = in.readString();
        remark = in.readString();
        times = in.readString();
    }

    public CreateOrderBody() {
    }

    public static final Creator<CreateOrderBody> CREATOR = new Creator<CreateOrderBody>() {
        @Override
        public CreateOrderBody createFromParcel(Parcel in) {
            return new CreateOrderBody(in);
        }

        @Override
        public CreateOrderBody[] newArray(int size) {
            return new CreateOrderBody[size];
        }
    };

    @Override
    public String toString() {
        return "CreateOrderBody{" +
                "couponId='" + couponId + '\'' +
                ", couponamount='" + couponamount + '\'' +
                ", coupondirection='" + coupondirection + '\'' +
                ", coupontype='" + coupontype + '\'' +
                ", day='" + day + '\'' +
                ", orderamount='" + orderamount + '\'' +
                ", packageId='" + packageId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", servicemodel='" + servicemodel + '\'' +
                ", storeId='" + storeId + '\'' +
                ", stylistId='" + stylistId + '\'' +
                ", usetime='" + usetime + '\'' +
                ", userId='" + userId + '\'' +
                ", optionId='" + optionId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", amount ='" + amount  + '\'' +
                ", isResult ='" + isResult  + '\'' +
                ", remark ='" + remark  + '\'' +
                ", times ='" + times  + '\'' +
                '}';
    }

    /**
     * couponId : 0
     * couponamount : 0
     * coupondirection : string
     * coupontype : string
     * day : 0
     * orderamount : 0
     * packageId : 0
     * serviceId : 0
     * servicemodel : string
     * storeId : 0
     * stylistId : 0
     * usetime : string
     */

    private String isResult;//是否属于订单页返回0否1是 此字段不提交
    private String amount;
    private String couponId;
    private String couponamount;
    private String coupondirection;
    private String coupontype;
    private String day;
    private String orderamount;
    private String packageId;
    private String serviceId;
    private String servicemodel;
    private String storeId;
    private String storeName;
    private String stylistId;
    private String usetime;
    private String userId;
    private String optionId;
    private String serviceName;
    private String remark;
    private String times;

    public String getIsResult() {
        return isResult;
    }

    public void setIsResult(String isResult) {
        this.isResult = isResult;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
    }

    public String getCoupondirection() {
        return coupondirection;
    }

    public void setCoupondirection(String coupondirection) {
        this.coupondirection = coupondirection;
    }

    public String getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(String coupontype) {
        this.coupontype = coupontype;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicemodel() {
        return servicemodel;
    }

    public void setServicemodel(String servicemodel) {
        this.servicemodel = servicemodel;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(isResult);
        parcel.writeString(amount);
        parcel.writeString(couponId);
        parcel.writeString(couponamount);
        parcel.writeString(coupondirection);
        parcel.writeString(coupontype);
        parcel.writeString(day);
        parcel.writeString(orderamount);
        parcel.writeString(packageId);
        parcel.writeString(serviceId);
        parcel.writeString(servicemodel);
        parcel.writeString(storeId);
        parcel.writeString(storeName);
        parcel.writeString(stylistId);
        parcel.writeString(usetime);
        parcel.writeString(userId);
        parcel.writeString(optionId);
        parcel.writeString(serviceName);
        parcel.writeString(remark);
        parcel.writeString(times);
    }
}
