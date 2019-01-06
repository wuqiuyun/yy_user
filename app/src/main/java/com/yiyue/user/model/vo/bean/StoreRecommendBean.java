package com.yiyue.user.model.vo.bean;

/**
 * 口碑店铺推荐
 * Created by wqy on 2018/11/7.
 */

public class StoreRecommendBean {

    private String distance; // 距离
    private String location; // 门店地址
    private int orderNumber; // 订单数
    private String picture;  // 封面
    private String serverType; // 服务类别
    private int storeId;  // 门店ID
    private String storeName; // 门店名称
    private float point; // 评分
    private int monthOrder;//月结单量

    public int getMonthOrder() {
        return monthOrder;
    }

    public void setMonthOrder(int monthOrder) {
        this.monthOrder = monthOrder;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "StoreRecommendBean{" +
                "distance='" + distance + '\'' +
                ", location='" + location + '\'' +
                ", orderNumber=" + orderNumber +
                ", picture='" + picture + '\'' +
                ", serverType='" + serverType + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", point=" + point +
                '}';
    }
}
