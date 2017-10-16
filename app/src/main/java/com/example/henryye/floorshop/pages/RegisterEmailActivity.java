package com.example.henryye.floorshop.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.henryye.floorshop.GlobalFunctions;
import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.wigets.ToolBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import cn.bmob.sms.BmobSMS;

public class RegisterEmailActivity extends AppCompatActivity {

    private MaterialEditText email;
    private MaterialEditText password;
    private ToolBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email_page);
        BmobSMS.initialize(this, "ee80fab0407209723c93996bff00b101");

        toolbar = (ToolBar)findViewById(R.id.toolbar);
        email = (MaterialEditText)findViewById(R.id.email_txt);
        password = (MaterialEditText)findViewById(R.id.password_txt);

        email.addValidator(new RegexpValidator("Please input correct email format", GlobalFunctions.EMAIL_PATTERN));

        password.addValidator(new RegexpValidator("Password should contains both character and numbers", GlobalFunctions.PASSWORD_PATTERN));




    }
}
