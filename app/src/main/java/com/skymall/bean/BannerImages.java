package com.skymall.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class BannerImages extends BmobObject{
    private BmobFile pic;
    private String page;
    private Items item;
    private String storeID;

    public String getStoreID(){
        return storeID;
    }

    public Items getItem(){
        return item;
    }

    public BmobFile getPic(){
        return pic;
    }

    public String getPage() { return page; }


}
