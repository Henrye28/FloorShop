package com.example.henryye.floorshop.pages;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.Adapter_GridView;
import com.example.henryye.floorshop.bean.BannerImages;
import com.example.henryye.floorshop.bean.HomePageHotItems;
import com.example.henryye.floorshop.widgets.MyGridView;
import com.example.henryye.floorshop.bean.Items;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hss01248.slider.Animations.DescriptionAnimation;
import com.hss01248.slider.SliderLayout;
import com.hss01248.slider.SliderTypes.BaseSliderView;
import com.hss01248.slider.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Homepage_Tab extends Fragment implements BaseSliderView.OnSliderClickListener{

    private Adapter_GridView gradViewAdapter;
    private MyGridView myGridView;
    private ArrayList<View> allListView;
    private SliderLayout viewPager ;

    private ImageView hotItem0;
    private ImageView hotItem1;
    private ImageView hotItem2;
    private ImageView hotItem3;

    private List<Items> viewPagerItems = new ArrayList<Items>();
    
    private int[] gridViewPics = { R.drawable.catogeries, R.drawable.picshare, R.drawable.saletab, R.drawable.hkmarket};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_tab, null);
        initView(view);
        initHotItemsBlock(view);
        initViewPager(view);
        return view;
    }

    public void initHotItemsBlock(View view) {
        hotItem0 = (SimpleDraweeView)view.findViewById(R.id.hotItem0);
        hotItem1 = (SimpleDraweeView)view.findViewById(R.id.hotItem1);
        hotItem2 = (SimpleDraweeView)view.findViewById(R.id.hotItem2);
        hotItem3 = (SimpleDraweeView)view.findViewById(R.id.hotItem3);

        BmobQuery<HomePageHotItems> query = new BmobQuery<HomePageHotItems>();
        query.findObjects(new FindListener<HomePageHotItems>() {
            @Override
            public void done(List<HomePageHotItems> list, BmobException e) {

                if (e == null) {
                    if (list.size() != 0) {
                        hotItem0.setImageURI(Uri.parse(list.get(0).getBitmap().getFileUrl()));
                        hotItem1.setImageURI(Uri.parse(list.get(1).getBitmap().getFileUrl()));
                        hotItem2.setImageURI(Uri.parse(list.get(2).getBitmap().getFileUrl()));
                        hotItem3.setImageURI(Uri.parse(list.get(3).getBitmap().getFileUrl()));
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    };

    private void initViewPager(final View view){
        viewPager = (SliderLayout)view.findViewById(R.id.view_pager);
        BmobQuery<BannerImages> query = new BmobQuery<BannerImages>();
        query.findObjects(new FindListener<BannerImages>() {
            @Override
            public void done(List<BannerImages> list, BmobException e) {
                if (e == null) {
                    for (BannerImages banner : list) {
                        if (banner.getPage().equals("homepage")) {
                            TextSliderView textSliderView = new TextSliderView(view.getContext());
                            textSliderView
                                    .image(banner.getPic().getUrl())
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(Homepage_Tab.this);
                            viewPager.addSlider(textSliderView);
                            viewPagerItems.add(banner.getItem());
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        //Set viewPager transform animation
        viewPager.setClickable(true);
        viewPager.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        viewPager.setCustomAnimation(new DescriptionAnimation());
        viewPager.setDuration(3000);

    }

    private void initView(View view) {
        myGridView = (MyGridView) view.findViewById(R.id.my_gridview);

        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gradViewAdapter = new Adapter_GridView(getActivity(), gridViewPics);
        myGridView.setAdapter(gradViewAdapter);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }





    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Items clickedItem = viewPagerItems.get(viewPager.getCurrentPosition());
        Intent intent = new Intent();
        intent.setClass(Homepage_Tab.this.getActivity(), ItemsPage.class);
        intent.putExtra("itemID", clickedItem.getObjectId());
        startActivity(intent);
    }
}
