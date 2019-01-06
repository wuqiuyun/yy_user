package com.yiyue.user.model.vo.bean;

import java.util.ArrayList;

/**
 * 评价
 * Created by wqy on 2018/11/20.
 */

public class EvaluationBean {


    /**
     * id : 5
     * level : 4
     * serviceName :
     * comment : 1111
     * nickname : ey，rexr，
     * reply : 11111
     * userId : 1
     * headImg : https://yiyuestore.oss-cn-shenzhen.aliyuncs.com/2018-11-10/c75281d5f4114feba410156e8038a103-file
     * createtime : 2018-11-20 11:09:51
     * imgPaths :
     */

    private int id;
    private float level;
    private String serviceName;
    private String comment;
    private String nickname;
    private String reply;
    private int userId;
    private String headImg;
    private String createtime;
    private ArrayList<String> imgPaths;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public ArrayList<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(ArrayList<String> imgPaths) {
        this.imgPaths = imgPaths;
    }
}
