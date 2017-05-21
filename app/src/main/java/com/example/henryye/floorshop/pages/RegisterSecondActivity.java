package com.example.henryye.floorshop.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.example.henryye.floorshop.wigets.CountDownButton;

/**
 * Created by henryye on 5/17/17.
 */
public class RegisterSecondActivity extends AppCompatActivity {

    private CountDownButton countDownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_second_activity);

        countDownButton = (CountDownButton)findViewById(R.id.btn_reSend);
        // Design a countdown button
        Intent in = getIntent();
        String mobile = in.getStringExtra("userMobile");
        String pwd = in.getStringExtra("userPwd");
        User u  = new User(mobile,pwd);

        countDownButton.setOnClickListener(countDownButton);





    }


}
