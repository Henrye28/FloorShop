package com.skymall.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hss01248.slider.Animations.DescriptionAnimation;
import com.hss01248.slider.SliderLayout;
import com.hss01248.slider.SliderTypes.BaseSliderView;
import com.hss01248.slider.SliderTypes.TextSliderView;
import com.skymall.R;
import com.skymall.bean.Comments;
import com.skymall.bean.ItemPics;
import com.skymall.bean.Items;
import com.skymall.bean.Stores;
import com.skymall.pages.ItemsPage;
import com.skymall.widgets.itemAttributeTag.Attribute;
import com.skymall.widgets.itemAttributeTag.FlowLayout;
import com.skymall.widgets.itemAttributeTag.TagAdapter;
import com.skymall.widgets.itemAttributeTag.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ItemInfoFragment extends Fragment implements View.OnClickListener{

    private final static String ITEM_INITIATED = "itemInitiated";
    private final static String COMMENT_INITIATED = "commentInitiated";

    private ScrollView sv_goods_info;
    private TextView tv_goods_detail, tv_goods_config;
    private View v_tab_cursor;
    public FrameLayout fl_content;
    public LinearLayout  commentLayout, noCommentLayout, ll_recommend, ll_pull_up;
    public TextView commentContentTv, commentUserTv, commentTimeTv, commentCountTv, commentEmptyTv;

    public TextView itemPriceTv, itemNameTv;
    public ItemsPage activity;
    private SliderLayout viewPager;
    private LinearLayout itemAttributeSelection;

    private List<String> mSizeData;//大小属性数据
    private List<String> mColorData;//颜色属性数据
    // private List<String> mFailureSkuDate;//无库存或不能选的所有组合sku
    private List<String> mAllSkuDate;//所有的组合sku
    private LayoutInflater mInflater;

    private Attribute SizeAtt = new Attribute();
    private Attribute ColorAtt = new Attribute();
    private TextView itemAttributeColorText;

    private int DefaultColor;//临时记录的颜色
    private int DefaultSize;//临时记录的大小

    //Item details
    private double itemPrice;
    private String itemName;
    private String itemDescription;
    private Stores itemStore;
    private String itemColor;
    private String itemSize;
    private String itemClassification;
    private List<String> itemSizeList;
    private List<String> itemColorList;

    //Comment details
    private String commentContent;
    private String commentUser;
    private String commentTime;
    private String commentCount;


    private int Color;
    private int Size;
    private String ColorStr;
    private String SizeStr;
    private String Sku;



    //ItemID is passed in by other activity(depends on which item you just clicked)
    private String itemID;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ItemsPage) context;
        Intent intent = this.getActivity().getIntent();
        itemID = intent.getStringExtra("itemID");
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case ITEM_INITIATED:
                    setItemsDetailData();
                    initialColorSizeData(itemSizeList,itemColorList);
                    break;
                case COMMENT_INITIATED:
                    setCommentDetailData();
                    break;
            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_info, null);

        initView(rootView);
        initViewPager(rootView);
        initListener();
        initialItemDetail(rootView);
        initCommentsDetails();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.itemAttributeSelection :
                CommodityAttribute mCommodityAttribute = new CommodityAttribute(getActivity());
                mCommodityAttribute.showAtLocation(itemAttributeColorText, Gravity.BOTTOM, 0, 0);

        }
    }

    private void initListener() {
        itemAttributeSelection.setOnClickListener(this);
        ll_pull_up.setOnClickListener(this);
    }

    private void initView(View rootView) {
        itemAttributeSelection = (LinearLayout)rootView.findViewById(R.id.itemAttributeSelection);
        itemAttributeColorText = (TextView)rootView.findViewById(R.id.itemAttributeColorText);

        //Comment block
        commentLayout = (LinearLayout)rootView.findViewById(R.id.comment_layout);
        noCommentLayout = (LinearLayout)rootView.findViewById(R.id.no_comment_layout);
        commentContentTv = (TextView)rootView.findViewById(R.id.comment_content);
        commentTimeTv = (TextView)rootView.findViewById(R.id.comment_time);
        commentUserTv = (TextView)rootView.findViewById(R.id.comment_user);
        commentCountTv = (TextView)rootView.findViewById(R.id.comment_count);
        commentEmptyTv = (TextView)rootView.findViewById(R.id.comment_empty);

        viewPager = (SliderLayout)rootView.findViewById(R.id.homepage_view_pager);
        itemNameTv = (TextView)rootView.findViewById(R.id.item_name);
        itemPriceTv = (TextView) rootView.findViewById(R.id.item_price);
        sv_goods_info = (ScrollView) rootView.findViewById(R.id.sv_goods_info);
        v_tab_cursor = rootView.findViewById(R.id.v_tab_cursor);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        ll_recommend = (LinearLayout) rootView.findViewById(R.id.ll_recommend);
        ll_pull_up = (LinearLayout) rootView.findViewById(R.id.ll_pull_up);
        tv_goods_detail = (TextView) rootView.findViewById(R.id.tv_goods_detail);
        tv_goods_config = (TextView) rootView.findViewById(R.id.tv_goods_config);
        setRecommendGoods();

    }

    //getting pics from ItemPics table based on itemId
    private void initViewPager(final View view) {
        viewPager = (SliderLayout) view.findViewById(R.id.homepage_view_pager);
        BmobQuery<ItemPics> query = new BmobQuery<ItemPics>();
        Items item = new Items();
        item.setObjectId(itemID);
        query.addWhereEqualTo("item", new BmobPointer(item));
        query.findObjects(new FindListener<ItemPics>() {
            @Override
            public void done(List<ItemPics> list, BmobException e) {
                if (e == null) {
                    for (ItemPics pics : list) {
                        TextSliderView textSliderView = new TextSliderView(view.getContext());
                        textSliderView
                                .image(pics.getPic().getUrl())
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        viewPager.addSlider(textSliderView);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
        //Set viewPager transform animation
        viewPager.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        viewPager.setCustomAnimation(new DescriptionAnimation());
        viewPager.setDuration(3000);
    }

    private void initCommentsDetails(){
        BmobQuery<Comments> query = new BmobQuery<Comments>();
        Items item = new Items();
        item.setObjectId(itemID);
        query.addWhereEqualTo("item", new BmobPointer(item));
        query.include("user,post.author");
        query.order("createdAt");
        query.findObjects(new FindListener<Comments>() {
            @Override
            public void done(List<Comments> list, BmobException e) {
                if (e == null) {
                    commentCount = "(" + list.size() + ")";
                    commentUser = list.get(0).getUser().getUsername();
                    commentTime = list.get(0).getCreatedAt();
                    commentContent = list.get(0).getContent();
                    Message itemDetailInitiated = new Message();
                    itemDetailInitiated.obj = COMMENT_INITIATED;
                    handler.sendMessage(itemDetailInitiated);
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initialItemDetail(final View view){
        BmobQuery<Items> query = new BmobQuery<Items>();
        query.addWhereEqualTo("objectId", itemID);
        query.findObjects(new FindListener<Items>() {
            @Override
            public void done(List<Items> list, BmobException e) {
                if (e == null) {
                    for (Items item : list) {
                        itemPrice = item.getPrice();
                        itemDescription = item.getDescription();
                        itemName = item.getName();
                        itemClassification = item.getClassification();
                        String attributes = item.getAttributes();
                        String[] splitedAttribute = attributes.split("\\|");
                        itemSize = splitedAttribute[0];
                        itemColor = splitedAttribute[1];
                        Message itemDetailInitiated = new Message();
                        itemDetailInitiated.obj = ITEM_INITIATED;
                        handler.sendMessage(itemDetailInitiated);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCommentDetailData(){
        if(commentCount.equals("(0)")){
            noCommentLayout.setVisibility(View.VISIBLE);
            commentEmptyTv.setText(" NO COMMENTS ");
        }else {
            noCommentLayout.setVisibility(View.INVISIBLE);
            commentLayout.setVisibility(View.VISIBLE);
        }

        commentUserTv.setText(commentUser);
        commentContentTv.setText(commentContent);
        commentTimeTv.setText(commentTime);
        commentCountTv.setText(commentCount);
    }

    private void setItemsDetailData() {
        StringBuilder itemNameAndDescrip = new StringBuilder(itemName + " | ");
        itemNameAndDescrip.append(itemDescription);
        itemNameTv.setText(itemNameAndDescrip);
        itemPriceTv.setText(itemPrice + "");
        itemSizeList = Arrays.asList(itemSize.split(","));
        itemColorList = Arrays.asList(itemColor.split(","));
    }

    /**
     * Recommandation part, hot sales part
     */
    public void setRecommendGoods() {
    }


    public class CommodityAttribute extends PopupWindow {
        private View CommodityAttributeView;
        private TagFlowLayout mTfSize;
        private TagFlowLayout mTfColor;
        private TextView mTvOk;
        private final mTagAdapter mSizeAdapter;
        private final mTagAdapter mColorAdapter;

        public CommodityAttribute(Activity context) {
            super(context);
            //Select(Sku);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CommodityAttributeView = inflater.inflate(R.layout.item_attribute_selection, null);
            mTfSize = (TagFlowLayout) CommodityAttributeView.findViewById(R.id.tf_size);
            mTfColor = (TagFlowLayout) CommodityAttributeView.findViewById(R.id.tf_color);
            mTvOk = (TextView) CommodityAttributeView.findViewById(R.id.popupwind_ok);


            mColorAdapter = new mTagAdapter(ColorAtt);
            mTfColor.setAdapter(mColorAdapter);
            mColorAdapter.setSelectedList(Color);
            ColorStr = mColorData.get(Color);

            mSizeAdapter = new mTagAdapter(SizeAtt);
            mTfSize.setAdapter(mSizeAdapter);
            mSizeAdapter.setSelectedList(Size);
            SizeStr = mSizeData.get(Size);

            //颜色属性标签点击事件
            mTfColor.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                boolean is;

                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {

                    if (position == DefaultColor) {
                        DefaultColor = -1;
                        ColorStr = "";
                        SizeAtt.FailureAliasName.clear();
                        TagAdapNotify(mSizeAdapter, DefaultSize);
                        return true;
                    } else {
                        DefaultColor = position;
                        ColorStr = mColorData.get(position);
                    }
                    TagAdapNotify(mSizeAdapter, DefaultSize);
                    return true;
                }
            });


            //大小属性标签点击事件
            mTfSize.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                boolean is;

                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (position == DefaultSize) {
                        DefaultSize = -1;
                        SizeStr = "";
                        ColorAtt.FailureAliasName.clear();
                        TagAdapNotify(mColorAdapter, DefaultColor);
                        return true;
                    } else {
                        DefaultSize = position;
                        SizeStr = mSizeData.get(position);
                    }
                    TagAdapNotify(mColorAdapter, DefaultColor);
                    return true;
                }
            });


            mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DefaultColor == -1 || DefaultSize == -1) {
                        return;
                    }

                    itemAttributeColorText.setText(ColorStr + "\t" + SizeStr);
                    Sku = ColorStr + ":" + SizeStr;
                    Size = DefaultSize;
                    Color = DefaultColor;
                    dismiss();
                }
            });


            this.setContentView(CommodityAttributeView);
            // 设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
            // 设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
            // 在PopupWindow里面就加上下面两句代码，让键盘弹出时，不会挡住pop窗口。
            this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 设置popupWindow以外可以触摸
            this.setOutsideTouchable(true);
            // 以下两个设置点击空白处时，隐藏掉pop窗口
            this.setFocusable(true);
            this.setBackgroundDrawable(new BitmapDrawable());
            // 设置popupWindow以外为半透明0-1 0为全黑,1为全白
            backgroundAlpha(0.3f);
            // 添加pop窗口关闭事件
            this.setOnDismissListener(new poponDismissListener());
            // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            CommodityAttributeView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    int height = CommodityAttributeView.findViewById(R.id.pop_layout)
                            .getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {

                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }

    }



    /**
     * PopouWindow设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        this.getActivity().getWindow().setAttributes(lp);

    }


    public void TagAdapNotify(mTagAdapter Adapter, int CcInt) {
        Adapter.getPreCheckedList().clear();
        if (CcInt != -1) {
            Adapter.setSelectedList(CcInt);
        }
        Adapter.notifyDataChanged();
    }


    class mTagAdapter extends TagAdapter<String> {

        private TextView tv;

        public mTagAdapter(Attribute ab) {
            super(ab);
        }

        @Override
        public View getView(FlowLayout parent, int position, Attribute t) {
            boolean is = false;
            //两个布局,一个是可点击的布局，一个是不可点击的布局
            List<String> st = t.FailureAliasName;
            if (st != null) {
                for (int i = 0; i < st.size(); i++) {
                    if (st.get(i).equals(t.aliasName.get(position))) {
                        is = true;
                    }
                }
            }
            if (!is) {
                tv = (TextView) mInflater.inflate(R.layout.item_attribute_tag_available, parent, false);
                tv.setText(t.aliasName.get(position));
            } else {
                tv = (TextView) mInflater.inflate(R.layout.item_attribute_tag_unavailable, parent, false);
                tv.setText(t.aliasName.get(position));
            }

            return tv;
        }
    }

    /**
     * PopouWindow添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public void initialColorSizeData(List<String> itemSizeList,List<String> itemColorList ) {
        //大小属性
        mSizeData = new ArrayList<>();
        mSizeData.addAll(itemSizeList);
        SizeAtt.aliasName.addAll(mSizeData);
        //颜色属性
        mColorData = new ArrayList<>();
        mColorData.addAll(itemColorList);
        ColorAtt.aliasName.addAll(mColorData);

        //所有的Sku
        mAllSkuDate = new ArrayList<>();
        for (int i = 0; i < mColorData.size(); i++) {
            String Colorstr = mColorData.get(i);
            for (int j = 0; j < mSizeData.size(); j++) {
                String s = mSizeData.get(j);
                mAllSkuDate.add(Colorstr + ":" + s);
            }
        }
    }



}
