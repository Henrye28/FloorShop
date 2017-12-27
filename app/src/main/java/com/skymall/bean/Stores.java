package com.skymall.bean;

import cn.bmob.v3.BmobObject;

public class Stores extends BmobObject {
    private String storeId;
    private String latlng;
    private String address;
    private String name;


    public String getStoreId(){
        return storeId;
    }

    public String getName(){
        return name;
    }

    public String getLatlng(){
        return latlng;
    }

    public String getAddress(){
        return address;
    }


}
