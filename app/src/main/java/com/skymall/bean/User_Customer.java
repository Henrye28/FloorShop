package com.skymall.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by henryye on 5/11/17.
 */
public class User_Customer extends BmobUser {
    public String name;
    private String password;
    private String email;
    //private String mobile;

    public User_Customer(String name, String password, String email){
        this.email = email;
        this.password = password;
        this. name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
