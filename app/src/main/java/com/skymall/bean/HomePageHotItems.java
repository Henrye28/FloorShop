package com.skymall.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class HomePageHotItems extends BmobObject {
    private String imageUrl;
    private String name;
    private BmobFile bitmap;

    public HomePageHotItems(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public BmobFile getBitmap(){
        return bitmap;
    }
}
