package com.skymall.pages;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dd.processbutton.FlatButton;
import com.skymall.R;

import cn.bmob.v3.BmobUser;


public class WelcomeActivity  extends AppCompatActivity {

    private FlatButton signIn;
    private FlatButton signUp;
    Intent in;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If user has loged in, then no need to display welcome page, directly go to homepage
        if(BmobUser.getCurrentUser() != null){
            in = new Intent(this,MainPage.class);
            startActivity(in);
            finish();
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_page);

        signIn = (FlatButton)findViewById(R.id.signIn);
        signUp = (FlatButton)findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(WelcomeActivity.this, LoginWithMobilePage.class);
                startActivity(in);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(WelcomeActivity.this, RegisterMobilePage.class);
                startActivity(in);
            }
        });

    }
}


















































