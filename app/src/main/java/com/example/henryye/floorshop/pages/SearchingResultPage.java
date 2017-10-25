package com.example.henryye.floorshop.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.fragments.SearchingResultItemFragment;
import com.example.henryye.floorshop.fragments.SearchingResultStoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SearchingResultPage extends AppCompatActivity {

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    private SearchingResultItemFragment mSearchingResultItemFragment;
    private SearchingResultStoreFragment mSearchingResultStoreFragment;

    private String[] tabTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_page);

        initView();
    }

    public void initView() {

        tabTitles = new String[]{getString(R.string.searching_sliding_item), getString(R.string.searching_sliding_store)};

        mPagerSlidingTabStrip = ButterKnife.findById(this, R.id.searching_toptabs);
        mViewPager = ButterKnife.findById(this, R.id.searching_showing_pager);

        mSearchingResultItemFragment = new SearchingResultItemFragment();
        mSearchingResultStoreFragment = new SearchingResultStoreFragment();
        fragments.add(mSearchingResultItemFragment);
        fragments.add(mSearchingResultStoreFragment);

        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putString("tab", tabTitles[position]);
            return SearchingResultItemFragment.getInstance(b);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
