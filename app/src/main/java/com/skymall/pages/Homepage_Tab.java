package com.skymall.pages;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hss01248.slider.Animations.DescriptionAnimation;
import com.hss01248.slider.SliderLayout;
import com.hss01248.slider.SliderTypes.BaseSliderView;
import com.hss01248.slider.SliderTypes.TextSliderView;
import com.skymall.R;
import com.skymall.adapters.HomepageGridViewAdapter;
import com.skymall.bean.BannerImages;
import com.skymall.bean.HomePageHotItems;
import com.skymall.bean.Items;
import com.skymall.fragments.searchDrawer.DrawerView;
import com.skymall.fragments.searchDrawer.ICallBack;
import com.skymall.fragments.searchDrawer.bCallBack;
import com.skymall.widgets.MyGridView;
import com.skymall.widgets.PageTopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Homepage_Tab extends Fragment implements BaseSliderView.OnSliderClickListener {


    private HomepageGridViewAdapter gradViewAdapter;
    private MyGridView myGridView;
    private SliderLayout viewPager ;

    @BindView(R.id.homepage_topbar)
    PageTopBar topbar;

    @BindView(R.id.homepage_view)
    DrawerLayout main_view;

    @BindView(R.id.homepage_drawer_view)
    DrawerView drawer_view;

    private ImageView hotItem0;
    private ImageView hotItem1;
    private ImageView hotItem2;
    private ImageView hotItem3;

    private List<Items> viewPagerItems = new ArrayList<Items>();
    
    private int[] gridViewPics = {R.drawable.catogeries, R.drawable.picshare, R.drawable.saletab, R.drawable.hkmarket};

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
                Log.d("test ------ ", " page pppppppp "  );
                if (e == null) {
                    if (list.size() != 0) {
                        Log.d("test ------ ", " page pppppppp 2" + list.get(0).getBitmap().getFileUrl() );
                        hotItem0.setImageURI(Uri.parse(list.get(0).getBitmap().getFileUrl()));
                        hotItem1.setImageURI(Uri.parse(list.get(1).getBitmap().getFileUrl()));
                        hotItem2.setImageURI(Uri.parse(list.get(2).getBitmap().getFileUrl()));
                        hotItem3.setImageURI(Uri.parse(list.get(3).getBitmap().getFileUrl()));
                    }
                } else {
                    Log.d("test ------ ", " page pppppppp e" + e.getMessage());
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

        ButterKnife.bind(this, view);

        topbar.showHomepageView();

        myGridView = (MyGridView) view.findViewById(R.id.my_gridview);
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gradViewAdapter = new HomepageGridViewAdapter(getActivity(), gridViewPics);
        myGridView.setAdapter(gradViewAdapter);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(Homepage_Tab.this.getActivity(), NearbyMapPage.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });

//        topbar.setSearchView(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                main_view.openDrawer(GravityCompat.START);
//            }
//        });

        drawer_view.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

            }
        });

        drawer_view.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                main_view.closeDrawer(drawer_view);
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
