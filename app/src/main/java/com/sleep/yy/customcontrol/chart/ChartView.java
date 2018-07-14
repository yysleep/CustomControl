package com.sleep.yy.customcontrol.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YySleep on 2018/7/10
 *
 * @author YySleep
 */

public class ChartView extends BaseView {

    private static final String TAG = "ChartView";
    private Context context;
    private Path mAxisLinePath;
    private Paint mAxisPathPaint;
    private Path mDataLinePath;
    private Paint mDataPathPaint;
    private Paint mPointPaint;
    private Point point;

    private OverScroller mScroller;
    private Paint mTextPaint;
    private VelocityTracker mVTracker;


    private int pointOffset;
    private float[] relPoints;
    private ArrayList<Float> pointList;
    private int textSize;
    private int timeBlank;
    private int timeSize;
    private int timeTextOffsetX;
    private int timeTextOffsetY;
    private float mLastX;
    private int count = 0;
    private int dataOffset;
    private int maxFlingVelocity;
    private int maxIndex;
    private int minFlingVelocity;
    private int mpaTextOffsetX;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingTop;
    private int maxMpa = 1200;
    private List<ChartData> dataList;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {

        dataList = new ArrayList<>();
        pointList = new ArrayList<>();
        context = getContext();
        timeBlank = 100;
        textSize = 24;
        paddingTop = 32;
        paddingBottom = 32;
        pointOffset = 8;
        timeTextOffsetX = pointOffset;
        timeTextOffsetY = 16;
        mpaTextOffsetX = 16;
        mLastX = -1.0F;
        maxIndex = 50;

        setOnTouchControl(new Control());
        setDefaultSize(800, 1000);

        mTextPaint = new Paint(1);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setColor(-1);

        mAxisPathPaint = new Paint(1);
        mAxisPathPaint.setStyle(Paint.Style.STROKE);
        mAxisPathPaint.setStrokeWidth(4.0F);
        mAxisPathPaint.setColor(-1);

        mDataPathPaint = new Paint(1);
        mDataPathPaint.setStyle(Paint.Style.STROKE);
        mDataPathPaint.setStrokeWidth(16.0F);
        mDataPathPaint.setColor(context.getResources().getColor(R.color.chart_color));
        mDataPathPaint.setStrokeJoin(Paint.Join.ROUND);

        mPointPaint = new Paint(1);
        mPointPaint.setStrokeWidth(16.0F);
        mPointPaint.setColor(-1);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);

        mAxisLinePath = new Path();
        mDataLinePath = new Path();
        point = new Point();

        setClickable(true);
        setEnabled(true);

        mScroller = new OverScroller(getContext());
        ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
        minFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
        maxFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    }

    public void setDataList(List<ChartData> list) {
        this.dataList = list;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        reset();
        calculateTimeSize();
        drawBackground(canvas);
        drawAxisLine(canvas);
        drawMpa(canvas);
        drawTime(canvas);
        drawData(canvas);
    }

    private void calculateTimeSize() {
        timeSize = "2018/1/18 23:15:30".length() * textSize / 2;
        timeSize = (int) Math.sqrt(timeSize * timeSize / 2) + 10;
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(context.getResources().getColor(R.color.chart_color));
        canvas.save();
        canvas.clipRect(timeSize, paddingTop, mWidth - timeSize + pointOffset, mHeight - timeSize - paddingBottom);
        canvas.drawColor(context.getResources().getColor(R.color.chart_background_color));
        canvas.restore();
    }

    private void drawAxisLine(Canvas canvas) {
        point.x = timeSize;
        point.y = paddingTop;
        mAxisLinePath.moveTo(point.x, point.y);
        point.y = (mHeight - timeSize - paddingBottom);
        mAxisLinePath.lineTo(point.x, point.y);
        point.x = mWidth - timeSize;
        mAxisLinePath.lineTo(point.x, point.y);
        canvas.drawPath(mAxisLinePath, mAxisPathPaint);
    }

    private void drawMpa(Canvas canvas) {
        point.x = (timeSize - mpaTextOffsetX);
        int i = 0;
        while (i < 7) {
            point.y = (mHeight - timeSize - paddingBottom - (mHeight - timeSize - paddingTop - paddingBottom) / 6 * i);
            canvas.drawText(i * 200 + "Mpa", point.x, point.y, mTextPaint);
            i++;
        }
    }

    private void drawTime(Canvas canvas) {
        point.y = (mHeight - timeSize - paddingBottom + timeTextOffsetY);
        int start = 0;
        int startX = timeSize + pointOffset;
        if (dataOffset < -startX) {
            start = -dataOffset / timeBlank - 1;
        }

        for (int i = start; i < dataList.size(); i++) {
            point.x = startX + timeBlank * i + dataOffset;
            if (point.x < timeSize - pointOffset) {
                continue;
            }
            if (point.x > mWidth - timeSize) {
                break;
            }
            canvas.save();
            canvas.rotate(-45.0F, point.x, point.y);
            canvas.drawText(dataList.get(i).time, point.x, point.y, mTextPaint);
            canvas.restore();
        }
    }

    private void drawData(Canvas canvas) {
        canvas.save();
        canvas.clipRect(timeSize, paddingTop, mWidth - timeSize + pointOffset, mHeight - timeSize - paddingBottom);
        drawDataLine(canvas);
        drawDataPoints(canvas);
        canvas.restore();
    }

    private void drawDataLine(Canvas canvas) {
        int startX = timeSize + pointOffset;
        int start = 0;
        boolean isFirst = true;
        if (dataOffset < -startX) {
            start = -dataOffset / timeBlank;
        }
        int lineHeight = mHeight - paddingTop - timeSize - paddingBottom;
        for (int i = start; i < dataList.size(); i++) {
            point.x = startX + timeBlank * i + dataOffset;
            point.y = lineHeight - lineHeight * dataList.get(i).mpa / maxMpa;
            LogUtil.d(TAG, "drawData() --- point.x = " + point.x + " --- mWidth = " + mWidth);
            if (point.x > mWidth - timeSize + timeBlank) {
                break;
            }
            if (isFirst) {
                mDataLinePath.moveTo(point.x, point.y);
                isFirst = false;
            } else {
                mDataLinePath.lineTo(point.x, point.y);
            }
            pointList.add((float) point.x);
            pointList.add((float) point.y);
        }

        canvas.drawPath(mDataLinePath, mDataPathPaint);
    }

    private void drawDataPoints(Canvas canvas) {
        initPoints();
        if (relPoints != null && relPoints.length > 0) {
            canvas.drawPoints(relPoints, mPointPaint);
        }
    }

    private void reset() {
        mAxisLinePath.reset();
        mDataLinePath.reset();
        pointList.clear();
        relPoints = null;
    }

    private void addVelocityTracker(MotionEvent paramMotionEvent) {
        if (mVTracker == null) {
            mVTracker = VelocityTracker.obtain();
        }
        mVTracker.addMovement(paramMotionEvent);
    }

    private void resetScroll() {
        mLastX = -1.0F;
        count = 0;
    }

    public void computeScroll() {
        if ((mScroller != null) && (mScroller.computeScrollOffset())) {
            count += 1;
            int i = mScroller.getCurrX();
            moveData(i + mLastX);
            LogUtil.d("ChartView", "computeScroll() --- x = " + i + " --- count = " + count);
        }
    }

    private void moveData(float x) {
        float distance = x - mLastX;
        if (mLastX >= 0.0F) {
            int length = (dataList.size() - 1) * timeBlank - (mWidth - timeSize * 2 - pointOffset);
            if (((dataOffset == 0) && (distance > 0)) || ((distance < 0) && (-dataOffset == length))) {
                return;
            }
            dataOffset = ((int) distance + dataOffset);
            if (dataOffset > 0) {
                dataOffset = 0;
            }
            if (dataOffset < -length) {
                dataOffset = -length;
            }
            invalidate();
        }
        mLastX = x;
        LogUtil.d("ChartView", "moveData() --- distance = " + distance + " --- offset = " + dataOffset);
    }

    private void removeVelocityTracker() {
        if (mVTracker != null) {
            mVTracker.clear();
        }
    }

    private void initPoints() {
        int length = pointList.size();
        if (length <= 0) {
            return;
        }
        relPoints = new float[length];
        for (int i = 0; i < length; i++) {
            relPoints[i] = pointList.get(i);
        }
    }

    private class Control implements BaseView.OnTouchControl {


        public boolean onTouch(MotionEvent event) {
            addVelocityTracker(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    resetScroll();
                    break;

                case MotionEvent.ACTION_MOVE:
                    moveData(event.getX());
                    break;

                case MotionEvent.ACTION_UP:
                    removeVelocityTracker();
                    break;
            }
            return false;
        }
    }

}
