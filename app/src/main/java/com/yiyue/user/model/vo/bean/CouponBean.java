package com.yiyue.user.model.vo.bean;

/**
 * Created by wqy on 2018/11/1.
 */

public class CouponBean {

    /**
     * id : 478
     * amount : 100
     * couponId : 1
     * createtime : 2018-10-30 19:36:40
     * deduction : null
     * limited : 0
     * stylistId : 1
     * type : 1
     * updatetime : 2018-11-05 21:14:17
     * useend : 14:33:20
     * userId : 1
     * usestart : 14:33:17
     * validtime : 2018-10-19 14:33:12
     * stylistName : 喵喵喵
     * direction : 2
     * status : 1
     */

    private int id;
    private double amount;
    private int couponId;
    private String createtime;
    private String deduction;
    private int limited;
    private String stylistId;
    private int type;
    private String updatetime;
    private String useend;
    private int userId;
    private String usestart;
    private String validtime;
    private String stylistName;
    private int direction;
    private int status;
    private String validstart;
    private String validend;
    private String serviceName;
    private String packageId;
    private String stocktimes;
    private String stylistPhoto;
    private String categoryname;
    private String times;
    private String costprice;
    private String price;
    private String serviceId;
    private String headportrait;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCostprice() {
        return costprice;
    }

    public void setCostprice(String costprice) {
        this.costprice = costprice;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getStylistPhoto() {
        return stylistPhoto;
    }

    public void setStylistPhoto(String stylistPhoto) {
        this.stylistPhoto = stylistPhoto;
    }

    public String getStocktimes() {
        return stocktimes;
    }

    public void setStocktimes(String stocktimes) {
        this.stocktimes = stocktimes;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getValidstart() {
        return validstart;
    }

    public void setValidstart(String validstart) {
        this.validstart = validstart;
    }

    public String getValidend() {
        return validend;
    }

    public void setValidend(String validend) {
        this.validend = validend;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * servicePackage : {"id":3727,"costprice":999,"createtime":"2018-11-02 17:16:30","price":999,"serviceId":687,"stylistId":1,"times":33,"type":1,"updatetime":"2018-11-02 17:16:30"}
     */

    private ServicePackageBean servicePackage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUseend() {
        return useend;
    }

    public void setUseend(String useend) {
        this.useend = useend;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsestart() {
        return usestart;
    }

    public void setUsestart(String usestart) {
        this.usestart = usestart;
    }

    public String getValidtime() {
        return validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ServicePackageBean getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageBean servicePackage) {
        this.servicePackage = servicePackage;
    }


    public static class ServicePackageBean {
        /**
         * id : 3727
         * costprice : 999
         * createtime : 2018-11-02 17:16:30
         * price : 999
         * serviceId : 687
         * stylistId : 1
         * times : 33
         * type : 1
         * updatetime : 2018-11-02 17:16:30
         */

        private int id;
        private double costprice;
        private String createtime;
        private String price;
        private int serviceId;
        private int stylistId;
        private String times;
        private int type;
        private String updatetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getCostprice() {
            return costprice;
        }

        public void setCostprice(double costprice) {
            this.costprice = costprice;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public int getStylistId() {
            return stylistId;
        }

        public void setStylistId(int stylistId) {
            this.stylistId = stylistId;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
    }
}
