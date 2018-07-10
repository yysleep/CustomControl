package com.sleep.yy.customcontrol.weather;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/22.
 *
 * @author YySleep
 */

public class WeatherView extends BaseView {

    private final static String TAG = "WeatherView";

    private Paint mCloudPaint;
    private Point mCloudPoint;
    private Path mCloudPath;

    private Paint mSunRingPaint;
    private Paint mSunPaint;
    private Paint mSunRectPaint;
    private Point mSunPoint;
    private Path mSunPath;

    private float mRadius00;
    private float mRadius01;
    private float mRadius02;
    private float mRadius03;
    private float mRadius04;

    private float mDisSunRadius;
    private float mDisSunRing;
    private float mSweepAngle;
    private float mStartAngle;

    private float mSunRadius;

    private float mSunRect;
    private float mRectRotate;

    private float mShadowRadius;
    private Paint mShadowPaint;
    private AnimatorSet mAnimatorSet;

    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        mCloudPoint = new Point(500, 350);
        mSunPoint = new Point(450, 350);

        mSunPath = new Path();

        mCloudPath = new Path();

        mCloudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCloudPaint.setShader(new LinearGradient(mCloudPoint.x - 200, mCloudPoint.y + 100,
                mCloudPoint.x + 200, mCloudPoint.y,
                getResources().getColor(R.color.white), getResources().getColor(R.color.cloudGray),
                Shader.TileMode.CLAMP));


        mSunPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSunPaint.setColor(getResources().getColor(R.color.yellow));

        mSunRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSunRectPaint.setShader(new LinearGradient(mSunPoint.x - 250, mSunPoint.y - 250,
                mSunPoint.x + 250, mSunPoint.y + 250,
                getResources().getColor(R.color.sunStartYellow), getResources().getColor(R.color.sunEndYellow),
                Shader.TileMode.CLAMP));

        mSunRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSunRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mSunRingPaint.setColor(getResources().getColor(R.color.yellow));
        mSunRingPaint.setStyle(Paint.Style.STROKE);

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(getResources().getColor(R.color.gray));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 4 * MeasureSpec.getSize(heightMeasureSpec) / 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRect(canvas);
        drawDisappearedSun(canvas);
        drawRing(canvas);
        drawSun(canvas);
        drawShadow(canvas);
        drawCloud(canvas);
    }

    private void drawRect(Canvas canvas) {
        // draw rect
        if (mRectRotate == 0 && mSunRect == 0) {
            return;
        }
        if (mRectRotate < 0) {
            mRectRotate = 0;
        }
        if (mSunRect < 0) {
            mSunRect = 0;
        }
        canvas.save();
        canvas.rotate(mRectRotate + 45, mSunPoint.x, mSunPoint.y);
        canvas.drawRect(mSunPoint.x - mSunRect, mSunPoint.y - mSunRect, mSunPoint.x + mSunRect, mSunPoint.y + mSunRect, mSunRectPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(mRectRotate, mSunPoint.x, mSunPoint.y);
        canvas.drawRect(mSunPoint.x - mSunRect, mSunPoint.y - mSunRect, mSunPoint.x + mSunRect, mSunPoint.y + mSunRect, mSunRectPaint);
        canvas.restore();
    }

    private void drawDisappearedSun(Canvas canvas) {
        // draw disappeared sun
        LogUtil.d(TAG, "[drawDisappearedSun] mDisSunRadius = " + mDisSunRadius + " --- mDisSunRing = " + mDisSunRing);
        if (mDisSunRadius == 0 && mDisSunRing == 0) {
            return;
        }
        if (mDisSunRadius < 0) {
            mDisSunRadius = 0;
        }
        if (mDisSunRing < 0) {
            mDisSunRing = 0;
        }
        canvas.save();
        mSunPath.reset();
        mSunPath.setFillType(Path.FillType.EVEN_ODD);
        mSunPath.addCircle(mSunPoint.x, mSunPoint.y, mDisSunRadius, Path.Direction.CW);
        mSunPath.addCircle(mSunPoint.x, mSunPoint.y, mDisSunRing, Path.Direction.CW);
        canvas.drawPath(mSunPath, mSunPaint);
        canvas.restore();
    }

    private void drawRing(Canvas canvas) {
        // draw ring
        if (mStartAngle == 0 && mSweepAngle == 0) {
            return;
        }
        if (mStartAngle < 0) {
            mStartAngle = 0;
        }
        if (mSweepAngle < 0) {
            mSweepAngle = 0;
        }
        canvas.save();
        mSunPath.reset();
        mSunRingPaint.setStrokeWidth(30);
        canvas.drawArc(mSunPoint.x - 200, mSunPoint.y - 200, mSunPoint.x + 200, mSunPoint.y + 200, mStartAngle / 2, mSweepAngle / 2, false, mSunRingPaint);
        mSunRingPaint.setStrokeWidth(20);
        canvas.drawArc(mSunPoint.x - 150, mSunPoint.y - 150, mSunPoint.x + 150, mSunPoint.y + 150, -mStartAngle, -mSweepAngle, false, mSunRingPaint);
        mSunRingPaint.setStrokeWidth(10);
        canvas.drawArc(mSunPoint.x - 100, mSunPoint.y - 100, mSunPoint.x + 100, mSunPoint.y + 100, mStartAngle / 2, mSweepAngle / 2, false, mSunRingPaint);
        //canvas.drawArc(mSunPoint.x - 50, mSunPoint.y - 50, mSunPoint.x + 50, mSunPoint.y + 50, mStartAngle, mSweepAngle, false, mSunRingPaint);
        canvas.restore();
    }

    private void drawSun(Canvas canvas) {
        // draw sun
        if (mSunRadius == 0) {
            return;
        }
        if (mSunRadius < 0) {
            mSunRadius = 0;
        }

        canvas.save();
        mSunPath.reset();
        mSunPath.addCircle(mSunPoint.x, mSunPoint.y, mSunRadius, Path.Direction.CW);
        canvas.drawPath(mSunPath, mSunPaint);
        canvas.restore();
    }

    private void drawShadow(Canvas canvas) {
        // draw shadow
        if (mShadowRadius == 0) {
            return;
        }
        if (mShadowRadius < 0) {
            mShadowRadius = 0;
        }
        canvas.save();
        canvas.drawArc(mSunPoint.x - 100 - mShadowRadius, mSunPoint.y + 600, mSunPoint.x + 100 + mShadowRadius, mSunPoint.y + 635, 0, 360, true, mShadowPaint);
        canvas.restore();
    }

    private void drawCloud(Canvas canvas) {
        // draw cloud
        if (mRadius00 == 0 && mRadius01 == 0 && mRadius02 == 0 && mRadius03 == 0 && mRadius04 == 0) {
            return;
        }
        if (mRadius00 < 0) {
            mRadius00 = 0;
        }
        if (mRadius01 < 0) {
            mRadius01 = 0;
        }
        if (mRadius02 < 0) {
            mRadius02 = 0;
        }
        if (mRadius03 < 0) {
            mRadius03 = 0;
        }
        if (mRadius04 < 0) {
            mRadius04 = 0;
        }
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

    public void setDisSunRadius(float radius) {
        if (radius == 0) {
            mDisSunRing = 0;
        }
        mDisSunRadius = radius;
        invalidate();
    }

    public void setDisSunRing(float ring) {
        mDisSunRing = ring;
        invalidate();
    }

    public void setSunRadius(float radius) {
        mSunRadius = radius;
        invalidate();
    }

    public void setSunRect(float sunRect) {
        mSunRect = sunRect;
        invalidate();
    }

    public void setShadow(float shadow) {
        mShadowRadius = shadow;
        invalidate();
    }

    public void setRectRotate(float rotate) {
        mRectRotate = rotate;
        invalidate();
    }

    public void setSweepAngle(float sweepAngle) {

        /*if (sweepAngle > 180) {
            mSweepAngle = 360 - sweepAngle;
        } else {
            mSweepAngle = sweepAngle;
        }*/
        mSweepAngle = sweepAngle;
        mStartAngle = sweepAngle;
        invalidate();
    }

    public void setCloudRadius(float radius) {
        LogUtil.d(TAG, "[setCloudRadius] radius = " + radius);
        if (radius < 0) {
            mRadius00 = radius;
            mRadius01 = radius;
            mRadius02 = radius;
            mRadius03 = radius;
            mRadius04 = radius;
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
            mRadius03 = (radius - 60) * 3 > 60 ? 60 : (radius - 60) * 3;
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
        ObjectAnimator sunEnlarge = ObjectAnimator.ofFloat(this, "DisSunRadius", 250);
        sunEnlarge.setDuration(200);

        ObjectAnimator sunDisappear = ObjectAnimator.ofFloat(this, "disSunRing", 250);
        sunDisappear.setDuration(400);

        ObjectAnimator ring = ObjectAnimator.ofFloat(this, "sweepAngle", 0, 180, 0);
        ring.setDuration(500);

        ObjectAnimator sunEnlarge01 = ObjectAnimator.ofFloat(this, "sunRadius", 0, 250);
        sunEnlarge01.setDuration(200);

        ObjectAnimator sunRect = ObjectAnimator.ofFloat(this, "sunRect", 0, 240);
        //sunRect.setInterpolator(new LinearInterpolator());
        sunRect.setDuration(200);

        ObjectAnimator rectRotateAnimator = ObjectAnimator.ofFloat(this, "rectRotate", 360);
        rectRotateAnimator.setDuration(30000);
        rectRotateAnimator.setInterpolator(new LinearInterpolator());
        rectRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rectRotateAnimator.setRepeatMode(ValueAnimator.RESTART);

        ObjectAnimator shadow = ObjectAnimator.ofFloat(this, "shadow", 200);
        //sunRect.setInterpolator(new LinearInterpolator());
        shadow.setDuration(300);

        ObjectAnimator cloudAnimator = ObjectAnimator.ofFloat(this, "cloudRadius", 100);
        cloudAnimator.setDuration(800);


        mAnimatorSet = new AnimatorSet();
        //mAnimatorSet.play(sunEnlarge);
        mAnimatorSet.play(sunDisappear).with(sunEnlarge);
        mAnimatorSet.play(ring).after(sunDisappear);
        mAnimatorSet.play(sunEnlarge01).after(ring);
        mAnimatorSet.play(sunRect).after(sunEnlarge01);
        mAnimatorSet.play(shadow).with(sunRect);
        mAnimatorSet.play(rectRotateAnimator).after(shadow);
        mAnimatorSet.play(cloudAnimator).with(rectRotateAnimator);
        mAnimatorSet.start();
    }

    private void reset() {
        setCloudRadius(-1);
        setDisSunRadius(-1);
        setDisSunRing(-1);
        setSweepAngle(-1);
        setSunRadius(-1);
        setSunRect(-1);
        setRectRotate(-1);
        setShadow(-1);
    }
}
