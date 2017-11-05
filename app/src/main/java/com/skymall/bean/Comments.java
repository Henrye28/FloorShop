package com.skymall.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Comments extends BmobObject {
    private BmobUser user;
    private Items item;
    private String content;


    public String getContent(){
        return content;
    }

    public Items getItem(){
        return item;
    }

    public BmobUser getUser(){
        return user;
    }

}