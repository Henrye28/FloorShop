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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageTopBar extends Toolbar{

    private LayoutInflater mInflater;
    private View mView;

    @BindView(R.id.topbar_searching_view)
    ImageView searchView;

    @BindView(R.id.topbar_community_title)
    LinearLayout communityLayout;

    @BindView(R.id.topbar_community_follow)
    TextView communityFollow;

    @BindView(R.id.topbar_community_discovery)
    TextView communityDiscovery;

    @BindView(R.id.topbar_comment)
    ImageView commentButton;

    @BindView(R.id.topbar_me_share)
    ImageView meShareButton;

    @BindView(R.id.topbar_me_title)
    TextView meTitle;

    @BindView(R.id.topbar_register_next)
    ImageView registerNextButton;

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
                setSearchView(attrSearchView);
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

            final Drawable registerNext = a.getDrawable(R.styleable.PageTopBar_registerNext);
            if (registerNext != null) {
                setRegisterNextButton(registerNext);
            }
            a.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_topbar, null);

            ButterKnife.bind(this,mView);

//            communityLayout = (LinearLayout) mView.findViewById(R.id.topbar_community_title);
//            communityFollow = (TextView) mView.findViewById(R.id.topbar_community_follow);
            communityFollow.setTypeface(textTypeFace);
//            communityDiscovery = (TextView) mView.findViewById(R.id.topbar_community_discovery);
            communityDiscovery.setTypeface(textTypeFace);
//            commentButton = (ImageView) mView.findViewById(R.id.topbar_comment);
//            meShareButton = (ImageView) mView.findViewById(R.id.topbar_me_share);
//            meTitle = (TextView) mView.findViewById(R.id.topbar_me_title);
//            registerNextButton = (ImageView) mView.findViewById(R.id.topbar_register_next);

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
        if (searchView != null)
            searchView.setVisibility(VISIBLE);

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

    public void setSearchViewListener(OnClickListener clickListener) {
        searchView.setOnClickListener(clickListener);
    }

    public void setMeShareButton(OnClickListener clickListener) {
        meShareButton.setOnClickListener(clickListener);
    }

    public void setRegisterNextButtonListener(OnClickListener clickListener) {
        registerNextButton.setOnClickListener(clickListener);
    }
}
