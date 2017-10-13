package com.example.henryye.floorshop.fragments.searchDrawer;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.henryye.floorshop.R;

/**
 * Created by dan on 17/9/10.
 */

public class EditText_Clear extends android.support.v7.widget.AppCompatEditText {

    private Drawable clearDrawable,searchDrawable;

    public EditText_Clear(Context context) {
        super(context);
        init();
    }

    public EditText_Clear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Clear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.icon_cross);
        searchDrawable = getResources().getDrawable(R.drawable.icon_searchbtn);

        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                null, null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                visible ? clearDrawable : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
                Drawable drawable = clearDrawable;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}

