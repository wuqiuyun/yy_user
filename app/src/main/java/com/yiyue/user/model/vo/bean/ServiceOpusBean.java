package com.yiyue.user.model.vo.bean;

/**
 * 作品（服务）
 * Created by wqy on 2018/11/15.
 */

public class ServiceOpusBean {

    /**
     * opusName :
     * isCollection : 0
     * pageview : 2
     * imgPath : https://yiyuestylist.oss-cn-shenzhen.aliyuncs.com/2018-11-14/3a324e47a82343c483861a0dd4ba7b26-files
     * headImg :
     * stylistName : 我的名字那么长23
     * opusId : 1563
     * position : 1
     * stylistId : 1005
     * describe : ajtmjmtm
     */

    private String opusName;
    private int isCollection;
    private int pageview;
    private String imgPath;
    private String headImg;
    private String stylistName;
    private int opusId;
    private String position;
    private int stylistId;
    private String describe;

    public String getOpusName() {
        return opusName;
    }

    public void setOpusName(String opusName) {
        this.opusName = opusName;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getPageview() {
        return pageview;
    }

    public void setPageview(int pageview) {
        this.pageview = pageview;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public int getOpusId() {
        return opusId;
    }

    public void setOpusId(int opusId) {
        this.opusId = opusId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "ServiceOpusBean{" +
                "opusName='" + opusName + '\'' +
                ", isCollection=" + isCollection +
                ", pageview=" + pageview +
                ", imgPath='" + imgPath + '\'' +
                ", headImg='" + headImg + '\'' +
                ", stylistName='" + stylistName + '\'' +
                ", opusId=" + opusId +
                ", position='" + position + '\'' +
                ", stylistId=" + stylistId +
                ", describe='" + describe + '\'' +
                '}';
    }
}
