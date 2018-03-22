package com.sleep.yy.customcontrol.weather;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/22.
 *
 * @author YySleep
 */

public class WeatherView extends View {

    private final static String TAG = "WeatherView";

    private Paint mCloudPaint;
    private Point mCloudPoint;
    private Path mCloudPath;

    private Paint mSunRingPaint;
    private Paint mSunPaint;
    private Point mSunPoint;
    private Path mSunPath;

    private float mRadius00;
    private float mRadius01;
    private float mRadius02;
    private float mRadius03;
    private float mRadius04;

    private float mSunRadius;
    private float mSunRing;
    private float mSweepAngle;
    private float mStartAngle;
    private float mRingRotate;


    private AnimatorSet mAnimatorSet;

    public WeatherView(Context context) {
        this(context, null);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mCloudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCloudPoint = new Point(500, 300);
        mCloudPaint.setShader(new LinearGradient(mCloudPoint.x - 200, mCloudPoint.y + 100,
                mCloudPoint.x + 200, mCloudPoint.y,
                getResources().getColor(R.color.white), getResources().getColor(R.color.cloudGray),
                Shader.TileMode.CLAMP));


        mSunPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mSunPaint.setColor(getResources().getColor(R.color.yellow));
        mSunPoint = new Point(450, 300);
        mSunPath = new Path();
        mSunPath.setFillType(Path.FillType.EVEN_ODD);

        mSunRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSunRingPaint.setStrokeWidth(10);
        mSunRingPaint.setColor(getResources().getColor(R.color.yellow));
        mSunRingPaint.setStyle(Paint.Style.STROKE);

        mCloudPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 3 * MeasureSpec.getSize(widthMeasureSpec) / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw sun
        canvas.save();
        mSunPath.reset();
        mSunPath.addCircle(mSunPoint.x, mSunPoint.y, mSunRadius, Path.Direction.CW);
        mSunPath.addCircle(mSunPoint.x, mSunPoint.y, mSunRing, Path.Direction.CW);
        canvas.drawPath(mSunPath, mSunPaint);
        canvas.restore();

        canvas.save();
        mSunPath.reset();
        canvas.rotate(mRingRotate);
        canvas.drawArc(mSunPoint.x - 100, mSunPoint.y - 100, mSunPoint.x + 100, mSunPoint.y + 100, mStartAngle, mSweepAngle, false, mSunRingPaint);
        canvas.drawArc(mSunPoint.x - 50, mSunPoint.y - 50, mSunPoint.x + 50, mSunPoint.y + 50, mStartAngle, mSweepAngle, false, mSunRingPaint);
        canvas.restore();

        // draw cloud
        canvas.save();
        mCloudPath.reset();
        canvas.clipRect(mCloudPoint.x - 50 - 100, mCloudPoint.y - 30 - 100, mCloudPoint.x + 210 + 80, mCloudPoint.y + 100);
        mCloudPath.addCircle(mCloudPoint.x, mCloudPoint.y - 15, mRadius02, Path.Direction.CW);
        mCloudPath.addCircle(mCloudPoint.x + 100, mCloudPoint.y, mRadius03, Path.Direction.CW);

        mCloudPath.addCircle(mCloudPoint.x - 30, mCloudPoint.y + 80, mRadius00, Path.Direction.CW);
        mCloudPath.addCircle(mCloudPoint.x + 100, mCloudPoint.y + 80, mRadius01, Path.Direction.CW);
        mCloudPath.addCircle(mCloudPoint.x + 160, mCloudPoint.y + 80, mRadius04, Path.Direction.CW);
        canvas.drawPath(mCloudPath, mCloudPaint);
        canvas.restore();

    }

    public void setSunRadius(float ring) {
        mSunRadius = ring > 0 ? ring : 0;
        invalidate();
    }

    public void setSunRing(float radius) {
        mSunRing = radius > 0 ? radius : 0;
        if (radius == 0) {
            mSunRing = 0;
        }
        invalidate();
    }

    public void setRingRotate(float rotate) {
        mRingRotate = rotate > 0 ? rotate : 0;
        invalidate();
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle > 0 ? sweepAngle : 0;
        mStartAngle = mSweepAngle;
        invalidate();
    }

    public void setCloudRadius(float radius) {
        LogUtil.d(TAG, "[setCloudRadius] radius = " + radius);
        if (radius < 0) {
            mRadius00 = 0;
            mRadius01 = 0;
            mRadius02 = 0;
            mRadius03 = 0;
            mRadius04 = 0;
            invalidate();
            return;
        }
        mRadius00 = radius * 4 > 80 ? 80 : radius * 4;
        if (radius > 20) {
            mRadius01 = (radius - 20) * 4 > 80 ? 80 : (radius - 20) * 4;
        }
        if (radius > 40) {
            mRadius02 = (radius - 40) * 3.5 > 70 ? 70 : (radius - 40) * 3.5f;
        }
        if (radius > 60) {
            mRadius03 = (radius - 60) * 3.5 > 70 ? 70 : (radius - 60) * 3.5f;
        }
        if (radius > 80) {
            mRadius04 = (radius - 80) * 2.5 > 50 ? 50 : (radius - 80) * 2.5f;
        }
        invalidate();
    }

    public void startAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet.end();
            mAnimatorSet = null;
            reset();
        }
        ObjectAnimator cloudAnimator = ObjectAnimator.ofFloat(this, "cloudRadius", 100);
        cloudAnimator.setDuration(800);

        ObjectAnimator sunEnlarge = ObjectAnimator.ofFloat(this, "sunRadius", 250);
        sunEnlarge.setDuration(500);

        ObjectAnimator sunDisappear = ObjectAnimator.ofFloat(this, "sunRing", 250);
        sunDisappear.setDuration(500);

        ObjectAnimator ringRotate = ObjectAnimator.ofFloat(this, "ringRotate", 360);
        ringRotate.setDuration(500);

        ObjectAnimator sweepAngle = ObjectAnimator.ofFloat(this, "sweepAngle", 0, 270, 0);
        sweepAngle.setDuration(1000);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(sunDisappear).after(sunEnlarge);
        mAnimatorSet.play(sweepAngle).after(sunDisappear);
        //mAnimatorSet.play(ringRotate).with(sweepAngle);
        mAnimatorSet.play(cloudAnimator).after(sweepAngle);
        mAnimatorSet.start();
    }

    private void reset() {
        setCloudRadius(-1);
        setSunRadius(-1);
        setSunRing(-1);
        setSweepAngle(-1);
        setRingRotate(-1);
    }
}
