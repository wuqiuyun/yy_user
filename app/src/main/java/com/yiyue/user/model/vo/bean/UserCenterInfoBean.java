package com.yiyue.user.model.vo.bean;

import java.io.Serializable;

/**
 * Created by Lizhuo on 2018/11/8.
 * 用户个人中心资料
 */
public class UserCenterInfoBean implements Serializable {
    /**
     * "gender": 1,
     "faceture": null,
     "hairstyle": null,
     "hobby": null,
     "birthday": "",
     "job": null,
     "id": 5439,
     "nickname": "村长",
     "headImg": ""
     */
    
    private int gender; //1男2女3人妖
    private int faceture; //1方脸 2圆脸 3尖脸 4瓜子脸 5鹅蛋脸 6包子脸
    private int hairstyle; //1长发 2短发 3中发 
    private String hobby;
    private String birthday;
    private String job;
    private long id;
    private String nickname;
    private String headImg;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getFaceture() {
        return faceture;
    }

    public void setFaceture(int faceture) {
        this.faceture = faceture;
    }

    public int getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(int hairstyle) {
        this.hairstyle = hairstyle;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
