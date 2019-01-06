package com.yiyue.user.model.vo.requestbody;

import java.util.List;

/**
 * Created by zm on 2018/11/10.
 */
public class OrderCommentRequestBody {

    private String stylistComment;
    private int stylistComp;
    private int stylistServer;
    private int skill;
    private String orderId;
    private String userId;
    private String storeComment;
    private List<String> storeImgPaths;
    private int storeCom;
    private int storeServer;
    private int storeEnv;
    private List<String> stylistImgPaths;


    public boolean checkParams() {
        return true;
    }

    private OrderCommentRequestBody(Builder builder) {
        stylistComment = builder.stylistComment;
        stylistComp = builder.stylistComp;
        stylistServer = builder.stylistServer;
        skill = builder.skill;
        orderId = builder.orderId;
        userId = builder.userId;
        storeComment = builder.storeComment;
        storeImgPaths = builder.storeImgPaths;
        storeCom = builder.storeCom;
        storeServer = builder.storeServer;
        storeEnv = builder.storeEnv;
        stylistImgPaths = builder.stylistImgPaths;
    }


    public static final class Builder {
        private String stylistComment;
        private int stylistComp;
        private int stylistServer;
        private int skill;
        private String orderId;
        private String userId;
        private String storeComment;
        private List<String> storeImgPaths;
        private int storeCom;
        private int storeServer;
        private int storeEnv;
        private List<String> stylistImgPaths;

        public Builder() {
        }

        public Builder stylistComment(String val) {
            stylistComment = val;
            return this;
        }

        public Builder stylistComp(int val) {
            stylistComp = val;
            return this;
        }

        public Builder stylistServer(int val) {
            stylistServer = val;
            return this;
        }

        public Builder skill(int val) {
            skill = val;
            return this;
        }

        public Builder orderId(String val) {
            orderId = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder storeComment(String val) {
            storeComment = val;
            return this;
        }

        public Builder storeImgPaths(List<String> val) {
            storeImgPaths = val;
            return this;
        }

        public Builder storeCom(int val) {
            storeCom = val;
            return this;
        }

        public Builder storeServer(int val) {
            storeServer = val;
            return this;
        }

        public Builder storeEnv(int val) {
            storeEnv = val;
            return this;
        }

        public Builder stylistImgPaths(List<String> val) {
            stylistImgPaths = val;
            return this;
        }

        public OrderCommentRequestBody build() {
            return new OrderCommentRequestBody(this);
        }
    }
}
