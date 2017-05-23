package com.example.henryye.floorshop.pages;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.example.henryye.floorshop.wigets.ClearEditText;
import com.example.henryye.floorshop.wigets.CountDownButton;
import com.example.henryye.floorshop.wigets.ToolBar;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * Created by henryye on 5/17/17.
 */
public class RegisterSecondActivity extends AppCompatActivity {

    private CountDownButton countDownButton;
    private Intent in;
    private ToolBar toolbar;
    private ClearEditText codeInput;
    private Context mContext;
    private String code;
    private String mobile;
    private String pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_second_activity);

        in = getIntent();

        mContext = this;

        toolbar = (ToolBar) findViewById(R.id.toolbar);
        countDownButton = (CountDownButton) findViewById(R.id.btn_reSend);
        codeInput = (ClearEditText) findViewById(R.id.edittxt_code);


        // Design a countdown button
        Intent in = getIntent();
        mobile = in.getStringExtra("userMobile");
        pwd = in.getStringExtra("userPwd");
        code = codeInput.getText().toString();

        User u = new User(mobile, pwd);

        countDownButton.setOnClickListener(countDownButton);


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //TODO
        }

        BmobSMS.initialize(this, "ee80fab0407209723c93996bff00b101");


        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeSending();
            }
        });

        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeVerifying();
            }
        });
    }

    private void codeSending() {
        final String number = mobile;

        BmobSMS.requestSMSCode(this, number, "Test1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                Log.d("Testing" , " ----- sent");
                Log.d("Testing" , " ----- number " + number);
                // TODO Auto-generated method stub
                if (ex == null) {
                    Log.d("Testing" , " ----- 2 sent");
                    Log.i("bmob", "SMS ID ：" + smsId);
                }else{
                    Log.d("Testing" , " ----- 3 sent");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void codeVerifying(){
        BmobSMS.verifySmsCode(this, mobile, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                } else {
                    Toast.makeText(mContext, "Incorrect verifying code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

}
