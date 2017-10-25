package com.example.henryye.floorshop.fragments;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.henryye.floorshop.pages.SearchingResultActivity;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dan.ge on 2017/10/25.
 */

public class SearchingResultItemFragment extends Fragment {

    private final int INFO_DOWNLOAD = 0;

    private LayoutInflater mInflater;

    @InjectView(R.id.searching_dropDownMenu)
    DropDownMenu mDropDownMenu;
    private String filters[];
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

    private Boolean isRecycle = true;
    private Boolean isAscend = true;

    private ArrayList<Items> tmpList = new ArrayList<>();
    private Boolean classificationSelected = false;
    private Boolean regionSelected = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INFO_DOWNLOAD:
                    mRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_page);
        ButterKnife.inject(this);

        initView();
//        initData();
    }

    private void initView() {

        filters = new String[]{getResources().getString(R.string.searching_menu_region),
                getResources().getString(R.string.searching_menu_classification)};

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

        View priceView = getLayoutInflater().inflate(R.layout.searching_menu_price, null);
        final ImageView priceIcon = ButterKnife.findById(priceView, R.id.searching_menu_price_image);
        priceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAscend) {
                    Collections.sort(item_content, new SearchingResultActivity.PriceAscendingComparator());
                    priceIcon.setImageResource(R.drawable.icon_searching_menu_up);
                    if (isRecycle)
                        mRecyclerAdapter.notifyDataSetChanged();
                    else
                        mGridAdapter.notifyDataSetChanged();
                    isAscend = !isAscend;
                } else {
                    Collections.sort(item_content, new SearchingResultActivity.PriceDescendingComparator());
                    priceIcon.setImageResource(R.drawable.icon_searching_menu_down);
                    if (isRecycle)
                        mRecyclerAdapter.notifyDataSetChanged();
                    else
                        mGridAdapter.notifyDataSetChanged();
                    isAscend = !isAscend;
                }
            }
        });

        popupViews.add(regionView);
        popupViews.add(classificationView);

        Items items1 = new Items(new Stores(), "11", "111", 11.11, "111", "111", null);
        Items items2 = new Items(new Stores(), "22", "222", 22.11, "222", "222", null);
        Items items3 = new Items(new Stores(), "33", "333", 33.11, "333", "333", null);
        item_content.add(items3);
        item_content.add(items1);
        item_content.add(items2);

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
        mRecyclerView.addItemDecoration(new SearchingResultActivity.SpacesItemDecoration(leftRight, topBottom));

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
        mDropDownMenu.addTab(priceView, 2);
        mDropDownMenu.addTab(triggerButton, 3);

        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecycle) {
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
        query.addWhereEqualTo("name", "Drone");
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
                        BmobFile cover = item.getCover();
                        Items searchResult = new Items(store, classification, name, price, description, attributes, cover);
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

    class PriceAscendingComparator implements Comparator<Items> {
        @Override
        public int compare(Items item1, Items item2) {
            if (item1.getPrice() < item2.getPrice()) return -1;
            if (item1.getPrice() > item2.getPrice()) return 1;
            return 0;
        }
    }

    class PriceDescendingComparator implements Comparator<Items> {
        @Override
        public int compare(Items item1, Items item2) {
            if (item1.getPrice() < item2.getPrice()) return 1;
            if (item1.getPrice() > item2.getPrice()) return -1;
            return 0;
        }
    }
}
