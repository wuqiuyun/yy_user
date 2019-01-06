package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * 门店详情页 门店名位置等信息
 * Created by wqy on 2018/11/8.
 */

public class StoreInfoBean {

    private int distance; //门店距离（单位米）
    private double grade; // 门店评分
    private int isCollection; // 是否收藏，1收藏，0未收藏
    private String location; // 门店地址
    private int storeId; // 门店id
    private String storeName; //  门店名
    private List<String> storePhotos; // 门店照片（多张）
    private String storeHeadImg; //  门店头像,用户门店详情页分享时的分享图片
    private double lat;
    private double lng;
    private int servers;//总接单

    public int getServers() {
        return servers;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

    public String getStoreHeadImg() {
        return storeHeadImg;
    }

    public void setStoreHeadImg(String storeHeadImg) {
        this.storeHeadImg = storeHeadImg;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public List<String> getStorePhotos() {
        return storePhotos;
    }

    public void setStorePhotos(List<String> storePhotos) {
        this.storePhotos = storePhotos;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
