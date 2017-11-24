package com.skymall.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.FlatButton;
import com.gxz.PagerSlidingTabStrip;
import com.skymall.GlobalFunctions;
import com.skymall.R;
import com.skymall.bean.Stores;
import com.skymall.fragments.StoreActivityPageFragment;
import com.skymall.fragments.StoreMainPageFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.mrapp.android.dialog.MaterialDialog;

public class StorePage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StorePage";
    private static final String STORE_INFO_INITIATED = "storeInfoInitiated";

    private FlatButton storeFollow;
    private TextView storeName,storeAdress,navAddress;
    private ImageView storeIcon, gotoNav;
    private PagerSlidingTabStrip slidingTab;
    private StoreMainPageFragment storeMainPageFragment;
    private StoreActivityPageFragment storeActivityPageFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager contentForTabs;
    private MaterialDialog listDialog;

    private Intent in;
    String storeID, latlng, address, name;

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case STORE_INFO_INITIATED:
                    initStoreInfo();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_page);
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
       // storeID = this.getIntent().getStringExtra("storeID");
        storeID = "kwuH000U";
        getStoreInfo();

        initViews();
    }

    private void initViews(){
        storeFollow = (FlatButton)findViewById(R.id.follow);
        storeName = (TextView)findViewById(R.id.store_name);
        storeAdress = (TextView)findViewById(R.id.store_adress);
        storeIcon = (ImageView)findViewById(R.id.store_icon);
        slidingTab = (PagerSlidingTabStrip)findViewById(R.id.store_slidingtabs);
        gotoNav = (ImageView)findViewById(R.id.gotonav);
        navAddress = (TextView)findViewById(R.id.nav_address);


        gotoNav.setOnClickListener(this);


        fragmentList.add(storeMainPageFragment = new StoreMainPageFragment());
        fragmentList.add(storeActivityPageFragment = new StoreActivityPageFragment());
//        contentForTabs.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
//                fragmentList, new String[]{"Homepage", "Activity"}));
//        contentForTabs.setOffscreenPageLimit(3);

    }

    private void initStoreInfo(){
        storeName.setText(name);
        storeAdress.setText(address);
        navAddress.setText(address);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotonav:
                pickNavApp();
                break;
        }
    }


    private void pickNavApp(){
        String baidumap = StorePage.this.getResources().getString(R.string.baidumap);
        String gaodemap = StorePage.this.getResources().getString(R.string.gaodemap);
        String googlemap = StorePage.this.getResources().getString(R.string.googlemap);

        MaterialDialog.Builder dialogBuilder = new de.mrapp.android.dialog.MaterialDialog.Builder(StorePage.this);

        dialogBuilder.setItems(new String[]{baidumap, gaodemap, googlemap,""}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        goByBaidu(latlng, address);
                        listDialog.dismiss();
                        break;
                    case 1:
                        goByGaode(latlng, address);
                        listDialog.dismiss();
                        break;
                    case 3:
                        goByGoogle(latlng, address);
                        listDialog.dismiss();
                        break;
                }
            }
        });

        listDialog = dialogBuilder.create();
        listDialog.setGravity(Gravity.CENTER);
        listDialog.show();
    }



    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }


    private void getStoreInfo(){
        BmobQuery<Stores> query = new BmobQuery<Stores>();
        query.addWhereEqualTo("objectId", storeID);
       //query.include("user,post.author");
       // query.order("createdAt");

        query.findObjects(new FindListener<Stores>() {
            @Override
            public void done(List<Stores> list, BmobException e) {
                if (e == null) {
                    for(Stores store : list){
                        latlng = store.getLatlng();
                        address = store.getAddress();
                        name = store.getName();
                        Message storeInfoInit = new Message();
                        storeInfoInit.obj = STORE_INFO_INITIATED;
                        handler.sendMessage(storeInfoInit);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    private void goByGaode(String latlng, String address){
        String[] location = latlng.split(",");
        //Assume latitude and longitude are not null in any case
        double[] laln =  bd09_To_Gcj02(Double.valueOf(location[0]),Double.valueOf(location[1]));
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + laln[0] + "&dlon=" + laln[0] + "&dname=" + "香港特别行政区深水埗区福荣街218号" + "&dev=0&m=0&t=1"));
        intent.addCategory("android.intent.category.DEFAULT");

        if(GlobalFunctions.isInstallByread("com.autonavi.minimap")){
            startActivity(intent);
        }else {
            Log.e(TAG, getResources().getString(R.string.amap_not_installed)) ;
        }
    }



    private void goByBaidu(String latlng, String address){
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&destination=latlng:" + latlng + "|name:" + address + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"));

        if(GlobalFunctions.isInstallByread("com.baidu.BaiduMap")){
            startActivity(intent);
        }else {
            Log.e(TAG, getResources().getString(R.string.baidumap_not_installed)) ;
        }
    }

    private void goByGoogle(String latlng, String address){
        String[] location = latlng.split(",");
        //Assume latitude and longitude are not null in any case
        double[] laln =  bd09_To_Gcj02(Double.valueOf(location[0]),Double.valueOf(location[1]));

        if (GlobalFunctions.isInstallByread("com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+laln[0]+","+laln[1]+", + "+ address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }else {
            Log.e(TAG, getResources().getString(R.string.Googlemap_not_installed));
        }
    }



}

