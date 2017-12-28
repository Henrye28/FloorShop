package com.skymall.fragments.searchDrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.skymall.R;
import com.skymall.widgets.PageTopBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dan on 17/9/11.
 */
public class DrawerView extends LinearLayout {

    private Context context;

    @BindView(R.id.drawer_top_bar)
    PageTopBar topBar;

    @BindView(R.id.drawer_et_search)
    EditText et_search;

    @BindView(R.id.drawer_history_view)
    SearchHistoryView historyView;

    private ArrayList<String> historySearch = new ArrayList<>();
    private MarginLayoutParams layoutParams;

    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    private ICallBack mCallBack;
    private bCallBack bCallBack;

    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    private Float searchBlockHeight;
    private int searchBlockColor;

    public DrawerView(Context context) {
        this(context, null);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawerSearchView);

        textSizeSearch = typedArray.getDimension(R.styleable.DrawerSearchView_textSizeSearch, getResources().getDimension(R.dimen.x17));

        textColorSearch = typedArray.getColor(R.styleable.DrawerSearchView_textColorSearch, Color.rgb(22, 23, 45));

        textHintSearch = typedArray.getString(R.styleable.DrawerSearchView_textHintSearch);

        searchBlockHeight = typedArray.getDimension(R.styleable.DrawerSearchView_searchBlockHeight, getResources().getDimension(R.dimen.x46));

        searchBlockColor = typedArray.getColor(R.styleable.DrawerSearchView_searchBlockColor, Color.argb(3, 22, 23, 45));

        typedArray.recycle();
    }

    private void init() {

        layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        initView();

        helper = new RecordSQLiteOpenHelper(context);

        queryData("");

//        tv_clear.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                deleteData();
//                queryData("");
//            }
//        });

        et_search.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (!(mCallBack == null)) {
                        mCallBack.SearchAciton(et_search.getText().toString());
                    }

                    Toast.makeText(context, "Searching" + et_search.getText(), Toast.LENGTH_SHORT).show();

                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        historySearch.add(et_search.getText().toString().trim());
                        addTextView(historySearch.size() - 1);
                    }
                }
                return false;
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                TextView textView = (TextView) view.findViewById(android.R.id.text1);
//                String name = textView.getText().toString();
//                et_search.setText(name);
//                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.fragment_drawer, this);
        ButterKnife.bind(this);

        topBar.showBackView();

        historyView = (SearchHistoryView) findViewById(R.id.drawer_history_view);

        et_search.requestFocus();
    }

    private void queryData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);

        if (cursor.moveToFirst()) {
            for (int i=0; i<cursor.getCount(); i++) {
                cursor.move(i);
                String record = cursor.getString(1);
                historySearch.add(record);
            }
        }

        for (int i = 0; i < historySearch.size(); i++) {
            addTextView(i);
        }
    }

    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
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

    private void addTextView(int index) {
        final TextView textView = new TextView(context);
        textView.setTag(index);
        textView.setTextSize(getResources().getDimension(R.dimen.x15));
        textView.setText(historySearch.get(index));
        textView.setPadding((int) getResources().getDimension(R.dimen.x20), 0, 8, 0);
        textView.setBackgroundResource(R.drawable.shape_search_history);
        historyView.addView(textView, layoutParams);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, historySearch.get((int) textView.getTag()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;

    }

    public void setOnClickBack(bCallBack bCallBack){
        this.bCallBack = bCallBack;
    }
}
