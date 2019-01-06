package com.yiyue.user.model.vo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zm on 2018/9/20.
 */
public class OrderBean implements Parcelable{

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
    private long createtime;
    private int status;
    private double handlingfee;
    private long starttime;
    private long endtime;
    private long canceltime;
    private long refundtime;
    private String stylistPath;
    private String stylistNickname;
    private String storeName;
    private long ordertime;
    private String datename;
    private String storePath;
    private int coupontype; // 满减 2 折扣 3 抵扣券
    private OrderAddMoneyBean orderAddMoney;

    public OrderBean() {}

    protected OrderBean(Parcel in) {
        id = in.readString();
        userId = in.readString();
        storeId = in.readString();
        stylistId = in.readString();
        servicemodel = in.readInt();
        serviceId = in.readString();
        packageId = in.readInt();
        ordername = in.readString();
        orderno = in.readString();
        orderamount = in.readDouble();
        payamount = in.readDouble();
        status = in.readInt();
        handlingfee = in.readDouble();
        starttime = in.readLong();
        endtime = in.readLong();
        canceltime = in.readLong();
        refundtime = in.readLong();
        stylistPath = in.readString();
        stylistNickname = in.readString();
        storeName = in.readString();
        ordertime = in.readLong();
        datename = in.readString();
        storePath = in.readString();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(int coupontype) {
        this.coupontype = coupontype;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(storeId);
        dest.writeString(stylistId);
        dest.writeInt(servicemodel);
        dest.writeString(serviceId);
        dest.writeInt(packageId);
        dest.writeString(ordername);
        dest.writeString(orderno);
        dest.writeDouble(orderamount);
        dest.writeDouble(payamount);
        dest.writeInt(status);
        dest.writeDouble(handlingfee);
        dest.writeLong(starttime);
        dest.writeLong(endtime);
        dest.writeLong(canceltime);
        dest.writeLong(refundtime);
        dest.writeString(stylistPath);
        dest.writeString(stylistNickname);
        dest.writeString(storeName);
        dest.writeLong(ordertime);
        dest.writeString(datename);
        dest.writeString(storePath);
    }

    public OrderAddMoneyBean getOrderAddMoney() {
        return orderAddMoney;
    }

    public void setOrderAddMoney(OrderAddMoneyBean orderAddMoney) {
        this.orderAddMoney = orderAddMoney;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }
}
