package com.yiyue.user.model.vo.bean;

/**
 * Created by zm on 2018/11/12.
 */
public class DayBean {
    String week; // 星期
    String date; // 时间
    String date1; // 时间

    public DayBean(String week, String date, String date1) {
        this.week = week;
        this.date1 = date1;
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}
