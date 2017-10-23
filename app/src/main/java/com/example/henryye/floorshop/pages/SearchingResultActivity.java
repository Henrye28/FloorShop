package com.example.henryye.floorshop.pages;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.adapters.SearchingPageDropdownCommonAdapter;
import com.example.henryye.floorshop.adapters.SearchingPageListGridAdapter;
import com.example.henryye.floorshop.adapters.SearchingPageListRecyclerAdapter;
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

    private final int INFO_DOWNLOAD = 0;

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
    private GridView mGridView;
    private ArrayList<Items> item_content = new ArrayList<>();
    private SearchingPageListRecyclerAdapter mRecyclerAdapter;
    private SearchingPageListGridAdapter mGridAdapter;

    private Boolean isRecycle = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INFO_DOWNLOAD:
                    mRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_page);
        ButterKnife.inject(this);
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
        initView();
//        initData();
    }

    private void initView() {

        View regionView = getLayoutInflater().inflate(R.layout.searching_dropdown_view, null);
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

        View classificationView = getLayoutInflater().inflate(R.layout.searching_dropdown_view, null);
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

Items items = new Items(new Stores(), "11", "111", 11.11, "111", "111", R.drawable.menu_1_1);
Items items1 = new Items(new Stores(), "22", "222", 22.11, "222", "222", R.drawable.menu_1_1);
item_content.add(items);
item_content.add(items1);

        final ImageView triggerButton = new ImageView(this);
        triggerButton.setImageResource(R.drawable.icon_recycler);
        triggerButton.setScaleType(ImageView.ScaleType.CENTER);

        mRecyclerView = new RecyclerView(this);
        mRecyclerAdapter = new SearchingPageListRecyclerAdapter(item_content);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setOnItemClickListener(new SearchingPageListRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Items content) {
                Toast.makeText(SearchingResultActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int leftRight = dip2px(12);
        int topBottom = dip2px(12);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mGridView = new GridView(this);
        mGridView.setGravity(Gravity.CENTER);
        mGridView.setNumColumns(2);
        mGridView.setHorizontalSpacing(30);
        mGridView.setVerticalSpacing(30);
        mGridAdapter = new SearchingPageListGridAdapter(item_content);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchingResultActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mDropDownMenu.setDropDownMenu(Arrays.asList(filters), popupViews, mRecyclerView);
        mDropDownMenu.addTab(triggerButton, 2);

        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecycle) {
                    mDropDownMenu.removeView();
                    mDropDownMenu.setDropDownMenu(Arrays.asList(filters), popupViews, mRecyclerView);
                    mDropDownMenu.addTab(triggerButton, 2);
                    isRecycle = !isRecycle;
                    triggerButton.setImageResource(R.drawable.icon_recycler);
                } else {
                    mDropDownMenu.removeView();
                    mDropDownMenu.setDropDownMenu(Arrays.asList(filters), popupViews, mGridView);
                    mDropDownMenu.addTab(triggerButton, 2);
                    isRecycle = !isRecycle;
                    triggerButton.setImageResource(R.drawable.icon_grid);
                }
            }
        });

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
                        int imageSrc = item.getImageSrc();
                        Items searchResult = new Items(store, classification, name, price, description, attributes, imageSrc);
                        item_content.add(searchResult);
                    }

                    mRecyclerAdapter.notifyDataSetChanged();
                    Message msg = new Message();
                    msg.what = INFO_DOWNLOAD;
                    handler.sendMessage(msg);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int leftRight;
        private int topBottom;

        public SpacesItemDecoration(int leftRight, int topBottom) {
            this.leftRight = leftRight;
            this.topBottom = topBottom;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = topBottom;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = leftRight;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.bottom = topBottom;
            }
        }
    }
}
