package com.yiyue.user.model.vo.bean;

/**
 * Created by zm on 2018/10/10.
 */
public class StylistBean {

    private float grade;
    private String stylistName;
    private String maxSalesItem;
    private String professor;
    private int receiptCount;
    private int receiptType;
    private long stylistId;
    private long userId;
    private String nickname;//昵称
    private float star;//评分
    private String headPortrait;//头像
    private double totalPerformance;//总业绩
    private double waitVerification;//
    private String service;
    private String position;
    private double price;
    private int monthOrder;
    private String distance;
    private int commentNumber;
    private String lable;
    private String stylistCover;
    private String coverImg;



    public StylistBean(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public double getTotalPerformance() {
        return totalPerformance;
    }

    public void setTotalPerformance(double totalPerformance) {
        this.totalPerformance = totalPerformance;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getMaxSalesItem() {
        return maxSalesItem;
    }

    public void setMaxSalesItem(String maxSalesItem) {
        this.maxSalesItem = maxSalesItem;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getReceiptCount() {
        return receiptCount;
    }

    public void setReceiptCount(int receiptCount) {
        this.receiptCount = receiptCount;
    }

    public int getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(int receiptType) {
        this.receiptType = receiptType;
    }

    public long getStylistId() {
        return stylistId;
    }

    public void setStylistId(long stylistId) {
        this.stylistId = stylistId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public double getWaitVerification() {
        return waitVerification;
    }

    public void setWaitVerification(double waitVerification) {
        this.waitVerification = waitVerification;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getStylistCover() {
        return stylistCover;
    }

    public void setStylistCover(String stylistCover) {
        this.stylistCover = stylistCover;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
}