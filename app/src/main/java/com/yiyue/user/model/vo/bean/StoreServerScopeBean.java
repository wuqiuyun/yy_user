package com.yiyue.user.model.vo.bean;

import java.util.List;

/**
 * 门店服务范围
 * Created by wqy on 2018/11/8.
 */

public class StoreServerScopeBean {

    /**
     * catergoryNames : ["洗剪吹","烫发","染发","接发","烫染拉"]
     * storeId : 4
     */

    private int storeId;
    private List<String> catergoryNames;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<String> getCatergoryNames() {
        return catergoryNames;
    }

    public void setCatergoryNames(List<String> catergoryNames) {
        this.catergoryNames = catergoryNames;
    }
}
