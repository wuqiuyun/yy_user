package com.yiyue.user.model.vo.bean;

import com.hyphenate.chat.EMMessage;

/**
 * Created by Lizhuo on 2018/10/12.
 * 
 */
public class EventBean
{
    //语音聊天使用
    public static class VoiceCallEventBean {
        private int tag;
        private Object data;

        public VoiceCallEventBean() {

        }

        public VoiceCallEventBean(int tag) {
            this.tag = tag;
        }

        public VoiceCallEventBean(int tag, Object data) {
            this.tag = tag;
            this.data = data;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    //群介绍修改
    public static class GroupIntroduce {
        private String introduce;

        public GroupIntroduce(String introduce)
        {
            this.introduce = introduce;
        }

        public String getIntroduce()
        {
            return introduce;
        }

        public void setIntroduce(String introduce)
        {
            this.introduce = introduce;
        }
    }

    /**
     * 群更新
     * tag 0：更新或转让 1：解散
     */
    public static class GroupUpdate {
        private int tag;//0：更新或转让 1：解散

        public GroupUpdate(int tag)
        {
            this.tag = tag;
        }

        public int getTag()
        {
            return tag;
        }

        public void setTag(int tag)
        {
            this.tag = tag;
        }
    }

    /**
     * 绑定推荐码成功
     */
    public static class BandingRefereesSuc {

    }

    /**
     * 修改个人资料成功(1昵称 2职业 3发长 4脸型)
     */
    public static class UpdateUserSuc {
        private int tag;// 1昵称 2职业 3发长 4脸型
        private String name;
        private String job;
        private int hair;
        private int face;

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getHair() {
            return hair;
        }

        public void setHair(int hair) {
            this.hair = hair;
        }

        public int getFace() {
            return face;
        }

        public void setFace(int face) {
            this.face = face;
        }
    }

    /**
     * 优惠券添加成功，优惠券页面刷新
     * tag 0：更新
     */
    public static class CouponsListUpdate {
        private int tag;//0：更新

        public CouponsListUpdate(int tag)
        {
            this.tag = tag;
        }

        public int getTag()
        {
            return tag;
        }

        public void setTag(int tag)
        {
            this.tag = tag;
        }
    }

    /**
     * 进入群或者退出群
     * tag 0：进入 1：退出
     */
    public static class GroupJoinOrOut {
        private int tag;
        private long id;

        public GroupJoinOrOut(int tag, long id) {
            this.tag = tag;
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }

    //好友列表变化使用
    public static class FriendChangeEventBean {
        private int tag;
        private Object data;

        public FriendChangeEventBean() {

        }

        public FriendChangeEventBean(int tag) {
            this.tag = tag;
        }

        public FriendChangeEventBean(int tag, Object data) {
            this.tag = tag;
            this.data = data;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    /**
     * 群组列表刷新UI
     */
    public static class GroupChangeEvent {
        private int tag;//1刷新

        public GroupChangeEvent(int tag) {
            this.tag = tag;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }

    /**
     * 会话列表刷新UI
     */
    public static class ConversationRefreshEvent {
        private int tag;//1只刷新现有会话列表的对话信息  2刷新 新会话

        public ConversationRefreshEvent(int tag) {
            this.tag = tag;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }

    /**
     * 好友信息更新
     * tag 0：更新
     */
    public static class FriendInfoUpdate {
        private int tag;//0：更新

        public FriendInfoUpdate(int tag) {
            this.tag = tag;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }
    /**
     * 发送红包后聊天消息页面更新
     * tag 0：更新
     */
    public static class RedPacketMsgUpdate {
        private int tag;//0：更新
        private SelfDefinedInfoBean chatNoFriendBean;
        private EMMessage message;
        private int msgType;

        public RedPacketMsgUpdate(int tag, SelfDefinedInfoBean selfDefinedInfoBean, EMMessage message, int msgType) {
            this.tag = tag;
            this.chatNoFriendBean = selfDefinedInfoBean;
            this.message = message;
            this.msgType = msgType;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public SelfDefinedInfoBean getChatNoFriendBean() {
            return chatNoFriendBean;
        }

        public void setChatNoFriendBean(SelfDefinedInfoBean chatNoFriendBean) {
            this.chatNoFriendBean = chatNoFriendBean;
        }

        public EMMessage getMessage() {
            return message;
        }

        public void setMessage(EMMessage message) {
            this.message = message;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }
    }

    /**
     * 邀请美发师同意拒绝后聊天消息页面更新
     * tag 0：更新
     */
    public static class InviteMsgUpdate {
        private int tag;//0：更新
        private SelfDefinedInfoBean selfDefinedInfoBean;
        private EMMessage message;
        private int msgType;

        public InviteMsgUpdate(int tag, SelfDefinedInfoBean selfDefinedInfoBean, EMMessage message, int msgType) {
            this.tag = tag;
            this.selfDefinedInfoBean = selfDefinedInfoBean;
            this.message = message;
            this.msgType = msgType;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public SelfDefinedInfoBean getSelfDefinedInfoBean() {
            return selfDefinedInfoBean;
        }

        public void setSelfDefinedInfoBean(SelfDefinedInfoBean selfDefinedInfoBean) {
            this.selfDefinedInfoBean = selfDefinedInfoBean;
        }

        public EMMessage getMessage() {
            return message;
        }

        public void setMessage(EMMessage message) {
            this.message = message;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }
    }

    /**
     * 有新推送消息
     */
    public static class NewMessage {
        
    }

    /**
     * 加价支付成功
     */
    public static class AddMoneyPaySuccess {

    }

    /**
     * 有新的好友/群申请 or 申请状态更新
     */
    public static class NewImMessage {

    }

    /**
     * 有新的订单消息
     */
    public static class NewOrderMessage {

    }

    /**
     * 有新的系统提醒消息
     */
    public static class NewSysMessage {

    }

    public static class ServiceComplete {

    }
}
