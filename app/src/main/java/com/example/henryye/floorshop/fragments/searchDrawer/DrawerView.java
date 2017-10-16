package com.example.henryye.floorshop.fragments.searchDrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryye.floorshop.R;

/**
 * Created by dan on 17/9/11.
 */
public class DrawerView extends LinearLayout {

    private Context context;

    private EditText et_search;
    private TextView tv_clear;
    private LinearLayout search_block;
    private ImageView searchBack;

    private SearchListView listView;
    private SimpleCursorAdapter adapter;

    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    private  ICallBack mCallBack;
    private  bCallBack bCallBack;

    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    private int searchBlockHeight;
    private int searchBlockColor;

    public DrawerView(Context context) {
        super(context, null);
        this.context = context;
        init();
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);

        textSizeSearch = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);

        textColorSearch = typedArray.getColor(R.styleable.Search_View_textColorSearch, Color.BLACK);

        textHintSearch = typedArray.getString(R.styleable.Search_View_textHintSearch);

        searchBlockHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);

        int defaultColor2 = context.getResources().getColor(R.color.bg_color);
        searchBlockColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, defaultColor2);

        typedArray.recycle();
    }

    private void init() {

        initView();

        helper = new RecordSQLiteOpenHelper(context);

        queryData("");

        tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteData();
                queryData("");
            }
        });

        et_search.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (!(mCallBack == null)){
                        mCallBack.SearchAciton(et_search.getText().toString());
                    }

                    Toast.makeText(context, "Searching" + et_search.getText(), Toast.LENGTH_SHORT).show();

                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                }
                return false;
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tempName = et_search.getText().toString();
                queryData(tempName);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            }
        });

//        searchBack.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!(bCallBack == null)){
//                    bCallBack.BackAciton();
//                }
//
//                Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initView(){

        LayoutInflater.from(context).inflate(R.layout.fragment_drawer,this);
//        inflate(context, R.layout.fragment_dr this);
//        inflate(context, R.layout.fragment_drawer, this);

        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setTextSize(textSizeSearch);
        et_search.setTextColor(textColorSearch);
        et_search.setHint(textHintSearch);

        search_block = (LinearLayout)findViewById(R.id.search_block);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) search_block.getLayoutParams();
        params.height = searchBlockHeight;
        search_block.setBackgroundColor(searchBlockColor);
        search_block.setLayoutParams(params);

        listView = (SearchListView) findViewById(R.id.listView);

        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setVisibility(INVISIBLE);

        searchBack = (ImageView) findViewById(R.id.search_back);
    }

    private void queryData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView row = (TextView) view;
                row.setTextColor(Color.BLACK);
                return false;
            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (tempName.equals("") && cursor.getCount() != 0){
            tv_clear.setVisibility(VISIBLE);
        }
        else {
            tv_clear.setVisibility(INVISIBLE);
        };
    }

    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tv_clear.setVisibility(INVISIBLE);
    }

    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        return cursor.moveToNext();
    }

    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;

    }

    public void setOnClickBack(bCallBack bCallBack){
        this.bCallBack = bCallBack;
    }
}
