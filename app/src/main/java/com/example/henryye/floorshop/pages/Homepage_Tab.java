package com.example.henryye.floorshop.pages;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.Adapter_GridView;
import com.example.henryye.floorshop.wigets.MyGridView;
import com.example.henryye.floorshop.wigets.ToolBar;
import com.example.henryye.floorshop.wigets.view.AbOnItemClickListener;
import com.example.henryye.floorshop.wigets.view.AbSlidingPlayView;

import java.util.ArrayList;


public class Homepage_Tab extends Fragment {

    private Adapter_GridView gradViewAdapter;
    private MyGridView myGridView;
    private ToolBar toolbar;
    private AbSlidingPlayView viewPager;
    private ArrayList<View> allListView;

    private int[] gridViewPics = { R.drawable.catogeries, R.drawable.picshare, R.drawable.saletab, R.drawable.hkmarket};
    private int[] viewPagerResID = {R.drawable.viewpager_market1, R.drawable.viewpager_market2, R.drawable.viewpager_market3};

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

        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
        viewPager.setPlayType(1);
        viewPager.setSleepTime(3000);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
        initViewPager();
    }


    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();


        for (int i = 0; i < viewPagerResID.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_pics, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(viewPagerResID[i]);
            allListView.add(view);
        }


        viewPager.addViews(allListView);
        viewPager.startPlay();
        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
            @Override
            public void onClick(int position) {
//                Intent intent = new Intent(getActivity(), BabyActivity.class);
//                startActivity(intent);
            }
        });
    }




}
