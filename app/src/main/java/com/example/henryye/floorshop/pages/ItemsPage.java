package com.example.henryye.floorshop.pages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.ItemTitlePagerAdapter;
import com.example.henryye.floorshop.fragments.ItemInfoFragment;
import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class ItemsPage extends AppCompatActivity {
    private ViewPager viewPager;
    //PagerSlidingTabStrip from third-party library
    public PagerSlidingTabStrip pagerSlidingTabStrip;
    private int[] viewPagerId = {R.drawable.detail_applewatch, R.drawable.detail_applewatch2, R.drawable.detail_applewatch3};

    private List<Fragment> fragmentList = new ArrayList<>();
    private ItemInfoFragment goodsInfoFragment;
    private ItemInfoFragment goodsDetailFragment;
    private ItemInfoFragment goodsCommentFragment;
    public ViewPager vp_content;
    public TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_page);

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        tv_title = (TextView) findViewById(R.id.tv_title);

        fragmentList.add(goodsInfoFragment = new ItemInfoFragment());
        fragmentList.add(goodsDetailFragment = new ItemInfoFragment());
        fragmentList.add(goodsCommentFragment = new ItemInfoFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"Goods", "Detail", "Comment"}));
        vp_content.setOffscreenPageLimit(3);
        pagerSlidingTabStrip.setViewPager(vp_content);

    }


}
