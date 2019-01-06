package com.yiyue.user.model.vo.bean.daobean;

import java.io.Serializable;

/**
 * 排行榜
 * Created by wqy on 2018/11/19
 */
public class RankBean implements Serializable {

    /**
     * stylistId : 1280
     * stylistName : 杀马特首席理发师
     * storeName : 杀马特基地
     * starLevel : 0
     * serviceName : 洗剪吹
     * price : 88
     * headImg : https://yiyuestore.oss-cn-shenzhen.aliyuncs.com/2018-11-16/b1f0b906a4954a78972f6c68e0fb6923-files
     */

    private int stylistId;
    private String stylistName;
    private String storeName;
    private float starLevel;
    private String serviceName;
    private double price;
    private String headImg;

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(float starLevel) {
        this.starLevel = starLevel;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
