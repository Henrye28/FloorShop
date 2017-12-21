package com.skymall.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.hss01248.slider.Animations.DescriptionAnimation;
import com.hss01248.slider.SliderLayout;
import com.hss01248.slider.SliderTypes.BaseSliderView;
import com.hss01248.slider.SliderTypes.TextSliderView;
import com.skymall.R;
import com.skymall.bean.BannerImages;
import com.skymall.bean.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Homepage_Tab extends Fragment implements BaseSliderView.OnSliderClickListener{

    private GridView gridViewTag;
    private SliderLayout viewPager;
    private GridView gridViewHot;

    private ImageView hotItem0;
    private ImageView hotItem1;
    private ImageView hotItem2;
    private ImageView hotItem3;

    private List<Items> viewPagerItems = new ArrayList<Items>();
    
    private int[] gridTagImages = {R.drawable.icon_shop, R.drawable.icon_coupon, R.drawable.icon_recommend};
    private String[] gridTagText = {"Hot Store", "Discount", "Recommend"};
    private ArrayList<HashMap<String, Object>> gridTagItems = new ArrayList<>();

    private int[] gridHotImages = {};
    private String[] gridHotText = {};
    private ArrayList<HashMap<String, Object>> gridHotItems = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_tab, null);
        initView(view);
//        initHotItemsBlock(view);
        initViewPager(view);
        return view;
    }

//    public void initHotItemsBlock(View view) {
//        hotItem0 = (SimpleDraweeView)view.findViewById(R.id.hotItem0);
//        hotItem1 = (SimpleDraweeView)view.findViewById(R.id.hotItem1);
//        hotItem2 = (SimpleDraweeView)view.findViewById(R.id.hotItem2);
//        hotItem3 = (SimpleDraweeView)view.findViewById(R.id.hotItem3);
//
//        BmobQuery<HomePageHotItems> query = new BmobQuery<HomePageHotItems>();
//        query.findObjects(new FindListener<HomePageHotItems>() {
//            @Override
//            public void done(List<HomePageHotItems> list, BmobException e) {
//
//                if (e == null) {
//                    if (list.size() != 0) {
//                        hotItem0.setImageURI(Uri.parse(list.get(0).getBitmap().getFileUrl()));
//                        hotItem1.setImageURI(Uri.parse(list.get(1).getBitmap().getFileUrl()));
//                        hotItem2.setImageURI(Uri.parse(list.get(2).getBitmap().getFileUrl()));
//                        hotItem3.setImageURI(Uri.parse(list.get(3).getBitmap().getFileUrl()));
//                    }
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//    };

    private void initViewPager(final View view){
        viewPager = (SliderLayout)view.findViewById(R.id.homepage_view_pager);
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
        gridViewTag = (GridView) view.findViewById(R.id.homepage_grid_tag);

        for (int i = 0; i< gridTagText.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img", gridTagImages[i]);
            map.put("text", gridTagText[i]);
            gridTagItems.add(map);
        }

        SimpleAdapter grid_adapter = new SimpleAdapter(getContext(), gridTagItems, R.layout.homepage_tab_grid_tag,
                new String[]{"img", "text"},
                new int[]{R.id.homepage_grid_tag_image, R.id.homepage_grid_tag_text});
        gridViewTag.setAdapter(grid_adapter);

        gridViewTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });

        gridViewHot = (GridView) view.findViewById(R.id.homepage_grid_hot);
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
