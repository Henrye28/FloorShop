package com.example.henryye.floorshop.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ItemPics extends BmobObject {
    Items item;
    BmobFile pic;

    public Items getItems(){
        return item;
    }

    public BmobFile getPic(){
        return pic;
    }

}
