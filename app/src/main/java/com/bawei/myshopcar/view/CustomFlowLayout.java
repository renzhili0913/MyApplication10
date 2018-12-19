package com.bawei.myshopcar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 所有记录的流式布局,这个很熟悉
 */
public class CustomFlowLayout extends LinearLayout {
    private int mChildMaxHeight;
    private int mHSpace = 20;
    private int mVSpace = 20;

    public CustomFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        findMaxChildMaxHeight();
        int left = 0, top = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (left != 0) {
                if ((left + view.getMeasuredWidth()) > sizeWidth) {
                    top += mChildMaxHeight + mVSpace;
                    left = 0;
                }
            }
            left += view.getMeasuredWidth() + mHSpace;
        }
        setMeasuredDimension(sizeWidth, (top + mChildMaxHeight) > sizeHeight ? sizeHeight : top + mChildMaxHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findMaxChildMaxHeight();
        int left = 0, top = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (left != 0) {
                if ((left + view.getMeasuredWidth()) > getWidth()) {
                    top += mChildMaxHeight + mVSpace;
                    left = 0;
                }
            }

            view.layout(left, top, left + view.getMeasuredWidth(), top + mChildMaxHeight);
            left += view.getMeasuredWidth() + mHSpace;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    private void findMaxChildMaxHeight() {
        mChildMaxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getMeasuredHeight() > mChildMaxHeight) {
                mChildMaxHeight = view.getMeasuredHeight();
            }
        }
    }
}