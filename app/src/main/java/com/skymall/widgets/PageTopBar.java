package com.skymall.widgets;

import android.annotation.TargetApi;
import android.content.Context;
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

import com.skymall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageTopBar extends Toolbar{

    private View mView;

    @BindView(R.id.topbar_searching_view)
    ImageView searchView;

    @BindView(R.id.topbar_searching_text)
    TextView searchText;

    @BindView(R.id.topbar_back_arrow)
    ImageView backArrowButton;

    @BindView(R.id.topbar_register_next)
    ImageView registerNextButton;

    @BindView(R.id.topbar_sign)
    TextView signUpButton;

    public PageTopBar(Context context) {
        this(context, null);
    }

    public PageTopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        if(attrs !=null) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.PageTopBar, defStyleAttr, 0);

            Drawable attrSearchView = a.getDrawable(R.styleable.PageTopBar_searchView);
            if (attrSearchView != null)
                setSearchView(attrSearchView);

            CharSequence attrSearchText = a.getText(R.styleable.PageTopBar_searchText);
            if (attrSearchText != null)
                setSearchText(attrSearchText);

            Drawable attrRegisterNext = a.getDrawable(R.styleable.PageTopBar_registerNext);
            if (attrRegisterNext != null)
                setRegisterNextButton(attrRegisterNext);

            Drawable attrBackArrow = a.getDrawable(R.styleable.PageTopBar_backArrow);
            if (attrBackArrow != null)
                setBackArrowButton(attrBackArrow);

            CharSequence attrSignUp = a.getText(R.styleable.PageTopBar_signUp);
            if (attrSignUp != null)
                setSignUpButton(attrSignUp);

            a.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.widget_topbar, null);

            ButterKnife.bind(this,mView);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mView, lp);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSearchView(Drawable icon) {
        if(searchView !=null){
            searchView.setBackground(icon);
            searchView.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSearchText(CharSequence text) {
        if (searchText != null) {
            searchText.setText(text);
            searchText.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSignUpButton(CharSequence text) {
        if (signUpButton != null) {
            signUpButton.setText(text);
            signUpButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackArrowButton(Drawable icon) {
        if (backArrowButton != null) {
            backArrowButton.setBackground(icon);
            backArrowButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRegisterNextButton(Drawable icon) {

        if (registerNextButton != null) {
            registerNextButton.setBackground(icon);
            registerNextButton.setVisibility(VISIBLE);
        }
    }

    public void showHomepageView() {
        if (searchView != null && searchText != null) {
            searchView.setVisibility(VISIBLE);
            searchText.setVisibility(VISIBLE);
        }
    }

    public void showBackView() {
        if (backArrowButton != null)
            backArrowButton.setVisibility(VISIBLE);
    }

    public void showRegisterView() {
        if (registerNextButton != null ){
            registerNextButton.setVisibility(VISIBLE);
        }
    }

    public void setSearchViewListener(OnClickListener clickListener) {
        searchView.setOnClickListener(clickListener);
    }

    public void setRegisterNextButtonListener(OnClickListener clickListener) {
        registerNextButton.setOnClickListener(clickListener);
    }

    public void setSignUpButtonListener(OnClickListener clickListener) {
        signUpButton.setOnClickListener(clickListener);
    }

    public void setBackArrowButtonListener(OnClickListener clickListener) {
        backArrowButton.setOnClickListener(clickListener);
    }
}
