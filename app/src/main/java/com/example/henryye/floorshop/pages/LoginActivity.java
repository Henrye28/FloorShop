package com.example.henryye.floorshop.pages;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.User;
import com.example.henryye.floorshop.wigets.AlertBox;
import com.example.henryye.floorshop.wigets.ClearEditText;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mobile = (ClearEditText)findViewById(R.id.etxt_phone);
        pwd = (ClearEditText)findViewById(R.id.etxt_pwd);
        login_btn = (Button)findViewById(R.id.btn_login);
        builder = new AlertBox.Builder(this);;
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login(){

        user = new User(mobile.getText().toString(),pwd.getText().toString());
        user.login( new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    //Jump to stores page
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户
                    //Create a custom loding box here
                }else{
                    builder.setTitle("Reminder");
                    builder.setMessage("Login failed, \nPlease Check your phone number and password");
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
