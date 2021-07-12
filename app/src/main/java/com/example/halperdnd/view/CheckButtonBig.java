package com.example.halperdnd.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.example.halperdnd.R;

public class CheckButtonBig extends CompoundButton {


    public CheckButtonBig(Context context) {
        this(context, null);
    }

    public CheckButtonBig(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonBarButtonStyle);
    }

    public CheckButtonBig(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CheckButtonBig(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CheckButtonBig.class.getName();
    }

}
