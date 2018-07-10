package com.sleep.yy.customcontrol.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sleep.yy.customcontrol.base.BaseView;

import java.util.Random;

/**
 * Created by YySleep on 2018/7/10
 *
 * @author YySleep
 */

public class ChartView extends BaseView {

    private static final String TAG = "ChartView";
    private Paint mTextPaint;
    private Paint mAxisPathPaint;
    private Paint mDataPathPaint;
    private Path mAxisLinePath;
    private Path mDataLinePath;
    private Point mTextPoint;

    private int timeSize;
    private float textSize;
    private int textBlank;
    private int offset;

    public ChartView(Context context) {
        super(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        textBlank = 100;
        textSize = 24;
        offset = 16;

        setDefaultSize(800, 800);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);

        mAxisPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAxisPathPaint.setStyle(Paint.Style.STROKE);
        mAxisPathPaint.setStrokeWidth(4);

        mDataPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDataPathPaint.setStyle(Paint.Style.STROKE);
        mDataPathPaint.setStrokeWidth(4);

        mAxisLinePath = new Path();
        mDataLinePath = new Path();
        mTextPoint = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        reset();
        calculateTimeSize();
        drawMpa(canvas);
        drawAxisLine(canvas);
        drawTime(canvas);
        drawDataLine(canvas);
    }

    private void calculateTimeSize() {
        timeSize = (int) ("2018/1/18 23:15:30".length() * textSize / 2);
        timeSize = (int) Math.sqrt(timeSize * timeSize / 2) + 10;
    }

    private void drawAxisLine(Canvas canvas) {
        mAxisLinePath.moveTo(timeSize - offset, textSize);
        mAxisLinePath.lineTo(timeSize - offset, mHeight - timeSize - offset);
        mAxisLinePath.lineTo(textSize + (mWidth / textBlank - 1) * textBlank + 60, mHeight - timeSize - offset);
        canvas.drawPath(mAxisLinePath, mAxisPathPaint);
    }

    private void drawMpa(Canvas canvas) {
        mTextPoint.x = timeSize - offset;
        int leftHeight = mHeight - timeSize - offset;
        for (int i = 0; i < 7; i++) {
            mTextPoint.y = (int) (leftHeight - (leftHeight - textSize) / 6 * i);
            canvas.drawText(i * 200 + "Mpa", mTextPoint.x, mTextPoint.y, mTextPaint);
        }
    }

    private void drawTime(Canvas canvas) {
        mTextPoint.y = mHeight - timeSize;
        for (int i = 0; i < mWidth / textBlank - 1; i++) {
            mTextPoint.x = timeSize + i * textBlank;
            canvas.save();
            canvas.rotate(-45, mTextPoint.x, mTextPoint.y);
            canvas.drawText("2018/1/18 23:15:30", mTextPoint.x, mTextPoint.y, mTextPaint);
            canvas.restore();
        }
    }

    private void drawDataLine(Canvas canvas) {
        Random random = new Random();
        for (int i = 0; i < mWidth / textBlank - 1; i++) {
            mTextPoint.x = timeSize + i * textBlank - offset;
            if (i == 0) {
                mDataLinePath.moveTo(mTextPoint.x, random.nextInt(mHeight - timeSize - offset * 2) + offset);
            } else {
                mDataLinePath.lineTo(mTextPoint.x, random.nextInt(mHeight - timeSize - offset * 2) + offset);
            }
        }

        canvas.drawPath(mDataLinePath, mDataPathPaint);
    }

    private void reset() {
        mAxisLinePath.reset();
        mDataLinePath.reset();
    }
}
