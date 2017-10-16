package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;

public class Items extends BmobObject {
    private Stores store;
    private String classification;
    private String name;
    private double price;
    private String description;
    private String attributes;

    public String getDescription(){ return description;}
    public String getClassification() { return classification;}
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Stores getStore(){ return store; }
    public String getAttributes(){ return attributes; }

}
