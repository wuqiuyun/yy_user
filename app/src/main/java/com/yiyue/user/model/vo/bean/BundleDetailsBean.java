package com.yiyue.user.model.vo.bean;

import java.io.Serializable;

/**
 * 套餐详情
 * Created by wqy on 2018/11/13.
 */

public class BundleDetailsBean implements Serializable {

    private int serviceId; //服务ID
    private String content;//内容
    private int times;      //使用次数
    private int type;       //套餐类型 1 单项多次套餐 2 多项单次套餐
    private double costprice;   //原价
    private double price;      //价格;
    private int packageId ; //套餐ID
    private String categoryname;//服务类型名称
    private String stylistName;//发布者
    private String stylistId;//美发师ID

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

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

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }
}
