package com.yiyue.user.model.vo.bean;

/**
 * Created by zm on 2018/11/12.
 */
public class TimeBean {
    private String time;
    private boolean lock;
    private boolean checked;

    @Override
    public String toString() {
        return "TimeBean{" +
                "time='" + time + '\'' +
                ", lock=" + lock +
                ", checked=" + checked +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
