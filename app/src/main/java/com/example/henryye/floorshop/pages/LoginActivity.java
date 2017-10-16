package com.example.henryye.floorshop.pages;

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
import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.mrapp.android.dialog.MaterialDialog;


public class LoginActivity  extends AppCompatActivity{

    private MaterialDialog alertDialog;
    private MaterialDialog listDialog;
    private ActionProcessButton signInButton;
    private MaterialEditText account;
    private MaterialEditText pwd;
    private User user;
    private TextView signUp;
    private TextView forgotPwd;
    private Intent in;
    private Context mContext;

    /**
     * Login page
     *
     * @author Henry
     * loadingbackGround this is the translucent background when loading box is loading
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mContext = this;

        account = (MaterialEditText)findViewById(R.id.account);
        pwd = (MaterialEditText)findViewById(R.id.password);
        signInButton = (ActionProcessButton)findViewById(R.id.login_button);

        signUp = (TextView)findViewById(R.id.txt_signUp);
        forgotPwd = (TextView)findViewById(R.id.txt_forgetPwd);

        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // progressGenerator.start(signInButton);
                signInButton.setProgress(75);
                signInButton.setEnabled(false);
                login();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registerWithEmail = LoginActivity.this.getResources().getString(R.string.signup_with_email);
                String registerWithMobile = LoginActivity.this.getResources().getString(R.string.signup_with_mobile);

                MaterialDialog.Builder dialogBuilder = new de.mrapp.android.dialog.MaterialDialog.Builder(LoginActivity.this);
                dialogBuilder.setButtonBarDividerColor(Color.parseColor("#EB4F38"));
                dialogBuilder.setItems(new String[]{registerWithEmail, registerWithMobile}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                in = new Intent(LoginActivity.this, RegisterMobileActivity.class);
                                startActivity(in);
                                listDialog.dismiss();
                                break;
                            case 1:
                                listDialog.dismiss();
                                break;
                        }
                    }
                });
                dialogBuilder.setPositiveButton(android.R.string.ok, null);
                listDialog = dialogBuilder.create();
                listDialog.show();

            }
        });

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to password reset page

            }
        });
    }




    public void login(){
        user = new User();
        user.setMobilePhoneNumber(account.getText().toString());
        user.setPassword(pwd.getText().toString());
        //Need to set username correct, or wont be able to login
        user.setUsername(account.getText().toString());

        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    in = new Intent(LoginActivity.this, MainPage.class);
                    signInButton.setProgress(0);
                    Toast.makeText(mContext, "Login done", Toast.LENGTH_SHORT).show();
                    startActivity(in);
                    //Jump to stores page
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户
                } else {
                    signInButton.setProgress(0);

                    MaterialDialog.Builder dialogBuilder = new de.mrapp.android.dialog.MaterialDialog.Builder(LoginActivity.this);
                    dialogBuilder.setMessage(R.string.login_alert);
                    dialogBuilder.setPositiveButton(android.R.string.ok, null);
                    alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    signInButton.setEnabled(true);
                }
            }
        });
    }



}
