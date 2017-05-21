package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by henryye on 5/11/17.
 */
public class User extends BmobUser {
    private String password;
    private String username;
    private String mobile;
    private long storeID;

    public User(String mobile, String password){
        this.password = password;
        this.mobile = mobile;
    }

    public long getStoreID() {
        return storeID;
    }

    public void setStoreID(long storeID) {
        this.storeID = storeID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
