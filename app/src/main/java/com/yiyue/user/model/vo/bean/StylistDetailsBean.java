package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * 美发师详情
 * Created by wqy on 2018/11/10.
 */

public class StylistDetailsBean {

    private String backGroundImg;   //美发师背景图
    private int stylistId;
    private int userId;
    private String nickname;
    private String lable;
    private String serverTypes;
    private float star;
    private float point;
    private int gender; // 性别：1 男 2 女 3 人妖
    private String headPortrait;
    private String position;
    private String mobile;
    private String yearbirth;
    private String description;
    private int evaluatenumer;
    private boolean isCollection;
    private String imusername;
    private int orderNumer;//订单总数
    private List<CardStoreDTOsBean> cardStoreDTOs;
    private List<CardOpusDTOsBean> cardOpusDTOs;
    private List<CardGradeDTOsBean> cardGradeDTOs;
    private List<CardServerItemsBean> cardServerItems;
    private List<CardCouponDTOs> cardCouponDTOs;
    private List<CardPackagesBean> cardPackages;

    public int getOrderNumer() {
        return orderNumer;
    }

    public void setOrderNumer(int orderNumer) {
        this.orderNumer = orderNumer;
    }

    public String getBackGroundImg() {
        return backGroundImg;
    }

    public void setBackGroundImg(String backGroundImg) {
        this.backGroundImg = backGroundImg;
    }

    public int getEvaluatenumer() {
        return evaluatenumer;
    }

    public void setEvaluatenumer(int evaluatenumer) {
        this.evaluatenumer = evaluatenumer;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getServerTypes() {
        return serverTypes;
    }

    public void setServerTypes(String serverTypes) {
        this.serverTypes = serverTypes;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getYearbirth() {
        return yearbirth;
    }

    public void setYearbirth(String yearbirth) {
        this.yearbirth = yearbirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public String getImusername() {
        return imusername;
    }

    public void setImusername(String imusername) {
        this.imusername = imusername;
    }

    public List<CardStoreDTOsBean> getCardStoreDTOs() {
        return cardStoreDTOs;
    }

    public void setCardStoreDTOs(List<CardStoreDTOsBean> cardStoreDTOs) {
        this.cardStoreDTOs = cardStoreDTOs;
    }

    public List<CardOpusDTOsBean> getCardOpusDTOs() {
        return cardOpusDTOs;
    }

    public void setCardOpusDTOs(List<CardOpusDTOsBean> cardOpusDTOs) {
        this.cardOpusDTOs = cardOpusDTOs;
    }

    public List<CardGradeDTOsBean> getCardGradeDTOs() {
        return cardGradeDTOs;
    }

    public void setCardGradeDTOs(List<CardGradeDTOsBean> cardGradeDTOs) {
        this.cardGradeDTOs = cardGradeDTOs;
    }

    public List<CardServerItemsBean> getCardServerItems() {
        return cardServerItems;
    }

    public void setCardServerItems(List<CardServerItemsBean> cardServerItems) {
        this.cardServerItems = cardServerItems;
    }

    public List<CardCouponDTOs> getCardCouponDTOs() {
        return cardCouponDTOs;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public void setCardCouponDTOs(List<CardCouponDTOs> cardCouponDTOs) {
        this.cardCouponDTOs = cardCouponDTOs;
    }

    public List<CardPackagesBean> getCardPackages() {
        return cardPackages;
    }

    public void setCardPackages(List<CardPackagesBean> cardPackages) {
        this.cardPackages = cardPackages;
    }

    public static class CardStoreDTOsBean {

        private int storeId;
        private double distance;
        private String storename;
        private String location;
        private String picture;

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public static class CardOpusDTOsBean {
        /**
         * stylistOpusId : 1296
         * stylistOpusCovers : ""
         */

        private int stylistOpusId;
        private String stylistOpusCovers;

        public int getStylistOpusId() {
            return stylistOpusId;
        }

        public void setStylistOpusId(int stylistOpusId) {
            this.stylistOpusId = stylistOpusId;
        }

        public String getStylistOpusCovers() {
            return stylistOpusCovers;
        }

        public void setStylistOpusCovers(String stylistOpusCovers) {
            this.stylistOpusCovers = stylistOpusCovers;
        }
    }

    public static class CardGradeDTOsBean {
        /**
         * gradeType : 服务评分
         * point : 0
         * gradeDescrip : null
         */

        private String gradeType;
        private float point;
        private String gradeDescrip;

        public String getGradeType() {
            return gradeType;
        }

        public void setGradeType(String gradeType) {
            this.gradeType = gradeType;
        }

        public float getPoint() {
            return point;
        }

        public void setPoint(float point) {
            this.point = point;
        }

        public String getGradeDescrip() {
            return gradeDescrip;
        }

        public void setGradeDescrip(String gradeDescrip) {
            this.gradeDescrip = gradeDescrip;
        }
    }

    public static class CardServerItemsBean {


        /**
         * serviceId : 7
         * name : 吹
         * type : 1
         * price : 500
         */

        private int serviceId;
        private String name;
        private int type;
        private double price;

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "CardServerItemsBean{" +
                    "serviceId=" + serviceId +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    ", price=" + price +
                    '}';
        }
    }

    public static class CardPackagesBean {
        /**
         * packageId : 5520
         * name : 洗剪吹
         * price : 300
         */
        private int serviceId;
        private int packageId;
        private String name;
        private double price;

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public int getPackageId() {
            return packageId;
        }

        public void setPackageId(int packageId) {
            this.packageId = packageId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class CardCouponDTOs {

        /**
         * amount : 0
         * couponId : 0
         * deduction : 0
         * limited : string
         * quantity : 0
         * type : string
         * usePercent : 0
         * isDraw : true
         */

        private double amount;
        private int couponId;
        private double deduction;
        private String limited;
        private int quantity;
        private String type; // 优惠券类型 1 满减 2 折扣
        private double usePercent;
        private boolean isDraw;// 是否领取

        public boolean isDraw() {
            return isDraw;
        }

        public void setDraw(boolean draw) {
            isDraw = draw;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public double getDeduction() {
            return deduction;
        }

        public void setDeduction(double deduction) {
            this.deduction = deduction;
        }

        public String getLimited() {
            return limited;
        }

        public void setLimited(String limited) {
            this.limited = limited;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getUsePercent() {
            return usePercent;
        }

        public void setUsePercent(double usePercent) {
            this.usePercent = usePercent;
        }
    }

    @Override
    public String toString() {
        return "StylistDetailsBean{" +
                "stylistId=" + stylistId +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", lable='" + lable + '\'' +
                ", serverTypes='" + serverTypes + '\'' +
                ", star=" + star +
                ", point=" + point +
                ", gender=" + gender +
                ", headPortrait='" + headPortrait + '\'' +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", yearbirth='" + yearbirth + '\'' +
                ", description='" + description + '\'' +
                ", isCollection=" + isCollection +
                ", imusername='" + imusername + '\'' +
                ", cardStoreDTOs=" + cardStoreDTOs +
                ", cardOpusDTOs=" + cardOpusDTOs +
                ", cardGradeDTOs=" + cardGradeDTOs +
                ", cardServerItems=" + cardServerItems +
                ", cardCouponDTOs=" + cardCouponDTOs +
                ", cardPackages=" + cardPackages +
                '}';
    }
}
