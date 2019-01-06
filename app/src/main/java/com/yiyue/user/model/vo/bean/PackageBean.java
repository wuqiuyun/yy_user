package com.yiyue.user.model.vo.bean;

/**
 * 套餐
 * Created by wqy on 2018/11/13.
 */

public class PackageBean {

    private int serviceId;//套餐服务ID
    private String content;
    private int times;
    private int type;//套餐类型1 单项 2 多项
    private double costprice;
    private double price;
    private int packageId;
    private String categoryname;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getCostprice() {
        return costprice;
    }

    public void setCostprice(double costprice) {
        this.costprice = costprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    @Override
    public String toString() {
        return "PackageBean{" +
                "serviceId=" + serviceId +
                ", content='" + content + '\'' +
                ", times=" + times +
                ", type=" + type +
                ", costprice=" + costprice +
                ", price=" + price +
                ", packageId=" + packageId +
                ", categoryname='" + categoryname + '\'' +
                '}';
    }
}
