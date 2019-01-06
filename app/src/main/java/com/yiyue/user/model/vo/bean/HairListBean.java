package com.yiyue.user.model.vo.bean;

/**
 * Created by wqy on 2018/11/17.
 */

public class HairListBean {

    /**
     * id : 1
     * createtime : 2018-10-17T12:36:26.000+0000
     * describe : changfa
     * name : 长发
     * updatetime : 2018-10-17T12:36:34.000+0000
     */

    private int id;
    private String createtime;
    private String describe;
    private String name;
    private String updatetime;

    public HairListBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "HairListBean{" +
                "id=" + id +
                ", createtime='" + createtime + '\'' +
                ", describe='" + describe + '\'' +
                ", name='" + name + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
