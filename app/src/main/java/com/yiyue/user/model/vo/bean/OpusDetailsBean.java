package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * 作品详情
 * Created by wqy on 2018/11/11.
 */

public class OpusDetailsBean {
    private int collection; //收藏量
    private String describe; //作品描述
    private boolean isCollection; //是否收藏
    private boolean isAppointment; //是否可以预约
    private int opusId;
    private int pageview; //查看量
    private int reposted; //转发量
    private List<PictruesBean> pictrues; // 照片

    public boolean isAppointment() {
        return isAppointment;
    }

    public void setAppointment(boolean appointment) {
        isAppointment = appointment;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public int getOpusId() {
        return opusId;
    }

    public void setOpusId(int opusId) {
        this.opusId = opusId;
    }

    public int getPageview() {
        return pageview;
    }

    public void setPageview(int pageview) {
        this.pageview = pageview;
    }

    public int getReposted() {
        return reposted;
    }

    public void setReposted(int reposted) {
        this.reposted = reposted;
    }

    public List<PictruesBean> getPictrues() {
        return pictrues;
    }

    public void setPictrues(List<PictruesBean> pictrues) {
        this.pictrues = pictrues;
    }

    public static class PictruesBean {
        /**
         * path : string
         * pictureId : 0
         */

        private String path;
        private int pictureId;

        public PictruesBean() {
        }

        public PictruesBean(String path, int pictureId) {
            this.path = path;
            this.pictureId = pictureId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPictureId() {
            return pictureId;
        }

        public void setPictureId(int pictureId) {
            this.pictureId = pictureId;
        }

        @Override
        public String toString() {
            return "PictruesBean{" +
                    "path='" + path + '\'' +
                    ", pictureId=" + pictureId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OpusDetailsBean{" +
                "collection=" + collection +
                ", describe='" + describe + '\'' +
                ", isCollection=" + isCollection +
                ", opusId=" + opusId +
                ", pageview=" + pageview +
                ", reposted=" + reposted +
                ", pictrues=" + pictrues +
                '}';
    }
}
