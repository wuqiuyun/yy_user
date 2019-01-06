package com.yiyue.user.model.vo.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
public class StoreBean implements Serializable{

    private String distance;
    private String location;
    private ArrayList<String> serves;
    private ArrayList<String> categorys;
    private String storeCover;
    private long storeId; // 店铺id
    private String storeName;
    private long userId;
    private String servers;
    private double grade;
    private String monthServer;
    private String scoretimes;
    private String minPrice;
    private String maxPrice;
    private String storename;
    private boolean status;
    private String pictrue;

    public ArrayList<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(ArrayList<String> categorys) {
        this.categorys = categorys;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public StoreBean(boolean status) {
        this.status = status;
    }

    public StoreBean() {
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public ArrayList<String> getServes()
    {
        return serves;
    }

    public void setServes(ArrayList<String> serves)
    {
        this.serves = serves;
    }

    public String getStoreCover()
    {
        return storeCover;
    }

    public void setStoreCover(String storeCover)
    {
        this.storeCover = storeCover;
    }

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getMonthServer() {
        return monthServer;
    }

    public void setMonthServer(String monthServer) {
        this.monthServer = monthServer;
    }

    public String getScoretimes() {
        return scoretimes;
    }

    public void setScoretimes(String scoretimes) {
        this.scoretimes = scoretimes;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    @Override
    public String toString() {
        return "StoreBean{" +
                "distance='" + distance + '\'' +
                ", location='" + location + '\'' +
                ", serves=" + serves +
                ", storeCover='" + storeCover + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", userId=" + userId +
                ", servers='" + servers + '\'' +
                ", grade=" + grade +
                ", monthServer='" + monthServer + '\'' +
                ", scoretimes='" + scoretimes + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", storename='" + storename + '\'' +
                ", status=" + status +
                '}';
    }
}
