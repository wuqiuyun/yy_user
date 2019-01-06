package com.yiyue.user.model.vo.bean;

/**
 * 预约理发师
 * Created by wqy on 2018/11/9.
 */

public class ChooseStylistBean {

    private float grade; //评分 0-5
    private String nickName; //昵称
    private String position; //职称
    private String storeCover; // 门店封面
    private String storeHeadImg; // 门店头像
    private String storeName; //门店名
    private String stylistCover; //理发师封面
    private int stylistId; //理发师ID
    private boolean isStatus;

    public ChooseStylistBean() {
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStoreCover() {
        return storeCover;
    }

    public void setStoreCover(String storeCover) {
        this.storeCover = storeCover;
    }

    public String getStoreHeadImg() {
        return storeHeadImg;
    }

    public void setStoreHeadImg(String storeHeadImg) {
        this.storeHeadImg = storeHeadImg;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStylistCover() {
        return stylistCover;
    }

    public void setStylistCover(String stylistCover) {
        this.stylistCover = stylistCover;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }
}
