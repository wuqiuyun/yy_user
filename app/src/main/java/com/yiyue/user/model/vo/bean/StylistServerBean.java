package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * Created by lvlong on 2018/11/12.
 */
public class StylistServerBean {

    /**
     * nickname : ??
     * position : 总监
     * serverItems : [{"serviceId":1,"servicename":"???","isoption":0,"price":22},{"serviceId":2,"servicename":"??","isoption":0,"price":24},{"serviceId":3,"servicename":"??","isoption":0,"price":33},{"serviceId":766,"servicename":"洗剪吹","isoption":0,"price":321},{"serviceId":771,"servicename":"染发","isoption":0,"price":889},{"serviceId":774,"servicename":"染发","isoption":0,"price":888},{"serviceId":777,"servicename":"烫发","isoption":0,"price":77},{"serviceId":789,"servicename":"洗剪吹","isoption":0,"price":777},{"serviceId":791,"servicename":"洗剪吹","isoption":0,"price":666},{"serviceId":802,"servicename":"132","isoption":0,"price":123},{"serviceId":806,"servicename":"烫发","isoption":0,"price":1636},{"serviceId":808,"servicename":"染发","isoption":0,"price":9855},{"serviceId":809,"servicename":"染发","isoption":0,"price":9866},{"serviceId":811,"servicename":"染发","isoption":0,"price":99886},{"serviceId":813,"servicename":"染发","isoption":0,"price":268},{"serviceId":816,"servicename":"染发","isoption":0,"price":268},{"serviceId":818,"servicename":"染发","isoption":1,"price":99825},{"serviceId":906,"servicename":"多项套餐666","isoption":1,"price":444},{"serviceId":955,"servicename":"染发","isoption":0,"price":123},{"serviceId":958,"servicename":"染发","isoption":0,"price":123},{"serviceId":960,"servicename":"染发","isoption":0,"price":123},{"serviceId":972,"servicename":"染发","isoption":0,"price":123123},{"serviceId":981,"servicename":"zyh - 1","isoption":0,"price":4321},{"serviceId":1065,"servicename":"zyh - 1","isoption":0,"price":123},{"serviceId":1066,"servicename":"zyh - 1","isoption":0,"price":112},{"serviceId":1072,"servicename":"zyh - 1","isoption":0,"price":1111},{"serviceId":1096,"servicename":"zyh - 1","isoption":0,"price":123}]
     */

    private String nickname;
    private int gender;
    private String position;
    private String headportrait;
    private List<ServerItemsBean> serverItems;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<ServerItemsBean> getServerItems() {
        return serverItems;
    }

    public void setServerItems(List<ServerItemsBean> serverItems) {
        this.serverItems = serverItems;
    }

    public static class ServerItemsBean {
        @Override
        public String toString() {
            return "ServerItemsBean{" +
                    "serviceId='" + serviceId + '\'' +
                    ", servicename='" + servicename + '\'' +
                    ", isoption='" + isoption + '\'' +
                    ", price='" + price + '\'' +
                    ", checked=" + checked +
                    '}';
        }

        /**
         * serviceId : 1
         * servicename : ???
         * isoption : 0
         * price : 22
         */

        private String serviceId;
        private String servicename;
        private String isoption;
        private String price;
        private boolean checked;
        private String selectedTitle;
        private String selectedPrice;

        public String getSelectedTitle() {
            return selectedTitle;
        }

        public void setSelectedTitle(String selectedTitle) {
            this.selectedTitle = selectedTitle;
        }

        public String getSelectedPrice() {
            return selectedPrice;
        }

        public void setSelectedPrice(String selectedPrice) {
            this.selectedPrice = selectedPrice;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServicename() {
            return servicename;
        }

        public void setServicename(String servicename) {
            this.servicename = servicename;
        }

        public String getIsoption() {
            return isoption;
        }

        public void setIsoption(String isoption) {
            this.isoption = isoption;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    @Override
    public String toString() {
        return "StylistServerBean{" +
                "nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", position='" + position + '\'' +
                ", headportrait='" + headportrait + '\'' +
                ", serverItems=" + serverItems +
                '}';
    }
}
