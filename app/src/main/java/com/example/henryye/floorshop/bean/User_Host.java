package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by henryye on 5/11/17.
 */
public class User_Host extends BmobObject{
    private String password;
    private String username;
    private String emailOrPhone;
    private long storeID;

    User_Host(String password, String emailOrPhone, String username, long storeID){
        this.username = username;
        this.password = password;
        this.storeID = storeID;
        this.password = password;
    }

}
