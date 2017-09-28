package com.example.henryye.floorshop.pages;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.example.henryye.floorshop.wigets.AlertBox;
import com.example.henryye.floorshop.wigets.ClearEditText;
import com.example.henryye.floorshop.wigets.LoadingViewWithText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by henryye on 5/9/17.
 */
public class LoginActivity  extends AppCompatActivity {

    private Button login_btn;
    private ClearEditText mobile;
    private ClearEditText pwd;
    private User user;
    private AlertBox.Builder builder;
    private LoadingViewWithText loadingView;
    private RelativeLayout rootView;
    private LinearLayout loadingBackground;
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

        in = new Intent(this,RegisterActivity.class);
        mobile = (ClearEditText)findViewById(R.id.etxt_phone);
        pwd = (ClearEditText)findViewById(R.id.etxt_pwd);
        login_btn = (Button)findViewById(R.id.btn_login);

        signUp = (TextView)findViewById(R.id.txt_signUp);
        forgotPwd = (TextView)findViewById(R.id.txt_forgetPwd);

        loadingBackground = (LinearLayout)findViewById(R.id.loading_background);
        builder = new AlertBox.Builder(this);;
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");

        loadingView = new LoadingViewWithText(this);
        rootView = (RelativeLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);

        mContext = this;

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.postLoadState(LoadingViewWithText.State.LOADING);
                rootView.addView(loadingView);
                loadingBackground.setBackgroundColor(getResources().getColor(R.color.translucent));
                rootView.setEnabled(false);
                login();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to register page
                startActivity(in);
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
        user.setMobilePhoneNumber(mobile.getText().toString());
        user.setPassword(pwd.getText().toString());
        //Need to set username correct, or wont be able to login
        user.setUsername(mobile.getText().toString());

        user.login( new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    rootView.removeView(loadingView);
                    Toast.makeText(mContext,"Login done",Toast.LENGTH_SHORT).show();
                    //Jump to stores page
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户
                }else{

                    rootView.removeView(loadingView);
                    rootView.setEnabled(true);
                    loadingBackground.setBackgroundColor(getResources().getColor(R.color.transparent));

                    builder.setTitle("Reminder");
                    builder.setMessage("Login failed, \nPlease Check your phone number and password");
                    rootView.setEnabled(true);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

}
