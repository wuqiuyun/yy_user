package com.yiyue.user.model.vo.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2018/12/21
 */
public class CouponDetailsBean implements Serializable {

    /**
     * id : 153
     * userId : 111111145
     * packageId : 1950
     * stylistId : 1370
     * stocktimes : 9
     * usetimes : 1
     * totaltimes : 10
     * amount : 10
     * payamount : 8.8
     * packagetimes : 1
     * createtime : 1545375056000
     * updatetime : 1545375135000
     */

    private int id;
    private int userId;
    private int packageId;
    private int stylistId;
    private int stocktimes;
    private int usetimes;
    private int totaltimes;
    private int amount;
    private double payamount;
    private int packagetimes;
    private long createtime;
    private long updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public int getStocktimes() {
        return stocktimes;
    }

    public void setStocktimes(int stocktimes) {
        this.stocktimes = stocktimes;
    }

    public int getUsetimes() {
        return usetimes;
    }

    public void setUsetimes(int usetimes) {
        this.usetimes = usetimes;
    }

    public int getTotaltimes() {
        return totaltimes;
    }

    public void setTotaltimes(int totaltimes) {
        this.totaltimes = totaltimes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPayamount() {
        return payamount;
    }

    public void setPayamount(double payamount) {
        this.payamount = payamount;
    }

    public int getPackagetimes() {
        return packagetimes;
    }

    public void setPackagetimes(int packagetimes) {
        this.packagetimes = packagetimes;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
}
