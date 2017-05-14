package com.example.henryye.floorshop.pages;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.fragments.CountryChoosingFragment;

/**
 * Created by henryye on 5/10/17.
 */
public class RegisterActivity extends AppCompatActivity {

    private TextView country;
    private EditText countryCode;
    private CountryChoosingFragment ccf;
    String[] countryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);



        ccf = new CountryChoosingFragment();

        countryCode = (EditText)findViewById(R.id.txtCountryCode);
        country = (TextView)findViewById(R.id.txtCountry);


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager. beginTransaction();
                transaction.replace(R.id.registerLayout, ccf);
                transaction.commit();

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