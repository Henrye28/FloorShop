package com.example.henryye.floorshop.pages;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
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
    private String[] countries;
    private String[] codes;
    private String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ccf = new CountryChoosingFragment();

        Resources res = getResources();
        array = res.getStringArray(R.array.country_code_list_en);
        codes = new String[array.length];
        countries = new String[array.length];

        countryCode = (EditText)findViewById(R.id.txtCountryCode);
        country = (TextView)findViewById(R.id.txtCountry);


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
                String input = countryCode.getText().toString();

                if(input.length() >= 3){
                    int i = 0;
                    for(String s : codes){
                        if(s.equals(input)){
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