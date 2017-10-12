package com.example.henryye.floorshop.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henryye.floorshop.R;

/**
 * Created by Dan on 17/9/3.
 */
public class PageTopBar extends Toolbar {

    private LayoutInflater mInflater;

    private View mView;
    private ImageView searchButton;
    private TextView title;
    private Typeface typeFace;

    public PageTopBar(Context context) {
        this(context, null);
    }

    public PageTopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/billabong.ttf");
        initView();
        setContentInsetsRelative(0, 0);

        if(attrs !=null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.PageTopBar, defStyleAttr, 0);

            final Drawable searchButton = a.getDrawable(R.styleable.PageTopBar_searchButton);
            if (searchButton != null) {
                setSearchButtonIcon(searchButton);
            }

            final CharSequence homepageTitle = a.getText(R.styleable.PageTopBar_homepageTitle);
            if (homepageTitle != null) {
                setHomepageTitle(homepageTitle);
            }

            a.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_topbar, null);

            searchButton = (ImageView) mView.findViewById(R.id.topbar_search);
            title = (TextView) mView.findViewById(R.id.topbar_homepage_title);
            title.setTypeface(typeFace);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSearchButtonIcon(Drawable icon){

        if(searchButton !=null){
            searchButton.setBackground(icon);
            searchButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setHomepageTitle(CharSequence text){

        if(title !=null){
            title.setText(text);
            searchButton.setVisibility(VISIBLE);
        }
    }

    public void showHomepageView() {
        if (searchButton != null)
            searchButton.setVisibility(VISIBLE);

        if (title != null)
            title.setVisibility(VISIBLE);
    }
}
