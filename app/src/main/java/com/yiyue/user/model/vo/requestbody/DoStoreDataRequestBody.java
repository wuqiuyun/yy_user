package com.yiyue.user.model.vo.requestbody;

import android.text.TextUtils;

import com.yiyue.user.component.toast.ToastUtils;

/**
 * Created by zm on 2018/10/18.
 */
public class DoStoreDataRequestBody {

          private int cityId; // 城市id
          private int districtId; // 区id
          private double latitude; // 维度
          private String location; // 位置信息
          private double longitude; // 经度
          private String mobile; // 手机号码
          private int provinceId; // 省份id
          private String storename; // 门店名称
          private String userId; // 用户id

    /**
     * 检查参数
     * @return
     */
    public boolean checkParams() {
        if (TextUtils.isEmpty(storename)) {
            ToastUtils.shortToast("门店名称不能为空");
            return false;
        }
        if (TextUtils.isEmpty(location)) {
            ToastUtils.shortToast("信息地址不能为空");
            return false;
        }
        return true;
    }


    private DoStoreDataRequestBody(Builder builder) {
        cityId = builder.cityId;
        districtId = builder.districtId;
        latitude = builder.latitude;
        location = builder.location;
        longitude = builder.longitude;
        mobile = builder.mobile;
        provinceId = builder.provinceId;
        storename = builder.storename;
        userId = builder.userId;
    }


    public static final class Builder {
        private int cityId;
        private int districtId;
        private double latitude;
        private String location;
        private double longitude;
        private String mobile;
        private int provinceId;
        private String storename;
        private String userId;

        public Builder() {
        }

        public Builder cityId(int val) {
            cityId = val;
            return this;
        }

        public Builder districtId(int val) {
            districtId = val;
            return this;
        }

        public Builder latitude(double val) {
            latitude = val;
            return this;
        }

        public Builder location(String val) {
            location = val;
            return this;
        }

        public Builder longitude(double val) {
            longitude = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public Builder provinceId(int val) {
            provinceId = val;
            return this;
        }

        public Builder storename(String val) {
            storename = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public DoStoreDataRequestBody build() {
            return new DoStoreDataRequestBody(this);
        }
    }
}
