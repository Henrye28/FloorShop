package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Items extends BmobObject {

    private Stores store;
    private String classification;
    private String name;
    private double price;
    private String description;
    private String attributes;
    private BmobFile cover;

    public String getDescription(){ return description;}
    public String getClassification() { return classification;}
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Stores getStore(){ return store; }
    public String getAttributes(){ return attributes; }
    public BmobFile getCover() {return cover;}

    public Items() {}

    public Items(Stores store, String classification, String name, double price, String description, String attributes, BmobFile cover) {
        this.store = store;
        this.classification = classification;
        this.name = name;
        this.price = price;
        this.description = description;
        this.attributes = attributes;
        this.cover = cover;
    }
}
