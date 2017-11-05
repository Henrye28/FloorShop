package com.skymall.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.skymall.GlobalFunctions;
import com.skymall.R;
import com.skymall.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterEmailPage extends AppCompatActivity {

    private MaterialEditText email;
    private MaterialEditText password;
    private Button next;
    private User user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email_page);
        Bmob.initialize(this,"ee80fab0407209723c93996bff00b101");

        next = (Button)findViewById(R.id.next);
        email = (MaterialEditText)findViewById(R.id.email_txt);
        password = (MaterialEditText)findViewById(R.id.password_txt);

        email.addValidator(new RegexpValidator("Please input correct email format", GlobalFunctions.EMAIL_PATTERN));

        password.addValidator(new RegexpValidator("Password should contains both character and numbers", GlobalFunctions.PASSWORD_PATTERN));

        

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.validate() && password.validate()) {
                    user = new User();
                    user.setPassword(password.getText().toString());
                    user.setUsername(email.getText().toString());
                    user.setEmail(email.getText().toString());

                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterEmailPage.this, "User regiser successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                GlobalFunctions.createDialogWithAlertMsg(RegisterEmailPage.this,e.getMessage());
                                Log.d("Register", " user sign up failed " + e.getMessage());
                            }
                        }
                    });

                }


            }
        });



    }
}

