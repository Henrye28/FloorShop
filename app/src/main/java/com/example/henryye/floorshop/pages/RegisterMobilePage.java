package com.example.henryye.floorshop.pages;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryye.floorshop.GlobalFunctions;
import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.example.henryye.floorshop.fragments.TempCountryChoosingFragment;
import com.example.henryye.floorshop.widgets.CountDownButton;
import com.example.henryye.floorshop.widgets.PageTopBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import de.mrapp.android.dialog.ProgressDialog;


public class RegisterMobilePage extends AppCompatActivity{

    private static final String VERIFY_SUCESS = "verifySuccess";


    private TextView country;
    private CountDownButton countDownButton;
    private TextView countryCode;

    private TempCountryChoosingFragment ccf;
    private PageTopBar toolbar;
    private String[] countries;
    private String[] codes;
    private String[] array;

    private Intent in;
    private MaterialEditText mobileInput;
    private MaterialEditText pwdInput;
    private MaterialEditText verifyCodeInput;
    private String mobileStr;
    private String pwdStr;
    private User user;
    private ProgressDialog dialog;

    private boolean verifySucess = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()){
                case VERIFY_SUCESS:
                    verifySucess = true;
                    Toast.makeText(RegisterMobilePage.this,"SMS Code verified", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_mobile_page);

        countryCode = (TextView)findViewById(R.id.txtCountryCode);
        country = (TextView)findViewById(R.id.txtCountry);

        mobileInput = (MaterialEditText)findViewById(R.id.mobile_txt);
        pwdInput = (MaterialEditText)findViewById(R.id.password_txt);
        mobileInput.addValidator(new RegexpValidator("Please input correct mobile format", GlobalFunctions.MOBILE_PATTERN));
        pwdInput.addValidator(new RegexpValidator("Password should contains both character and numbers", GlobalFunctions.PASSWORD_PATTERN));

        verifyCodeInput = (MaterialEditText)findViewById(R.id.verify_code_txt);

        countDownButton = (CountDownButton) findViewById(R.id.btn_reSend);

        toolbar = (PageTopBar)findViewById(R.id.pageTopBar);
        countDownButton.setOnClickListener(countDownButton);
        ccf = new TempCountryChoosingFragment();

        BmobSMS.initialize(this, "ee80fab0407209723c93996bff00b101");


        toolbar.setRightCornerButton(getResources().getDrawable(R.drawable.next));

        toolbar.setRightCornerButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setUsername(mobileStr);
                user.setMobilePhoneNumber(mobileStr);
                user.setPassword(pwdStr);

                if (verifyCodeInput.getText().toString().equals("") || verifyCodeInput.getText().toString() == null) {
                    GlobalFunctions.createDialogWithAlertMsg(RegisterMobilePage.this, R.string.verify_code_format_alert);
                } else {
                    codeVerifying(mobileStr, verifyCodeInput.getText().toString());
                }
            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.registerLayout, ccf);
                transaction.commit();

            }
        });

        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mobileInput.validate() && pwdInput.validate()){
                codeSending(mobileStr);
                countDownButton.startTimer();
            }else {
                countDownButton.stopTimer();
            }
            }
        });


// --------------- Related to country code
//        Resources res = getResources();
//        array = res.getStringArray(R.array.country_code_list_en);
//        codes = new String[array.length];
//        countries = new String[array.length];

//        get digits from countries array
//        int i = 0;
//        for(String s : array){
//            String[] divide = s.split(" ");
//            int len = divide.length;
//            codes[i] = divide[len-1];
//            String country = s.replace(" "+codes[i],"");
//            countries[i] = country;
//            i++;
//        }


// ---------------- Change region when country code
//        countryCode.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String input = countryCode.getText().toString();
//
//                if (input.length() >= 3) {
//                    int i = 0;
//                    for (String s : codes) {
//                        if (s.equals(input)) {
//                            country.setText(countries[i]);
//                        }
//                        i++;
//                    }
//                }
//                return false;
//            }
//        });

    }

    private void codeSending(String mobile){
        BmobSMS.requestSMSCode(this, mobile, "Test1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {
                    Log.i("bmob", "SMS ID ：" + smsId);
                } else {
                    countDownButton.stopTimer();
                    Toast.makeText(RegisterMobilePage.this, getString(R.string.verify_code_failed_to_send), Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });
    }

    private void codeVerifying (String mobile, String code){

        ProgressDialog.Builder dialogBuilder = new ProgressDialog.Builder(RegisterMobilePage.this);
        dialogBuilder.setTitle(R.string.logining);
        dialogBuilder.setProgressBarPosition(ProgressDialog.ProgressBarPosition.BOTTOM);
        dialog = dialogBuilder.create();
        dialog.show();

        BmobSMS.verifySmsCode(this, mobile, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {

                if (e == null) {
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User bmobUser, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                Message message = new Message();
                                message.obj = VERIFY_SUCESS;
                                handler.sendMessage(message);
                            } else {
                                dialog.dismiss();
                                //Jump to register fail page
                                Log.d("Register", " user sign up failed " + e.getMessage());

                                GlobalFunctions.createDialogWithAlertMsg(RegisterMobilePage.this, R.string.user_register_failed_alert);
                            }
                        }
                    });
                } else {
                    Log.d("Register ", " sms code verify failed " + e.getMessage());
                    GlobalFunctions.createDialogWithAlertMsg(RegisterMobilePage.this, R.string.verify_code_format_alert);
                    dialog.dismiss();
                }
            }
        });
    }



}