package com.example.henryye.floorshop.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.interfaces.IBtnCallListener;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.HashMap;

import cn.bmob.v3.Bmob;

public class MainPage extends FragmentActivity implements OnClickListener,IBtnCallListener {

    private ImageView[] bt_menu = new ImageView[5];
    private int[] bt_menu_id = { R.id.home_tab, R.id.store_tab, R.id.cart_tab, R.id.myself_tab};
    private int[] select_on = { R.drawable.homepagetab, R.drawable.storetab, R.drawable.carttab, R.drawable.myselftab };
    private int[] select_off = { R.drawable.homepagetab, R.drawable.storetab, R.drawable.carttab, R.drawable.myselftab };

    private Homepage_Tab home_F;
    private MyInfo_Tab myInfo_tab;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
        Fresco.initialize(this);
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

        if (home_F == null) {
            home_F = new Homepage_Tab();
            addFragment(home_F);
            showFragment(home_F);
        } else {
            showFragment(home_F);
        }

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
                        showFragment(home_F);
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
        ft.add(R.id.show_layout, fragment);
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
