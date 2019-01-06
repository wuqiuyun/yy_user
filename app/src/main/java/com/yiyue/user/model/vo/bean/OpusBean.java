package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * 作品集
 * Created by wqy on 2018/11/11.
 */

public class OpusBean {

    private List<OpusFeaTureListBean> opusFeaTureList;
    private List<OpusHairstyleListBean> opusHairstyleList;
    private List<OpusListBean> opusList;


    public List<OpusFeaTureListBean> getOpusFeaTureList() {
        return opusFeaTureList;
    }

    public void setOpusFeaTureList(List<OpusFeaTureListBean> opusFeaTureList) {
        this.opusFeaTureList = opusFeaTureList;
    }

    public List<OpusHairstyleListBean> getOpusHairstyleList() {
        return opusHairstyleList;
    }

    public void setOpusHairstyleList(List<OpusHairstyleListBean> opusHairstyleList) {
        this.opusHairstyleList = opusHairstyleList;
    }

    public List<OpusListBean> getOpusList() {
        return opusList;
    }

    public void setOpusList(List<OpusListBean> opusList) {
        this.opusList = opusList;
    }

    public static class OpusFeaTureListBean {
        /**
         * count : 0
         * feaTureId : 0
         * feaTureName : string
         */

        private int count;
        private int feaTureId;
        private String feaTureName;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFeaTureId() {
            return feaTureId;
        }

        public void setFeaTureId(int feaTureId) {
            this.feaTureId = feaTureId;
        }

        public String getFeaTureName() {
            return feaTureName;
        }

        public void setFeaTureName(String feaTureName) {
            this.feaTureName = feaTureName;
        }

        @Override
        public String toString() {
            return "OpusFeaTureListBean{" +
                    "count=" + count +
                    ", feaTureId=" + feaTureId +
                    ", feaTureName='" + feaTureName + '\'' +
                    '}';
        }
    }

    public static class OpusHairstyleListBean {
        /**
         * count : 0
         * hairstyleId : 0
         * hairstyleName : string
         */

        private int count;
        private int hairstyleId;
        private String hairstyleName;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getHairstyleId() {
            return hairstyleId;
        }

        public void setHairstyleId(int hairstyleId) {
            this.hairstyleId = hairstyleId;
        }

        public String getHairstyleName() {
            return hairstyleName;
        }

        public void setHairstyleName(String hairstyleName) {
            this.hairstyleName = hairstyleName;
        }

        @Override
        public String toString() {
            return "OpusHairstyleListBean{" +
                    "count=" + count +
                    ", hairstyleId=" + hairstyleId +
                    ", hairstyleName='" + hairstyleName + '\'' +
                    '}';
        }
    }

    public static class OpusListBean {
        /**
         * stylistOpusCovers : string
         * stylistOpusId : 0
         */

        private String stylistOpusCovers;//作品图片
        private int stylistOpusId;

        public String getStylistOpusCovers() {
            return stylistOpusCovers;
        }

        public void setStylistOpusCovers(String stylistOpusCovers) {
            this.stylistOpusCovers = stylistOpusCovers;
        }

        public int getStylistOpusId() {
            return stylistOpusId;
        }

        public void setStylistOpusId(int stylistOpusId) {
            this.stylistOpusId = stylistOpusId;
        }

        @Override
        public String toString() {
            return "OpusListBean{" +
                    "stylistOpusCovers='" + stylistOpusCovers + '\'' +
                    ", stylistOpusId=" + stylistOpusId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OpusBean{" +
                "opusFeaTureList=" + opusFeaTureList +
                ", opusHairstyleList=" + opusHairstyleList +
                ", opusList=" + opusList +
                '}';
    }
}
