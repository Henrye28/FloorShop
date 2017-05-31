package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by henryye on 5/11/17.
 */
public class Items extends BmobObject {
    private long storeID;
    private long itemId;
    public String type;
    public String name;
    public double price;
    public String description;


    Items(long storeID, String type, String name, double price){
        this.storeID = storeID;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    Items(long storeID, String type, String name, double price, String description){
        this.storeID = storeID;
        this.type = type;
        this.name = name;
        this.price = price;
        this.description = description;
    }


    public void setDescription(String description){
        this.description = description;
    }



}
