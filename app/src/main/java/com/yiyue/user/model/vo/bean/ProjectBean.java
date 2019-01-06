package com.yiyue.user.model.vo.bean;

/**
 * Created by lvlong on 2018/10/12.
 */
public class ProjectBean {
    private String label;
    private boolean checked;
    private boolean enabled;
    private int id;
    private String price;
    private String parentId;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ServiceSettingBean{" +
                "label='" + label + '\'' +
                ", checked=" + checked +
                ", parentId=" + parentId +
                '}';
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ProjectBean(boolean checked) {
        this.checked = checked;
    }

    public ProjectBean(String label, boolean checked) {
        this.label = label;
        this.checked = checked;
    }
    public ProjectBean(String label, boolean checked, boolean enabled) {
        this.label = label;
        this.checked = checked;
        this.enabled = enabled;
    }
    public ProjectBean(String label, boolean checked, int id ,String price) {
        this.label = label;
        this.checked = checked;
        this.id = id;
        this.price = price;
    }
    public ProjectBean(String label, boolean checked, int id) {
        this.label = label;
        this.checked = checked;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
