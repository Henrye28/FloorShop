package com.skymall.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skymall.R;
import com.skymall.widgets.PageTopBar;

public class RegisterMobilePageFirst extends AppCompatActivity {

    private PageTopBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_mobile_page_first);
        toolbar = (PageTopBar)findViewById(R.id.mainpage_topbar);
        setSupportActionBar(toolbar);
        toolbar.showBackView();

    }



}
