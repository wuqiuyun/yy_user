package com.yiyue.user.model.vo.bean;

/**
 * 口碑美发师推荐
 * Created by wqy on 2018/11/7.
 */

public class StylistRecommendBean {

    private String headportrait;
    private String imusername;
    private String lable;
    private String nickname;
    private int orderNumber;
    private String serverType;
    private int stylistId;
    private int userId;
    private String position;

    public boolean isCollection() {
        return isCollection;
    }


    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    private boolean isCollection;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public StylistRecommendBean() {
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public String getImusername() {
        return imusername;
    }

    public void setImusername(String imusername) {
        this.imusername = imusername;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StylistRecommendBean{" +
                "headportrait='" + headportrait + '\'' +
                ", imusername='" + imusername + '\'' +
                ", lable='" + lable + '\'' +
                ", nickname='" + nickname + '\'' +
                ", orderNumber=" + orderNumber +
                ", serverType='" + serverType + '\'' +
                ", stylistId=" + stylistId +
                ", userId=" + userId +
                '}';
    }
}
