package com.yiyue.user.model.vo.bean;

/**
 *
 * Created by wqy on 2018/11/14.
 */

public class ServiceCouponBean {

    /**
     * amount : 0
     * couponId : 0
     * deduction : 0
     * limited : string
     * quantity : 0
     * stylistId : 0
     * stylistName : string
     * type : string
     * useend : string
     * usestart : string
     * validend : 2018-11-14T07:38:16.115Z
     * validstart : 2018-11-14T07:38:16.115Z
     */

    private double amount;
    private int couponId;
    private float deduction;
    private int limited;
    private int quantity;
    private int stylistId;
    private String stylistName;
    private String type;
    private String useend;
    private String usestart;
    private String validend;
    private String validstart;

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

    public float getDeduction() {
        return deduction;
    }

    public void setDeduction(float deduction) {
        this.deduction = deduction;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseend() {
        return useend;
    }

    public void setUseend(String useend) {
        this.useend = useend;
    }

    public String getUsestart() {
        return usestart;
    }

    public void setUsestart(String usestart) {
        this.usestart = usestart;
    }

    public String getValidend() {
        return validend;
    }

    public void setValidend(String validend) {
        this.validend = validend;
    }

    public String getValidstart() {
        return validstart;
    }

    public void setValidstart(String validstart) {
        this.validstart = validstart;
    }

    @Override
    public String toString() {
        return "ServiceCouponBean{" +
                "amount=" + amount +
                ", couponId=" + couponId +
                ", deduction=" + deduction +
                ", limited='" + limited + '\'' +
                ", quantity=" + quantity +
                ", stylistId=" + stylistId +
                ", stylistName='" + stylistName + '\'' +
                ", type='" + type + '\'' +
                ", useend='" + useend + '\'' +
                ", usestart='" + usestart + '\'' +
                ", validend='" + validend + '\'' +
                ", validstart='" + validstart + '\'' +
                '}';
    }
}
