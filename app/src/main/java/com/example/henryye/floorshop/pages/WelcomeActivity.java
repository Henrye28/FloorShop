package com.example.henryye.floorshop.pages;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dd.processbutton.FlatButton;
import com.example.henryye.floorshop.R;


public class WelcomeActivity  extends AppCompatActivity {

    private FlatButton signIn;
    private FlatButton signUp;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_page);

        signIn = (FlatButton)findViewById(R.id.signIn);
        signUp = (FlatButton)findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(WelcomeActivity.this, RegisterMobileActivity.class);
                startActivity(in);
            }
        });

    }
}



















































