package com.skymall.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.hss01248.slider.Animations.DescriptionAnimation;
import com.hss01248.slider.SliderLayout;
import com.hss01248.slider.SliderTypes.BaseSliderView;
import com.hss01248.slider.SliderTypes.TextSliderView;
import com.skymall.R;
import com.skymall.adapters.HomepageHotGridAdapter;
import com.skymall.bean.BannerImages;
import com.skymall.bean.HomePageHotItems;
import com.skymall.bean.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class HomepageTab extends Fragment implements BaseSliderView.OnSliderClickListener{

    private final static int LOAD_SUCCESS = 0;

    private GridView gridViewTag;
    private SliderLayout viewPager;
    private HomePageHotItemsGridView gridViewHot;
    private HomepageHotGridAdapter mHomepageHotGridAdapter = null;

    private List<Items> viewPagerItems = new ArrayList<Items>();
    
    private int[] gridTagImages = {R.drawable.icon_shop, R.drawable.icon_coupon, R.drawable.icon_recommend};
    private String[] gridTagText = null;
    private ArrayList<HashMap<String, Object>> gridTagItems = new ArrayList<>();
    private ArrayList<HomePageHotItems> gridHotItems = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_SUCCESS:
                    mHomepageHotGridAdapter.notifyDataSetChanged();
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_tab, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        BmobQuery<BannerImages> queryBanner = new BmobQuery<BannerImages>();
        queryBanner.findObjects(new FindListener<BannerImages>() {
            @Override
            public void done(List<BannerImages> list, BmobException e) {
                if (e == null) {
                    for (BannerImages banner : list) {
                        if (banner.getPage().equals("homepage")) {
                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .image(banner.getPic().getUrl())
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(HomepageTab.this);
                            viewPager.addSlider(textSliderView);
                            viewPagerItems.add(banner.getItem());
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        BmobQuery<HomePageHotItems> queryHotItems = new BmobQuery<HomePageHotItems>();
        queryHotItems.findObjects(new FindListener<HomePageHotItems>() {
            @Override
            public void done(List<HomePageHotItems> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (int i=0; i<list.size(); i++)
                            gridHotItems.add(list.get(i));
                    }

                    Message msg = new Message();
                    msg.what = LOAD_SUCCESS;
                    handler.sendMessage(msg);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView(View view) {
        viewPager = (SliderLayout)view.findViewById(R.id.homepage_view_pager);

        //Set viewPager transform animation
        viewPager.setClickable(true);
        viewPager.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        viewPager.setCustomAnimation(new DescriptionAnimation());
        viewPager.setDuration(3000);

        gridTagText = new String[]{getActivity().getString(R.string.homepage_tag_hot_store), getActivity().getString(R.string.homepage_tag_discount),
                getActivity().getString(R.string.homepage_tag_recommend)};
        gridViewHot = (HomePageHotItemsGridView) view.findViewById(R.id.homepage_grid_hot);
        mHomepageHotGridAdapter = new HomepageHotGridAdapter(getContext(), gridHotItems);
        gridViewHot.setAdapter(mHomepageHotGridAdapter);

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
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Items clickedItem = viewPagerItems.get(viewPager.getCurrentPosition());
        Intent intent = new Intent();
        intent.setClass(HomepageTab.this.getActivity(), ItemsPage.class);
        intent.putExtra("itemID", clickedItem.getObjectId());
        startActivity(intent);
    }
}

class HomePageHotItemsGridView extends GridView {

    public HomePageHotItemsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

