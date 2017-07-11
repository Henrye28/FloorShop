package com.example.henryye.floorshop.pages;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.Adapter_GridView;
import com.example.henryye.floorshop.wigets.MyGridView;
import com.example.henryye.floorshop.wigets.ToolBar;


public class Homepage extends Fragment {

    private Adapter_GridView gradViewAdapter;
    private MyGridView myGridView;
    private ToolBar toolbar;

    private int[] gridViewPics = { R.drawable.catogeries, R.drawable.picshare, R.drawable.saletab, R.drawable.hkmarket};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = (ToolBar)view.findViewById(R.id.toolbar);
        myGridView = (MyGridView) view.findViewById(R.id.my_gridview);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gradViewAdapter = new Adapter_GridView(getActivity(), gridViewPics);
        myGridView.setAdapter(gradViewAdapter);

        toolbar.showSearchView();

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


            }
        });
    }




}
