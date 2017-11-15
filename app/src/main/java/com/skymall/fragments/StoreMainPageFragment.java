package com.skymall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymall.R;

public class StoreMainPageFragment extends Fragment implements View.OnClickListener{

    private LayoutInflater mInflater;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_info, null);

        initView(rootView);
        initViewPager(rootView);
        return rootView;
    }


    private void initView(View view){

    }


    private void initViewPager(View view){

    }


    @Override
    public void onClick(View v) {

    }
}
