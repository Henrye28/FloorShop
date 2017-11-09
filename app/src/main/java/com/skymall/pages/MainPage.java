package com.skymall.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

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

    private ImageView[] bt_menu = new ImageView[5];
    private int[] bt_menu_id = { R.id.home_tab, R.id.store_tab, R.id.cart_tab, R.id.myself_tab};
    private int[] select_on = { R.drawable.homepagetab, R.drawable.storetab, R.drawable.carttab, R.drawable.myselftab };
    private int[] select_off = { R.drawable.homepagetab, R.drawable.storetab, R.drawable.carttab, R.drawable.myselftab };

    private Homepage_Tab home_F;

    @BindView(R.id.mainpage_topbar)
    PageTopBar topBar;

    @BindView(R.id.mainpage_view)
    DrawerLayout mainView;

    @BindView(R.id.mainpage_drawer_view)
    DrawerView drawer_view;

    private MyInfo_Tab myInfo_tab;

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

        if (home_F == null) {
            home_F = new Homepage_Tab();
            addFragment(home_F);
            showFragment(home_F);
        } else {
            showFragment(home_F);

//        if (myInfo_tab == null) {
//            myInfo_tab = new MyInfo_Tab();
//            addFragment(myInfo_tab);
//            showFragment(myInfo_tab);
//        } else {
//            showFragment(myInfo_tab);
        }

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab:
                if (home_F == null) {
                    home_F = new Homepage_Tab();
                    addFragment(home_F);
                    showFragment(home_F);
                } else {
                    if (home_F.isHidden()) {

//                if (myInfo_tab == null) {
//                    myInfo_tab = new MyInfo_Tab();
//                    addFragment(home_F);
//                    showFragment(home_F);
//                } else {
//                    if (myInfo_tab.isHidden()) {
//                        showFragment(home_F);
                    }
                }

                break;
        }

        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            if (v.getId() == bt_menu_id[i]) {
                bt_menu[i].setImageResource(select_on[i]);
            }
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mainpage_show_layout, fragment);
        ft.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

        if (home_F != null) {
            ft.hide(home_F);
        }
//        if (tao_F != null) {
//            ft.hide(tao_F);
//        }
//        if (discover_F != null) {
//            ft.hide(discover_F);
//        }
//        if (cart_F != null) {
//            ft.hide(cart_F);
//        }
//        if (user_F != null) {
//            ft.hide(user_F);
//        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();

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
        if (home_F == null) {
            home_F = new Homepage_Tab();
            addFragment(home_F);
            showFragment(home_F);
        } else {
            showFragment(home_F);
        }
        bt_menu[3].setImageResource(select_off[3]);
        bt_menu[0].setImageResource(select_on[0]);

    }

}
