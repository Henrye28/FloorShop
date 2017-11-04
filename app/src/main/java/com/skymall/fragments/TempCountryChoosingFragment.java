package com.skymall.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skymall.R;

public class TempCountryChoosingFragment extends Fragment {

    private View view;
    private ImageView back;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.temp_country_choosing_page, container, false);
        back = (ImageView)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_down_enter, R.anim.fragment_slide_up_exit);
                fragmentTransaction.remove(TempCountryChoosingFragment.this).commit();
            }
        });


        return  view;
    }
}

