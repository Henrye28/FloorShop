package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by henryye on 5/11/17.
 */
public class User_Customer extends BmobObject {
    public String name;
    private String password;
    private String emailOrPhone;


    User_Customer(String name, String password, String emailOrPhone){
        this.emailOrPhone = emailOrPhone;
        this.password = password;
        this. name = name;
    }

}
