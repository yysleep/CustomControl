package com.sleep.yy.customcontrol.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.util.LogUtil;


/**
 * Created by YySleep on 2018/3/14.
 *
 * @author YySleep
 */

public class PieChartView extends View {

    private final static String TAG = "TestView";
    private Paint mPathPaint;
    private Paint mPaint00;
    private Paint mPaint01;
    private Paint mPaint02;
    private Paint mPaint03;
    private Paint mPaint04;
    private Paint mPaint05;

    private Path mPath;
    private int mWidth;
    private int mHeight;
    private int mQuadX;
    private int mRedSweepAngle = 140;
    private int mYellowStartAngle = 320;
    private int mYellowSweepAngle = 40;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPath = new Path();

        mPaint00 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint00.setColor(getResources().getColor(R.color.red));

        mPaint01 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint01.setColor(getResources().getColor(R.color.yellow));

        mPaint02 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint02.setColor(getResources().getColor(R.color.purple));

        mPaint03 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint03.setColor(getResources().getColor(R.color.gray));

        mPaint04 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint04.setColor(getResources().getColor(R.color.green));

        mPaint05 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint05.setColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            mWidth = widthSize - getPaddingStart() - getPaddingEnd();
        } else {
            mWidth = 150;
        }

        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            mHeight = heightSize - getPaddingTop() - getPaddingBottom();
        } else {
            mHeight = 150;
        }
        setMeasuredDimension(mWidth, mHeight);
        LogUtil.d(TAG, "[onMeasure] mWidth = " + mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawQuadPath(canvas);
        drawArc(canvas);

    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(0, 0, mWidth - 24, mHeight - 24, 180, mRedSweepAngle, true, mPaint00);

        canvas.drawArc(24, 24, mWidth, mHeight, mYellowStartAngle, mYellowSweepAngle, true, mPaint01);

        canvas.drawArc(24, 24, mWidth, mHeight, 1, 10, true, mPaint02);

        canvas.drawArc(24, 24, mWidth, mHeight, 12, 10, true, mPaint03);

        canvas.drawArc(24, 24, mWidth, mHeight, 23, 50, true, mPaint04);

        canvas.drawArc(24, 24, mWidth, mHeight, 74, 106, true, mPaint05);

        /*canvas.drawArc(0, 0, mWidth-24, mHeight-24, 180, 90, true, mPaint04);

        canvas.drawArc(24, 24, mWidth, mHeight, -90, 270, true, mPaint05);*/
    }

    // 绘制二阶贝塞尔曲线
    private void drawQuadPath(Canvas canvas) {
        mPath.moveTo(0, 500);
        mPath.quadTo(mWidth * mQuadX / 100, 20, mWidth, 600);
        canvas.drawPath(mPath, mPathPaint);
    }

    public void setQuadX(int x) {
        if (x < 0) {
            x = 0;
        } else if (x > 100) {
            x = 100;
        }
        this.mQuadX = x;
        LogUtil.d(TAG, "[setQuadX] X = " + mWidth * mQuadX / 100);
        mPath.reset();
        invalidate();
    }

    public void setRedProgress(int progress) {
        mRedSweepAngle = (100 - progress) * 140 / 100;
        mYellowStartAngle = 320 - 140 * progress / 200;
        mYellowSweepAngle = 40 + progress * 140 / 100;
        invalidate();
    }
}
