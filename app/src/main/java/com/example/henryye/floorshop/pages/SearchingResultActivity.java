package com.example.henryye.floorshop.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.SearchingPageDropdownCommonAdapter;
import com.example.henryye.floorshop.adapters.SearchingPageListAdapter;
import com.example.henryye.floorshop.bean.Items;
import com.example.henryye.floorshop.bean.Stores;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchingResultActivity extends AppCompatActivity {

    @InjectView(R.id.searching_dropDownMenu) DropDownMenu mDropDownMenu;
    private String filters[] = {"Region", "Classification"};
    private List<View> popupViews = new ArrayList<>();

    private SearchingPageDropdownCommonAdapter regionAdapter;
    private SearchingPageDropdownCommonAdapter classificationAdapter;

    private String regions[] = {"goulongtang", "Mongkok"};
    private String classifications[] = {"digital product", "shoes"};

    private int regionPosition = 0;
    private int classificationPosition = 0;

    private RecyclerView mRecyclerView;
    private ArrayList<Items> item_content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_page);
        ButterKnife.inject(this);
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
        initView();
        initData();

    }

    private void initView() {

        View regionView = getLayoutInflater().inflate(R.layout.searching_dropdown, null);
        GridView region = ButterKnife.findById(regionView, R.id.searching_dropdown_content);
        regionAdapter = new SearchingPageDropdownCommonAdapter(this, Arrays.asList(regions));
        region.setAdapter(regionAdapter);
        TextView regionConfirm = ButterKnife.findById(regionView, R.id.searching_dropdown_confirm);
        regionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(regionPosition == 0 ? filters[0] : regions[regionPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        View classificationView = getLayoutInflater().inflate(R.layout.searching_dropdown, null);
        GridView classification = ButterKnife.findById(classificationView, R.id.searching_dropdown_content);
        classificationAdapter = new SearchingPageDropdownCommonAdapter(this, Arrays.asList(classifications));
        classification.setAdapter(classificationAdapter);
        TextView classificationConfirm = ButterKnife.findById(classificationView, R.id.searching_dropdown_confirm);
        classificationConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(classificationPosition == 0 ? filters[1] : classifications[classificationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        popupViews.add(regionView);
        popupViews.add(classificationView);

        mRecyclerView = (RecyclerView) findViewById(R.id.searching_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SearchingPageListAdapter mAdapter = new SearchingPageListAdapter(item_content);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchingPageListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Items content) {
                Toast.makeText(SearchingResultActivity.this, "item clicked", Toast.LENGTH_LONG).show();
            }
        });

        mDropDownMenu.setDropDownMenu(Arrays.asList(filters), popupViews, mRecyclerView);
    }

    @Override
    public void onBackPressed() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void initData() {
        BmobQuery<Items> query = new BmobQuery<>();
        query.addWhereEqualTo("name", "Apple Watch");
        query.setLimit(100);
        query.findObjects(new FindListener<Items>() {
            @Override
            public void done(List<Items> items, BmobException e) {
                if (e == null) {
                    for (Items item : items) {
                        String name = item.getName();
                        Stores store = item.getStore();
                        double price = item.getPrice();
                        String description = item.getDescription();
                        String classification = item.getClassification();
                        String attributes = item.getAttributes();
                        Items searchResult = new Items(store, classification, name, price, description, attributes);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void initContent() {
    }
}
