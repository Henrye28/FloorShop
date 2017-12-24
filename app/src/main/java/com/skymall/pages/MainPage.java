package com.skymall.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skymall.R;
import com.skymall.fragments.searchDrawer.DrawerView;
import com.skymall.fragments.searchDrawer.ICallBack;
import com.skymall.fragments.searchDrawer.bCallBack;
import com.skymall.interfaces.IBtnCallListener;
import com.skymall.widgets.PageTopBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPage extends AppCompatActivity implements OnClickListener,IBtnCallListener {

    private ImageView[] switchImage;
    private TextView[] switchText;

    private HomepageTab homepageTab;
    private MyInfo_Tab myInfoTab;

    private FragmentManager manager = getSupportFragmentManager();

    @BindView(R.id.mainpage_topbar)
    PageTopBar topBar;

    @BindView(R.id.mainpage_view)
    DrawerLayout mainView;

    @BindView(R.id.mainpage_drawer_view)
    DrawerView drawer_view;

    @BindView(R.id.mainpage_tab_home)
    LinearLayout homeButton;

    @BindView(R.id.mainpage_tab_discover)
    LinearLayout discoverButton;

    @BindView(R.id.mainpage_tab_profile)
    LinearLayout profileButton;

    @BindView(R.id.mainpage_tab_home_img)
    ImageView homeButtonImg;

    @BindView(R.id.mainpage_tab_home_text)
    TextView homeButtonText;

    @BindView(R.id.mainpage_tab_discover_img)
    ImageView discoverButtonImg;

    @BindView(R.id.mainpage_tab_discover_text)
    TextView discoverButtonText;

    @BindView(R.id.mainpage_tab_profile_img)
    ImageView profileButtonImg;

    @BindView(R.id.mainpage_tab_profile_text)
    TextView profileButtonText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        getSaveData();
        initView();
    }

    private void getSaveData() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        SharedPreferences sp = getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
        int size = sp.getInt("ArrayCart_size", 0);
        for (int i = 0; i < size; i++) {
            hashMap.put("type", sp.getString("ArrayCart_type_" + i, ""));
            hashMap.put("color", sp.getString("ArrayCart_color_" + i, ""));
            hashMap.put("num", sp.getString("ArrayCart_num_" + i, ""));
        }
    }

    private void initView() {

        ButterKnife.bind(this);
        setSupportActionBar(topBar);
        topBar.showHomepageView();

//        topBar.showCommunityView();
//        topBar.showMeView();

        switchImage = new ImageView[]{homeButtonImg, discoverButtonImg, profileButtonImg};
        switchText = new TextView[] {homeButtonText, discoverButtonText, profileButtonText};

        if (homepageTab == null)
            homepageTab = new HomepageTab();
        if (myInfoTab == null)
            myInfoTab = new MyInfo_Tab();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainpage_show_layout, homepageTab);
        transaction.add(R.id.mainpage_show_layout, myInfoTab);
        transaction.show(homepageTab);
        transaction.hide(myInfoTab);
        transaction.commit();
        switchImage[0].setImageResource(R.drawable.icon_home_filled);
        switchImage[1].setImageResource(R.drawable.icon_discover);
        switchImage[2].setImageResource(R.drawable.icon_profile);

        topBar.setSearchViewListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.openDrawer(GravityCompat.START);
            }
        });

        drawer_view.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

            }
        });

        drawer_view.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                mainView.closeDrawer(drawer_view);
            }
        });

        homeButton.setOnClickListener(this);
        discoverButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainpage_tab_home:
                if (homepageTab == null)
                    homepageTab = new HomepageTab();
                FragmentTransaction transaction_home = manager.beginTransaction();
                transaction_home.show(homepageTab);
                transaction_home.hide(myInfoTab);
                transaction_home.commit();
                switchImage[0].setImageResource(R.drawable.icon_home_filled);
                switchImage[1].setImageResource(R.drawable.icon_discover);
                switchImage[2].setImageResource(R.drawable.icon_profile);
                break;
            case R.id.mainpage_tab_profile:
                if (myInfoTab == null)
                    myInfoTab = new MyInfo_Tab();
                FragmentTransaction transaction_discover = manager.beginTransaction();
                transaction_discover.show(myInfoTab);
                transaction_discover.hide(homepageTab);
                transaction_discover.commit();
                switchImage[0].setImageResource(R.drawable.icon_home);
                switchImage[1].setImageResource(R.drawable.icon_discover);
                switchImage[2].setImageResource(R.drawable.icon_profile_filled);
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @SuppressWarnings("unused")
    private IBtnCallListener btnCallListener;

    @Override
    public void onAttachFragment(Fragment fragment) {
        try {
            btnCallListener = (IBtnCallListener) fragment;
        } catch (Exception e) {
        }
        super.onAttachFragment(fragment);
    }

    @Override
    public void transferMsg() {
    }
}
