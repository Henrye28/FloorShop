package com.example.henryye.floorshop.fragments;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.example.henryye.floorshop.widgets.SearchingMenuGridView;
import com.yyydjk.library.DropDownMenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.ButterKnife;
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
    private DropDownMenu mDropDownMenu;

    private String filters[];
    private List<View> popupViews = new ArrayList<>();

    private SearchingPageDropdownCommonAdapter regionAdapter;
    private SearchingPageDropdownCommonAdapter classificationAdapter;

    private String regions[] = {"goulongtang", "Mongkok"};
    private String classifications[] = {"digital product", "shoes"};

    private RecyclerView mRecyclerView;
    private GridView mGridView;
    private List<Items> item_content = new ArrayList<>();
    private SearchingPageListRecyclerAdapter mRecyclerAdapter;
    private SearchingPageListGridAdapter mGridAdapter;

    private Boolean isRecycle = true;
    private Boolean isAscend = true;

    private List<Items> copyList = new ArrayList<>();
    private ArrayList<String> claConditions = new ArrayList<>();
    private ArrayList<String> regionConditions = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INFO_DOWNLOAD:
                    mRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };

    public static Fragment getInstance(Bundle bundle) {
        SearchingResultItemFragment fragment = new SearchingResultItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = inflater.inflate(R.layout.searching_dropdown, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mDropDownMenu = ButterKnife.findById(view, R.id.searching_dropDownMenu);
        filters = new String[]{getResources().getString(R.string.searching_menu_region),
                getResources().getString(R.string.searching_menu_classification)};

        View regionView = mInflater.inflate(R.layout.searching_dropdown_view, null);
        SearchingMenuGridView region = ButterKnife.findById(regionView, R.id.searching_dropdown_content);
        regionAdapter = new SearchingPageDropdownCommonAdapter(getActivity(), Arrays.asList(regions));
        region.setAdapter(regionAdapter);
        TextView regionConfirm = ButterKnife.findById(regionView, R.id.searching_dropdown_confirm);

        region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regionAdapter.setCheckItem(position);
            }
        });
        regionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < regionAdapter.getChosePosition().length; i++) {
                    if (regionAdapter.getChosePosition()[i] == true)
                        Log.d("here------", i + "");
                }

                mDropDownMenu.closeMenu();
            }
        });

        View classificationView = mInflater.inflate(R.layout.searching_dropdown_view, null);
        SearchingMenuGridView classification = ButterKnife.findById(classificationView, R.id.searching_dropdown_content);
        classificationAdapter = new SearchingPageDropdownCommonAdapter(getActivity(), Arrays.asList(classifications));
        classification.setAdapter(classificationAdapter);
        TextView classificationConfirm = ButterKnife.findById(classificationView, R.id.searching_dropdown_confirm);

        classification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classificationAdapter.setCheckItem(position);
            }
        });
        classificationConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                claConditions.clear();

                for (int i=0; i<classificationAdapter.getChosePosition().length; i++) {
                    if (classificationAdapter.getChosePosition()[i] == true)
                        claConditions.add(classifications[i]);
                }

                filterData();
                mDropDownMenu.closeMenu();
            }
        });

        View priceView = mInflater.inflate(R.layout.searching_menu_price, null);
        final ImageView priceIcon = ButterKnife.findById(priceView, R.id.searching_menu_price_image);
        priceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAscend) {
                    Collections.sort(item_content, new PriceAscendingComparator());
                    priceIcon.setImageResource(R.drawable.icon_arrow_up);
                    if (isRecycle)
                        mRecyclerAdapter.notifyDataSetChanged();
                    else
                        mGridAdapter.notifyDataSetChanged();
                    isAscend = !isAscend;
                } else {
                    Collections.sort(item_content, new PriceDescendingComparator());
                    priceIcon.setImageResource(R.drawable.icon_arrow_down);
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

        Items items1 = new Items(new Stores(), "shoes", "111", 11.11, "111", "111", null);
        Items items2 = new Items(new Stores(), "digital product", "222", 22.11, "222", "222", null);
        Items items3 = new Items(new Stores(), "shoes", "333", 33.11, "333", "333", null);
        item_content.add(items3);
        item_content.add(items1);
        item_content.add(items2);

        try {
            copyList = deepCopy(item_content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final ImageView triggerButton = new ImageView(getActivity());
        triggerButton.setImageResource(R.drawable.icon_recycler);
        triggerButton.setScaleType(ImageView.ScaleType.CENTER);

        mRecyclerView = new RecyclerView(getActivity());
        mRecyclerAdapter = new SearchingPageListRecyclerAdapter(item_content);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setOnItemClickListener(new SearchingPageListRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Items content) {
                Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        int leftRight = dip2px(12);
        int topBottom = dip2px(12);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mGridView = new GridView(getActivity());
        mGridView.setGravity(Gravity.CENTER);
        mGridView.setNumColumns(2);
        mGridView.setHorizontalSpacing(30);
        mGridView.setVerticalSpacing(30);
        mGridAdapter = new SearchingPageListGridAdapter(item_content);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_SHORT).show();
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

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void filterData() {
        item_content.clear();

        if (claConditions.size() != 0) {
            item_content.addAll(copyList.stream()
                    .filter(items -> claConditions.contains(items.getClassification()))
                    .collect(Collectors.toList()));
        }

//        if (regionConditions.size() != 0) {
//            item_content.addAll(copyList.stream()
//                    .filter(items -> regionConditions.contains(items.getRegion()))
//                    .collect(Collectors.toList()));
//        }

        if (isRecycle)
            mRecyclerAdapter.notifyDataSetChanged();
        else
            mGridAdapter.notifyDataSetChanged();
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
