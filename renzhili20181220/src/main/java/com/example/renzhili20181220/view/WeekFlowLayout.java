package com.example.renzhili20181220.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class WeekFlowLayout extends LinearLayout {
    private int mChildMaxHeight;
    private int mHSpace=20;
    private int mVSpace=20;
    public WeekFlowLayout(Context context) {
        super(context);
    }

    public WeekFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        /**
         * 调用内部类中寻找孩子中最高的一个孩子的方法，找到把值放在mChildMaxHeight变量中
         * */
        findMaxChildMaxHeight();
        //初始化值
        int left=0;
        int top=0;
        //得到所有的孩子并循环所有的孩子
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (left!=0){
                //如果子元素的宽度加上左边距大于父元素的推荐宽，则需要换行了,因为放不下啦
                if ((left+view.getMeasuredWidth())>sizeWidth){
                    top+=mChildMaxHeight+mVSpace;
                    left=0;
                }
            }
            left+=view.getMeasuredWidth()+mHSpace;
        }
        setMeasuredDimension(sizeWidth,(top+mChildMaxHeight)>sizeHeight?sizeHeight:top+mChildMaxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findMaxChildMaxHeight();
        //初始化值
        int left = 0, top = 0;
        //循环所有的孩子
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //是否是一行的开头
            if (left != 0) {
                //需要换行了,因为放不下啦
                if ((left + view.getMeasuredWidth()) > getWidth()) {
                    //计算出下一行的top
                    top += mChildMaxHeight + mVSpace;
                    left = 0;
                }
            }
            //安排孩子的位置
            view.layout(left, top, left + view.getMeasuredWidth(), top + mChildMaxHeight);
            left += view.getMeasuredWidth() + mHSpace;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    /**
     * 寻找孩子中最高的一个孩子
     */
    private void findMaxChildMaxHeight() {
        mChildMaxHeight=0;
        //获取组视图中子视图的数量
        int childCount=getChildCount();
        for (int i = 0 ;i<childCount;i++){
            //返回指定位置的子视图
            View view = getChildAt(i);
            //判断指定位置的视图的高度是否大于布局中最高的孩子，如果是则把高的赋予最高孩子
            if (view.getMeasuredHeight()>mChildMaxHeight){
                mChildMaxHeight=view.getMeasuredHeight();
            }
        }
    }
}
