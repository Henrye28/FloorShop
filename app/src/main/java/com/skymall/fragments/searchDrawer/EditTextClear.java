package com.skymall.fragments.searchDrawer;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.skymall.R;

/**
 * Created by dan on 17/9/10.
 */

public class EditTextClear extends android.support.v7.widget.AppCompatEditText {

    private Drawable clearDrawable;

    private Paint mPaint;
    private int width;
    private int height;

    public EditTextClear(Context context) {
        this(context, null);
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
        init();
    }

    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.icon_cross);
        this.setBackground(getResources().getDrawable(R.drawable.shape_edittext));
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
        setCompoundDrawablesWithIntrinsicBounds(null, null,
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

