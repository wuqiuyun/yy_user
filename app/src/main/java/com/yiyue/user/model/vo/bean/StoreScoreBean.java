package com.yiyue.user.model.vo.bean;

/**
 * Created by wqy on 2018/11/8.
 */

public class StoreScoreBean {

    private String environmentScore;
    private String pareEnvirtAvg;
    private String pareServerAvg;
    private String score;
    private String scoretimes;
    private String serverScore;
    private String storeId;

    public String getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(String environmentScore) {
        this.environmentScore = environmentScore;
    }

    public String getPareEnvirtAvg() {
        return pareEnvirtAvg;
    }

    public void setPareEnvirtAvg(String pareEnvirtAvg) {
        this.pareEnvirtAvg = pareEnvirtAvg;
    }

    public String getPareServerAvg() {
        return pareServerAvg;
    }

    public void setPareServerAvg(String pareServerAvg) {
        this.pareServerAvg = pareServerAvg;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoretimes() {
        return scoretimes;
    }

    public void setScoretimes(String scoretimes) {
        this.scoretimes = scoretimes;
    }

    public String getServerScore() {
        return serverScore;
    }

    public void setServerScore(String serverScore) {
        this.serverScore = serverScore;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "StoreScoreBean{" +
                "environmentScore='" + environmentScore + '\'' +
                ", pareEnvirtAvg='" + pareEnvirtAvg + '\'' +
                ", pareServerAvg='" + pareServerAvg + '\'' +
                ", score='" + score + '\'' +
                ", scoretimes='" + scoretimes + '\'' +
                ", serverScore='" + serverScore + '\'' +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}
