package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * Created by lvlong on 2018/11/12.
 */
public class ServiceDetailesBean {

    /**
     * servicename : 染发
     * description :  这是服务描述  
     * price : null
     * serviceOptions : [{"optionId":1,"optiontitle":"短发","serviceOptionDetails":[{"serviceOptionId":3921,"optionvalue":"短发药水1","price":33}]},{"optionId":2,"optiontitle":"中发","serviceOptionDetails":[{"serviceOptionId":3922,"optionvalue":"中发药水1","price":44}]},{"optionId":3,"optiontitle":"长发","serviceOptionDetails":[{"serviceOptionId":3923,"optionvalue":"长发药水1","price":55}]}]
     */

    private String servicename;
    private String description;
    private String price;
    private String picture;
    private List<ServiceOptionsBean> serviceOptions;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ServiceOptionsBean> getServiceOptions() {
        return serviceOptions;
    }

    public void setServiceOptions(List<ServiceOptionsBean> serviceOptions) {
        this.serviceOptions = serviceOptions;
    }

    public static class ServiceOptionsBean {
        /**
         * optionId : 1
         * optiontitle : 短发
         * serviceOptionDetails : [{"serviceOptionId":3921,"optionvalue":"短发药水1","price":33}]
         */

        private int optionId;
        private String optiontitle;
        private List<ServiceOptionDetailsBean> serviceOptionDetails;

        public int getOptionId() {
            return optionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public String getOptiontitle() {
            return optiontitle;
        }

        public void setOptiontitle(String optiontitle) {
            this.optiontitle = optiontitle;
        }

        public List<ServiceOptionDetailsBean> getServiceOptionDetails() {
            return serviceOptionDetails;
        }

        public void setServiceOptionDetails(List<ServiceOptionDetailsBean> serviceOptionDetails) {
            this.serviceOptionDetails = serviceOptionDetails;
        }

        public static class ServiceOptionDetailsBean {
            /**
             * serviceOptionId : 3921
             * optionvalue : 短发药水1
             * price : 33
             */

            private int serviceOptionId;
            private String optionvalue;
            private String price;

            public int getServiceOptionId() {
                return serviceOptionId;
            }

            public void setServiceOptionId(int serviceOptionId) {
                this.serviceOptionId = serviceOptionId;
            }

            public String getOptionvalue() {
                return optionvalue;
            }

            public void setOptionvalue(String optionvalue) {
                this.optionvalue = optionvalue;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
