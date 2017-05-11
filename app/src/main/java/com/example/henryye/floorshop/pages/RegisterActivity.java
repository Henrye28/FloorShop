package com.example.henryye.floorshop.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.henryye.floorshop.R;

/**
 * Created by henryye on 5/10/17.
 */
public class RegisterActivity extends AppCompatActivity {

    private TextView country;
    private EditText countryCode;
    String[] countryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);


        countryCode = (EditText)findViewById(R.id.txtCountryCode);
        country = (TextView)findViewById(R.id.txtCountry);


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        countryCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {



                return false;
            }
        });


    }

}