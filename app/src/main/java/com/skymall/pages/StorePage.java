package com.skymall.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.FlatButton;
import com.skymall.R;
import com.skymall.adapters.ItemTitlePagerAdapter;
import com.skymall.fragments.StoreActivityPageFragment;
import com.skymall.fragments.StoreMainPageFragment;
import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class StorePage extends AppCompatActivity implements View.OnClickListener {

    private FlatButton storeFollow;
    private TextView storeName,storeAdress;
    private ImageView storeIcon;
    private PagerSlidingTabStrip slidingTab;
    private StoreMainPageFragment storeMainPageFragment;
    private StoreActivityPageFragment storeActivityPageFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager contentForTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_page);
        initViews();
    }

    private void initViews(){
        storeFollow = (FlatButton)findViewById(R.id.follow);
        storeName = (TextView)findViewById(R.id.store_name);
        storeAdress = (TextView)findViewById(R.id.store_adress);
        storeIcon = (ImageView)findViewById(R.id.store_icon);
        slidingTab = (PagerSlidingTabStrip)findViewById(R.id.store_slidingtabs);

        fragmentList.add(storeMainPageFragment = new StoreMainPageFragment());
        fragmentList.add(storeActivityPageFragment = new StoreActivityPageFragment());
        contentForTabs.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"Homepage", "Activity"}));
        contentForTabs.setOffscreenPageLimit(3);

    }




    @Override
    public void onClick(View v) {

    }
}
