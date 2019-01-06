package com.yiyue.user.model.vo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lvlong on 2018/11/16.
 */
public class CreateOrderBean implements Parcelable {

    /**
     * id : 1437
     * userId : 1588
     * storeId : 1603
     * stylistId : 5
     * servicemodel : 1
     * serviceId : 6
     * packageId : 4071
     * ordername : 美发-短发-药水名称
     * orderno : S2018111610582045929
     * orderamount : 94
     * payamount : 94
     * status : 0
     * remark : null
     * handlingfee : null
     * updatetime : 1542337100516
     * createtime : 1542337100516
     * starttime : null
     * endtime : null
     * canceltime : null
     * refundtime : null
     */

    private int id;
    private int userId;
    private int storeId;
    private int stylistId;
    private int servicemodel;
    private int serviceId;
    private int packageId;
    private String ordername;
    private String orderno;
    private double orderamount;
    private double payamount;
    private int status;
    private String remark;
    private String handlingfee;
    private long updatetime;
    private long createtime;
    private String starttime;
    private String endtime;
    private String canceltime;
    private String refundtime;
    private int gender;
    private int times;

    public CreateOrderBean() {
    }

    protected CreateOrderBean(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        storeId = in.readInt();
        stylistId = in.readInt();
        servicemodel = in.readInt();
        serviceId = in.readInt();
        packageId = in.readInt();
        ordername = in.readString();
        orderno = in.readString();
        orderamount = in.readDouble();
        payamount = in.readDouble();
        status = in.readInt();
        remark = in.readString();
        handlingfee = in.readString();
        updatetime = in.readLong();
        createtime = in.readLong();
        starttime = in.readString();
        endtime = in.readString();
        canceltime = in.readString();
        refundtime = in.readString();
        gender = in.readInt();
        times = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(storeId);
        dest.writeInt(stylistId);
        dest.writeInt(servicemodel);
        dest.writeInt(serviceId);
        dest.writeInt(packageId);
        dest.writeString(ordername);
        dest.writeString(orderno);
        dest.writeDouble(orderamount);
        dest.writeDouble(payamount);
        dest.writeInt(status);
        dest.writeString(remark);
        dest.writeString(handlingfee);
        dest.writeLong(updatetime);
        dest.writeLong(createtime);
        dest.writeString(starttime);
        dest.writeString(endtime);
        dest.writeString(canceltime);
        dest.writeString(refundtime);
        dest.writeInt(gender);
        dest.writeInt(times);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateOrderBean> CREATOR = new Creator<CreateOrderBean>() {
        @Override
        public CreateOrderBean createFromParcel(Parcel in) {
            return new CreateOrderBean(in);
        }

        @Override
        public CreateOrderBean[] newArray(int size) {
            return new CreateOrderBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public int getServicemodel() {
        return servicemodel;
    }

    public void setServicemodel(int servicemodel) {
        this.servicemodel = servicemodel;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandlingfee() {
        return handlingfee;
    }

    public void setHandlingfee(String handlingfee) {
        this.handlingfee = handlingfee;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(String canceltime) {
        this.canceltime = canceltime;
    }

    public String getRefundtime() {
        return refundtime;
    }

    public void setRefundtime(String refundtime) {
        this.refundtime = refundtime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
