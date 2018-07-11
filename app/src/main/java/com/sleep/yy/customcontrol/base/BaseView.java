package com.sleep.yy.customcontrol.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sleep.yy.customcontrol.util.LogUtil;
import com.sleep.yy.customcontrol.util.MeasureUtil;

public abstract class BaseView extends View {

    protected int mWidth;
    protected int mHeight;
    protected OnTouchControl mTouchControl;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureUtil.measureSize(widthMeasureSpec, mWidth > 0 ? mWidth : 500);
        mHeight = MeasureUtil.measureSize(heightMeasureSpec, mHeight > 0 ? mHeight : 300);
        setMeasuredDimension(mWidth, mHeight);
        LogUtil.d(getClass().getName(), "onMeasure() --- mWidth = " + mWidth + " --- mHeight = " + mHeight);
    }

    protected void setDefaultSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void setOnTouchControl(OnTouchControl paramOnTouchControl)
    {
        mTouchControl = paramOnTouchControl;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        boolean bool = false;
        if (mTouchControl != null) {
            bool = mTouchControl.onTouch(paramMotionEvent);
        }
        return (bool) || (super.onTouchEvent(paramMotionEvent));
    }

    public static abstract interface OnTouchControl
    {
        public abstract boolean onTouch(MotionEvent paramMotionEvent);
    }
}
