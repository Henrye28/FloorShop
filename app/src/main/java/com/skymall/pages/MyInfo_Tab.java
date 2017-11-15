package com.skymall.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymall.R;

public class MyInfo_Tab extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.myinfo_tab, null);
        return view;

        // Content to be confirmed, self info can be edited seems not many, do we really need this
    }


}
