package com.skymall.pages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.skymall.GlobalFunctions;
import com.skymall.R;
import com.skymall.bean.User;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.mrapp.android.dialog.MaterialDialog;

public class LoginWithEmailPage extends AppCompatActivity implements View.OnClickListener {

    private MaterialDialog listDialog;
    private ActionProcessButton signInButton;
    private MaterialEditText account;
    private MaterialEditText pwd;
    private TextView signUp;
    private TextView forgotPwd;
    private TextView signInWithMobile;
    private Intent in;
    private User user;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_page);
        initViews();
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
    }

    private void initViews(){
        account = (MaterialEditText)findViewById(R.id.account);
        pwd = (MaterialEditText)findViewById(R.id.password);
        signInButton = (ActionProcessButton)findViewById(R.id.login_button);
        signUp = (TextView)findViewById(R.id.txt_signUp);
        forgotPwd = (TextView)findViewById(R.id.txt_forgetPwd);
        signInWithMobile = (TextView)findViewById(R.id.txt_signInWithMobile);

        account.addValidator(new RegexpValidator("Please input correct email format", GlobalFunctions.EMAIL_PATTERN));
        pwd.addValidator(new RegexpValidator("Password should contains both character and numbers", GlobalFunctions.PASSWORD_PATTERN));

        signInButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        signInWithMobile.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
    }



    private void signUpClicked(){
        String registerWithEmail = LoginWithEmailPage.this.getResources().getString(R.string.signup_with_email);
        String registerWithMobile = LoginWithEmailPage.this.getResources().getString(R.string.signup_with_mobile);

        MaterialDialog.Builder dialogBuilder = new de.mrapp.android.dialog.MaterialDialog.Builder(LoginWithEmailPage.this);
        dialogBuilder.setButtonBarDividerColor(Color.parseColor("#EB4F38"));
        dialogBuilder.setItems(new String[]{registerWithEmail, registerWithMobile}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        in = new Intent(LoginWithEmailPage.this, RegisterMobilePage.class);
                        startActivity(in);
                        listDialog.dismiss();
                        break;
                    case 1:
                        in = new Intent(LoginWithEmailPage.this, RegisterEmailPage.class);
                        startActivity(in);
                        listDialog.dismiss();
                        break;
                }
            }
        });
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        listDialog = dialogBuilder.create();
        listDialog.show();
    }

    private void signInClicked(){
        user = new User();
        user.setEmail(account.getText().toString());
        user.setPassword(pwd.getText().toString());
        //Need to set username correct, or wont be able to login
        user.setUsername(account.getText().toString());
        if(account.validate() && pwd.validate()) {
            user.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        in = new Intent(LoginWithEmailPage.this, MainPage.class);
                        signInButton.setProgress(0);
                        Toast.makeText(mContext, "Login done", Toast.LENGTH_SHORT).show();
                        startActivity(in);
                        //Jump to stores page
                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户
                    } else {
                        signInButton.setProgress(0);

                        GlobalFunctions.createDialogWithAlertMsg(LoginWithEmailPage.this, R.string.login_alert);

                        signInButton.setEnabled(true);
                    }
                }
            });
        }else{
            signInButton.setEnabled(true);
            signInButton.setProgress(0);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_signUp :
                signUpClicked();
                break;
            case R.id.login_button :
                signInButton.setProgress(75);
                signInButton.setEnabled(false);
                signInClicked();
                break;
            case R.id.txt_forgetPwd :
                Toast.makeText(LoginWithEmailPage.this, "HAHAHAHAHAH~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_signInWithMobile :
                in = new Intent(LoginWithEmailPage.this, LoginWithMobilePage.class);
                startActivity(in);
                break;
        }

    }
}
