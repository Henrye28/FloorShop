package com.skymall.widgets;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skymall.R;

public class PageTopBar extends Toolbar{

    private LayoutInflater mInflater;

    private View mView;
    private ImageView searchView;
    private LinearLayout communityLayout;
    private TextView communityFollow;
    private TextView communityDiscovery;
    private ImageView commentButton;
    private ImageView meShareButton;
    private TextView meTitle;
    private ImageView registerNextButton;
    private Typeface textTypeFace;

    public PageTopBar(Context context) {
        this(context, null);
    }

    public PageTopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Condensed.ttf");
        initView();
        setContentInsetsRelative(0, 0);

        if(attrs !=null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.PageTopBar, defStyleAttr, 0);

            final Drawable attrSearchView = a.getDrawable(R.styleable.PageTopBar_searchView);
            if (attrSearchView != null) {
                setSearchViewIcon(attrSearchView);
            }

            final CharSequence attrCommunityFollow = a.getText(R.styleable.PageTopBar_communityFollow);
            if (attrCommunityFollow != null) {
                setCommunityFollow(attrCommunityFollow);
            }

            final CharSequence attrCommunityDiscovery = a.getText(R.styleable.PageTopBar_communityDiscovery);
            if (attrCommunityDiscovery != null) {
                setCommunityDiscovery(attrCommunityDiscovery);
            }

            final Drawable attrCommunityComment = a.getDrawable(R.styleable.PageTopBar_communityComment);
            if (attrCommunityComment != null) {
                setCommentButton(attrCommunityComment);
            }

            final Drawable attrMeShare = a.getDrawable(R.styleable.PageTopBar_meShare);
            if (attrMeShare != null) {
                setMeShareButton(attrMeShare);
            }

            final CharSequence attrMeTitle = a.getText(R.styleable.PageTopBar_meTitle);
            if (attrMeTitle != null) {
                setMeTitle(attrMeTitle);
            }

            final Drawable attrRegisterNext = a.getDrawable(R.styleable.PageTopBar_registerNext);
            if (attrRegisterNext != null) {
                setRegisterNextButton(attrRegisterNext);
            }
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_topbar, null);

            searchView = (ImageView) mView.findViewById(R.id.topbar_searching_view);
            communityLayout = (LinearLayout) mView.findViewById(R.id.topbar_community_title);
            communityFollow = (TextView) mView.findViewById(R.id.topbar_community_follow);
            communityFollow.setTypeface(textTypeFace);
            communityDiscovery = (TextView) mView.findViewById(R.id.topbar_community_discovery);
            communityDiscovery.setTypeface(textTypeFace);
            commentButton = (ImageView) mView.findViewById(R.id.topbar_comment);
            meShareButton = (ImageView) mView.findViewById(R.id.topbar_me_share);
            meTitle = (TextView) mView.findViewById(R.id.topbar_me_title);
            meTitle.setTypeface(textTypeFace);
            registerNextButton = (ImageView) mView.findViewById(R.id.topbar_register_next);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mView, lp);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSearchViewIcon(Drawable icon) {

        if(searchView !=null){
            searchView.setBackground(icon);
            searchView.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCommunityFollow(CharSequence text) {

        if (communityFollow != null) {
            communityFollow.setText(text);
            communityFollow.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCommunityDiscovery(CharSequence text) {

        if (communityDiscovery != null) {
            communityDiscovery.setText(text);
            communityDiscovery.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCommentButton(Drawable icon) {

        if (commentButton != null) {
            commentButton.setBackground(icon);
            commentButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setMeShareButton(Drawable icon) {

        if (meShareButton != null) {
            meShareButton.setBackground(icon);
            meShareButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setMeTitle(CharSequence text) {

        if (meTitle != null) {
            meTitle.setText(text);
            meTitle.setVisibility(VISIBLE);
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
        if (searchView != null)
            searchView.setVisibility(VISIBLE);
    }

    public void showCommunityView() {

        if (communityLayout != null)
            communityLayout.setVisibility(VISIBLE);

        if (commentButton != null)
            commentButton.setVisibility(VISIBLE);
    }

    public void showMeView() {
        if (meShareButton != null)
            meShareButton.setVisibility(VISIBLE);

        if (meTitle != null)
            meTitle.setVisibility(VISIBLE);

        if (commentButton != null) {
            commentButton.setVisibility(VISIBLE);
        }
    }

    public void showRegisterView() {
        if (registerNextButton != null ){
            registerNextButton.setVisibility(VISIBLE);
        }
    }

    public void setCommentButtonButton(OnClickListener clickListener) {
        commentButton.setOnClickListener(clickListener);
    }

    public void setSearchView(OnClickListener clickListener) {
        searchView.setOnClickListener(clickListener);
    }

    public void setMeShareButton(OnClickListener clickListener) {
        meShareButton.setOnClickListener(clickListener);
    }

    public void setRegisterNextButton(OnClickListener clickListener) {
        registerNextButton.setOnClickListener(clickListener);
    }
}
