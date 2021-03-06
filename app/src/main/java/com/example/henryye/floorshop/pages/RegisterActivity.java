package com.example.henryye.floorshop.pages;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.henryye.floorshop.GlobalFunctions;
import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.fragments.CountryChoosingFragment;
import com.example.henryye.floorshop.wigets.AlertBox;
import com.example.henryye.floorshop.wigets.ClearEditText;
import com.example.henryye.floorshop.wigets.ToolBar;

/**
 * Created by henryye on 5/10/17.
 */
public class RegisterActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback  {

    private TextView country;
    private EditText countryCode;
    private ToolBar toolbar;
    private CountryChoosingFragment ccf;
    private String[] countries;
    private String[] codes;
    private String[] array;
    private Intent in;
    private ClearEditText mobileInput;
    private ClearEditText pwdInput;
    private EditText codeInput;
    private AlertBox.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        countryCode = (EditText)findViewById(R.id.txtCountryCode);
        country = (TextView)findViewById(R.id.txtCountry);
        toolbar = (ToolBar)findViewById(R.id.toolbar);
        mobileInput = (ClearEditText)findViewById(R.id.edittxt_phone);
        pwdInput = (ClearEditText)findViewById(R.id.edittxt_pwd);
        codeInput = (EditText)findViewById(R.id.txtCountryCode);

        in = new Intent(this,RegisterSecondActivity.class);
        builder = new AlertBox.Builder(this);
        ccf = new CountryChoosingFragment();

        Resources res = getResources();
        array = res.getStringArray(R.array.country_code_list_en);
        codes = new String[array.length];
        countries = new String[array.length];

        // get digits from countries array
        int i = 0;
        for(String s : array){
            String[] divide = s.split(" ");
            int len = divide.length;
            codes[i] = divide[len-1];
            String country = s.replace(" "+codes[i],"");
            countries[i] = country;
            i++;
        }


        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalFunctions.isMobileNO(mobileInput.getText().toString()) && GlobalFunctions.isRightPwd(pwdInput.getText().toString())){
                   // in.putExtra("userMobile", codeInput.getText().toString().trim() + " " + mobileInput.getText().toString());
                   if(mobileInput.getText().toString() != null && pwdInput.getText().toString() != null) {
                       in.putExtra("userMobile", mobileInput.getText().toString());
                       in.putExtra("userPwd", pwdInput.getText().toString());
                       startActivity(in);
                   }
                }else{
                    builder.setTitle("Reminder");
                    builder.setMessage("Please Check your phone number and password format \n password should contains both character and number");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new Dialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
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




        countryCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String input = countryCode.getText().toString();

                if (input.length() >= 3) {
                    int i = 0;
                    for (String s : codes) {
                        if (s.equals(input)) {
                            country.setText(countries[i]);
                        }
                        i++;
                    }
                }
                return false;
            }
        });

    }

}