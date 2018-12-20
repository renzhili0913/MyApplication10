package com.example.renzhili20181220.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.renzhili20181220.R;

@SuppressLint("AppCompatCustomView")
public class WeekGroupNameLayout extends TextView {
    public WeekGroupNameLayout(Context context) {
        super(context);
    }

    public WeekGroupNameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeekGroupNameLayout);
        int color = typedArray.getColor(R.styleable.WeekGroupNameLayout_textColor, Color.RED);
        setTextColor(color);
        typedArray.recycle();
    }

    public WeekGroupNameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
