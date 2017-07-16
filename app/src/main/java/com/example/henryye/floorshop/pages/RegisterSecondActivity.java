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
import cn.bmob.v3.listener.SaveListener;

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
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_second_page);

        in = getIntent();

        mContext = this;

        toolbar = (ToolBar) findViewById(R.id.toolbar);
        countDownButton = (CountDownButton) findViewById(R.id.btn_reSend);
        codeInput = (ClearEditText) findViewById(R.id.edittxt_code);


        // Design a countdown button
        in = getIntent();
        mobile = in.getStringExtra("userMobile");
        pwd = in.getStringExtra("userPwd");


        user = new User();
        user.setUsername(mobile);
        user.setMobile(mobile);
        user.setPassword(pwd);

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
                try {
                    code = codeInput.getText().toString();
                    codeVerifying();
                }catch (Exception e){
                    Log.d("Verifying enter "," ----------------enter error"+e.getMessage() );
                }

            }
        });
    }

    private void codeSending() {
        final String number = mobile;

        BmobSMS.requestSMSCode(this, number, "Test1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {
                    Log.i("bmob", "SMS ID ：" + smsId);
                }else{
                    ex.printStackTrace();
                }
            }
        });
    }

    private void codeVerifying (){
        BmobSMS.verifySmsCode(this, mobile, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {

                if (e == null) {
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User bmobUser, cn.bmob.v3.exception.BmobException e) {
                            if(e == null){
                                    //Jump to homepage
                                Toast.makeText(mContext,"Register successfully",Toast.LENGTH_SHORT).show();
                                in = new Intent(mContext,LoginActivity.class);
                                startActivity(in);

                            }else{
                                Toast.makeText(mContext,"Wrong verifying code",Toast.LENGTH_SHORT).show();
                                    //Jump to register fail page
                            }
                        }
                    });
                } else {
                    Log.d("Register "," -----------fail" + e.getMessage());
                    // THE ERROR IS HERE !!!!!!!!!!!!!!!!
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
