package com.skymall.fragments.searchDrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.skymall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 17/10/19.
 */
public class SearchHistoryView extends ViewGroup {

    private List<List<View>> mAllChildViews = new ArrayList<>();

    private List<Integer> mLineHeight = new ArrayList<>();

    public SearchHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawerSearchHistoryView);
        int id = typedArray.getResourceId(R.styleable.DrawerSearchHistoryView_linkedView, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for(int i = 0;i < childCount; i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if(lineWidth + childWidth > sizeWidth){
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if(i == childCount -1){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllChildViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for(int i = 0;i < childCount; i ++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if(childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width){
                mLineHeight.add(lineHeight);
                mAllChildViews.add(lineViews);
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        int left = 0;
        int top = 0;
        int lineCount = mAllChildViews.size();
        for(int i = 0; i < lineCount; i ++){
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for(int j = 0; j < lineViews.size(); j ++){
                View child = lineViews.get(j);
                if(child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
