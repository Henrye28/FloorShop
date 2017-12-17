package com.skymall.pages;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.skymall.R;


public class WelcomeActivity  extends AppCompatActivity {

    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If user has loged in, then no need to display welcome page, directly go to homepage
//        if(BmobUser.getCurrentUser() != null){
//            in = new Intent(this,MainPage.class);
//            startActivity(in);
//            finish();
//        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_page);
    }
}



















































