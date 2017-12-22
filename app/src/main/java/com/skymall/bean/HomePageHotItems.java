package com.skymall.bean;

import android.graphics.Bitmap;

import java.util.BitSet;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class HomePageHotItems extends BmobObject {
    private String imageUrl;
    private String text;
    private BmobFile bitmap;

    public HomePageHotItems(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public BmobFile getBitmap(){
        return bitmap;
    }
}
