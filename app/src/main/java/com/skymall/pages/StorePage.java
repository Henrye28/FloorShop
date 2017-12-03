package com.skymall.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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


    private Intent in;
    String storeID, latlng, address, name;


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
                GlobalFunctions.pickNavApp(latlng, address, StorePage.this);
                break;
        }
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
                    for (Stores store : list) {
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












}

