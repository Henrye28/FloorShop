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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.henryye.floorshop.R;

/**
 * Created by Dan on 17/9/3.
 */
public class PageTopBar extends Toolbar {

    private LayoutInflater mInflater;

    private View mView;
    private ImageView searchButton;
    private TextView homepageTitle;
    private LinearLayout communityLayout;
    private TextView communityFollow;
    private TextView communityDiscovery;
    private ImageView commentButton;
    private ImageView meShareButton;
    private TextView meTitle;
    private Typeface homeTypeFace;
    private Typeface communityTypeFace;

    public PageTopBar(Context context) {
        this(context, null);
    }

    public PageTopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        homeTypeFace = Typeface.createFromAsset(context.getAssets(),"fonts/billabong.ttf");
        communityTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Condensed.ttf");
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

            final CharSequence communityFollow = a.getText(R.styleable.PageTopBar_communityFollow);
            if (communityFollow != null) {
                setCommunityFollow(communityFollow);
            }

            final CharSequence communityDiscovery = a.getText(R.styleable.PageTopBar_communityDiscovery);
            if (communityDiscovery != null) {
                setCommunityDiscovery(communityDiscovery);
            }

            final Drawable communityComment = a.getDrawable(R.styleable.PageTopBar_communityComment);
            if (communityComment != null) {
                setCommentButton(communityComment);
            }

            final Drawable meShare = a.getDrawable(R.styleable.PageTopBar_meShare);
            if (meShare != null) {
                setMeShareButton(meShare);
            }

            final CharSequence meTitle = a.getText(R.styleable.PageTopBar_meTitle);
            if (meTitle != null) {
                setMeTitle(meTitle);
            }
            a.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_topbar, null);

            searchButton = (ImageView) mView.findViewById(R.id.topbar_search);
            homepageTitle = (TextView) mView.findViewById(R.id.topbar_homepage_title);
            homepageTitle.setTypeface(homeTypeFace);
            communityLayout = (LinearLayout) mView.findViewById(R.id.topbar_community_title);
            communityFollow = (TextView) mView.findViewById(R.id.topbar_community_follow);
            communityFollow.setTypeface(communityTypeFace);
            communityDiscovery = (TextView) mView.findViewById(R.id.topbar_community_discovery);
            communityDiscovery.setTypeface(communityTypeFace);
            commentButton = (ImageView) mView.findViewById(R.id.topbar_comment);
            meShareButton = (ImageView) mView.findViewById(R.id.topbar_me_share);
            meTitle = (TextView) mView.findViewById(R.id.topbar_me_title);
            meTitle.setTypeface(homeTypeFace);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mView, lp);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSearchButtonIcon(Drawable icon) {

        if(searchButton !=null){
            searchButton.setBackground(icon);
            searchButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setHomepageTitle(CharSequence text) {

        if(homepageTitle !=null){
            homepageTitle.setText(text);
            homepageTitle.setVisibility(VISIBLE);
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

    public void showHomepageView() {
        if (searchButton != null)
            searchButton.setVisibility(VISIBLE);

        if (homepageTitle != null)
            homepageTitle.setVisibility(VISIBLE);
    }

    public void showCommunityView() {
        if (searchButton != null)
            searchButton.setVisibility(VISIBLE);

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

}
