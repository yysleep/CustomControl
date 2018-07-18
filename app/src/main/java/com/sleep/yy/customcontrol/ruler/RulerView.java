package com.sleep.yy.customcontrol.ruler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

public class RulerView extends BaseView {

    private static final String TAG = "RulerView";
    private Paint mScalePaint;
    private Paint mPointerPaint;
    private Paint mTextPaint;
    private Path mPointPath;

    private Scroller mScroller;
    private VelocityTracker mVTracker;
    private Context context;

    private float mLastX;
    private int blank;
    private int scalePaintWidth;
    private int maxCount = 50;
    private int maxLength;
    private int textOffsetY;
    private int testSize;
    private int pointerLength;
    private int scaleLength;

    private boolean inBoundary;


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
        context = getContext();
        setClickable(true);

        testSize = 48;
        textOffsetY = testSize * 2;
        scalePaintWidth = 3;
        blank = 150;

        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint.setColor(Color.WHITE);
        mScalePaint.setStrokeWidth(scalePaintWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStrokeWidth(scalePaintWidth);
        mTextPaint.setTextSize(testSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(Color.WHITE);

        mPointPath = new Path();
        mScroller = new Scroller(getContext());

        setDefaultSize(500, 400);
        maxLength = maxCount * blank;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        pointerLength = mHeight / 8;
        scaleLength = mHeight * 2 / 5;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawScale(canvas);
        drawPointer(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(context.getResources().getColor(R.color.cloudGray));
    }

    private void drawScale(Canvas canvas) {
        int startX;
        int startY = 0;
        int scrollX = getScrollX();
        int startIndex = 0;
        int length = scrollX - mWidth / 2;
        if (length > 0) {
            startIndex = length / blank;
        }
        LogUtil.d(TAG, "drawScale() --- startIndex = " + startIndex);
        for (; startIndex <= maxCount; startIndex++) {

            startX = startIndex * blank + mWidth / 2;
            canvas.drawLine(startX, startY, startX, scaleLength, mScalePaint);
            canvas.drawText(String.valueOf(startIndex), startX, scaleLength + textOffsetY, mTextPaint);
            if (startX > scrollX + mWidth) {
                break;
            }
        }
    }

    private void drawPointer(Canvas canvas) {
        if (mPointPath != null) {
            int startY = 0;
            mPointPath.reset();
            int scrollX = getScrollX();
            mPointPath.moveTo(mWidth / 2 - blank / 3 + scrollX, startY);
            mPointPath.lineTo(mWidth / 2 + blank / 3 + scrollX, startY);
            mPointPath.lineTo(mWidth / 2 + scrollX, pointerLength);
            canvas.drawPath(mPointPath, mPointerPaint);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        addMovement(event);
        int scrollX = getScrollX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                reset();
                break;

            case MotionEvent.ACTION_MOVE:
                float distance = x - mLastX;
                LogUtil.d(TAG, "onTouchEvent() --- distance = " + distance + " --- scrollX = " + scrollX);
                if ((scrollX == 0 && distance >= 0) || (scrollX == maxCount * blank && distance <= 0)) {
                    break;
                }
                if (scrollX < 0 && distance > 0) {
                    scrollTo(0, 0);
                } else if (scrollX > maxLength && distance < 0) {
                    scrollTo(maxLength, 0);
                } else {
                    scrollBy((int) -distance, 0);
                }

                mLastX = x;
                postInvalidate();
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
                mScroller.fling(scrollX, 0, -xVelocity / 2, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
                LogUtil.d(TAG, "onTouchEvent() --- xVelocity = " + xVelocity);
                releaseVelocity();
                break;


        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        inBoundary = false;
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            if (inBoundary) {
                mScroller.abortAnimation();
                return;
            }
            int x = mScroller.getCurrX();
            if (x < 0) {
                x = 0;
                inBoundary = true;
            } else if (x > maxLength) {
                x = maxLength;
                inBoundary = true;
            }
            scrollTo(x, 0);

            LogUtil.d(TAG, "computeScroll() ---  x = " + x);
            postInvalidate();
        }

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
