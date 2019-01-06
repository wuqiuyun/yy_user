package com.yiyue.user.model.vo.requestbody;

/**
 * Created by zm on 2018/11/8.
 */
public class CreateOrderRequestBody {

    private int couponId;
    private int couponamount;
    private String coupondirection;
    private String coupontype;
    private int day;
    private int orderamount;
    private int packageId;
    private int serviceId;
    private String servicemodel;
    private int storeId;
    private int stylistId;
    private String usetime;

    private CreateOrderRequestBody(Builder builder) {
        couponId = builder.couponId;
        couponamount = builder.couponamount;
        coupondirection = builder.coupondirection;
        coupontype = builder.coupontype;
        day = builder.day;
        orderamount = builder.orderamount;
        packageId = builder.packageId;
        serviceId = builder.serviceId;
        servicemodel = builder.servicemodel;
        storeId = builder.storeId;
        stylistId = builder.stylistId;
        usetime = builder.usetime;
    }


    public static final class Builder {
        private int couponId;
        private int couponamount;
        private String coupondirection;
        private String coupontype;
        private int day;
        private int orderamount;
        private int packageId;
        private int serviceId;
        private String servicemodel;
        private int storeId;
        private int stylistId;
        private String usetime;

        public Builder() {
        }

        public Builder couponId(int val) {
            couponId = val;
            return this;
        }

        public Builder couponamount(int val) {
            couponamount = val;
            return this;
        }

        public Builder coupondirection(String val) {
            coupondirection = val;
            return this;
        }

        public Builder coupontype(String val) {
            coupontype = val;
            return this;
        }

        public Builder day(int val) {
            day = val;
            return this;
        }

        public Builder orderamount(int val) {
            orderamount = val;
            return this;
        }

        public Builder packageId(int val) {
            packageId = val;
            return this;
        }

        public Builder serviceId(int val) {
            serviceId = val;
            return this;
        }

        public Builder servicemodel(String val) {
            servicemodel = val;
            return this;
        }

        public Builder storeId(int val) {
            storeId = val;
            return this;
        }

        public Builder stylistId(int val) {
            stylistId = val;
            return this;
        }

        public Builder usetime(String val) {
            usetime = val;
            return this;
        }

        public CreateOrderRequestBody build() {
            return new CreateOrderRequestBody(this);
        }
    }
}
