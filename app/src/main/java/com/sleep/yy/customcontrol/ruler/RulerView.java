package com.sleep.yy.customcontrol.ruler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

public class RulerView extends BaseView {

    private static final String TAG = "RulerView";
    private Paint mScalePaint;
    private Paint mPointerPaint;
    private Point mPoint;
    private Path mPointPath;

    private Scroller mScroller;
    private VelocityTracker mVTracker;

    private float mLastX = -1;
    private int mOffset;
    private int blank = 150;
    private int lineLength = 150;
    private int scalePaintWidth = 3;
    private int maxCount = 50;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    protected void init() {
        setClickable(true);
        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint.setColor(Color.BLACK);
        mScalePaint.setStrokeWidth(scalePaintWidth);
        mScalePaint.setTextSize(48);
        mScalePaint.setTextAlign(Paint.Align.CENTER);

        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(Color.GREEN);

        mPoint = new Point();

        mScroller = new Scroller(getContext());

        setDefaultSize(500, 700);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawPointer(canvas);
    }

    private void drawScale(Canvas canvas) {
        int startX = mWidth / 2 + mOffset;
        int offsetIndex = 0;
        int startY = mHeight / 2 - lineLength / 2;
        if (startX < 0) {
            offsetIndex = -startX / blank;
            startX = startX % blank;
        }
        //LogUtil.d(TAG, "drawScale() --- startX = " + startX + " --- offsetIndex = " + offsetIndex);
        int start = startX;
        for (int i = offsetIndex; i < mWidth / blank + offsetIndex + 2; i++) {
            int stopY = mHeight / 2 + lineLength / 2;
            canvas.drawLine(startX, startY, startX, stopY, mScalePaint);
            canvas.drawText(String.valueOf(i), startX, stopY + +lineLength / 2, mScalePaint);
            if (i >= maxCount) {
                break;
            }
            startX = startX + blank;
        }
        canvas.drawLine(start, startY, startX, startY, mScalePaint);
    }

    private void drawPointer(Canvas canvas) {
        if (mPointPath == null) {
            mPointPath = new Path();
            mPointPath.moveTo(mWidth / 2 - blank / 3, mHeight / 2 - lineLength / 2 - scalePaintWidth);
            mPointPath.lineTo(mWidth / 2 + blank / 3, mHeight / 2 - lineLength / 2 - scalePaintWidth);
            mPointPath.lineTo(mWidth / 2, mHeight / 2 - lineLength / 4 - scalePaintWidth);
        }
        canvas.drawPath(mPointPath, mPointerPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reset();
                break;

            case MotionEvent.ACTION_MOVE:
                scrollByOffset((int) (x - mLastX));
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVTracker.computeCurrentVelocity(1000, 8000);
                int xVelocity = (int) mVTracker.getXVelocity();
                if (xVelocity > 8000) {
                    xVelocity = 8000;
                }
                if (xVelocity < -8000) {
                    xVelocity = -8000;
                }
                mScroller.fling(0, 0, xVelocity / 10, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                LogUtil.d(TAG, "onTouchEvent() --- xVelocity = " + xVelocity);
                releaseVelocity();
                break;


        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        mLastX = -1;
        mScroller.forceFinished(true);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            LogUtil.d(TAG, "computeScroll() ---  x = " + x);
            scrollByOffset(x);
        }
    }

    private void scrollByOffset(int distance) {
        if (mLastX >= 0) {
            mOffset = mOffset + distance;
        }
        if (mOffset > 0) {
            mOffset = 0;
        }
        if (mOffset < -(maxCount * blank)) {
            mOffset = -(maxCount * blank);
        }
        LogUtil.d(TAG, "scrollByOffset() --- mOffset = " + mOffset);
        mLastX = distance + mLastX;
        postInvalidate();
    }

    private void addMovement(MotionEvent event) {
        if (mVTracker == null) {
            mVTracker = VelocityTracker.obtain();
        }
        mVTracker.addMovement(event);
    }

    private void releaseVelocity() {
        if (mVTracker != null) {
            mVTracker.clear();
        }
    }
}
