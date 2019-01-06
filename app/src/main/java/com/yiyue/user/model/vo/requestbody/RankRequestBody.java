package com.yiyue.user.model.vo.requestbody;

/**
 * Created by wqy on 2018/11/18
 */
public class RankRequestBody {

    private String cityGDId;
    private String distance;
    private String latitude;
    private String longitude;
    private int page;
    private int size;
    private int sort;
    private int type;

    private RankRequestBody(Builder builder) {
        cityGDId = builder.cityGDId;
        distance = builder.distance;
        latitude = builder.latitude;
        longitude = builder.longitude;
        page = builder.page;
        size = builder.size;
        sort = builder.sort;
        type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String cityGDId;
        private String distance;
        private String latitude;
        private String longitude;
        private int page;
        private int size;
        private int sort;
        private int type;

        private Builder() {
        }

        public Builder cityGDId(String val) {
            cityGDId = val;
            return this;
        }

        public Builder distance(String val) {
            distance = val;
            return this;
        }

        public Builder latitude(String val) {
            latitude = val;
            return this;
        }

        public Builder longitude(String val) {
            longitude = val;
            return this;
        }

        public Builder page(int val) {
            page = val;
            return this;
        }

        public Builder size(int val) {
            size = val;
            return this;
        }

        public Builder sort(int val) {
            sort = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public RankRequestBody build() {
            return new RankRequestBody(this);
        }
    }
}
