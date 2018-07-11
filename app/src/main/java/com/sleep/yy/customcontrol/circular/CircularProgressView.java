package com.sleep.yy.customcontrol.circular;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/8.
 *
 * @author YySleep
 */

public class CircularProgressView extends BaseView {
    private final static String TAG = "CircularProgressView";
    private final int PADDING = 4;

    private Paint mTestPaint;
    private Paint mProgressPaint;

    private int mProgress;
    private int mStartAngleProgress;
    private String mText;
    private int mTestSize;

    private AnimatorSet mAnimatorSet;
    private int mArcLeft = 0;
    private int mArcTop = 0;
    private int mArcRight = 0;
    private int mArcBottom = 0;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setDefaultSize(150, 150);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
        CharSequence c = typedArray.getText(R.styleable.CircularProgressView_text);
        if (c != null) {
            mText = c.toString();
        }
        LogUtil.d(TAG, "[init] c = " + c);
        mTestSize = typedArray.getDimensionPixelSize(R.styleable.CircularProgressView_textSize, 8);
        int textColor = typedArray.getColor(R.styleable.CircularProgressView_textColor, Color.BLACK);
        int progressColor = typedArray.getColor(R.styleable.CircularProgressView_color, Color.BLACK);
        mTestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTestPaint.setTextSize(mTestSize);
        mTestPaint.setColor(textColor);
        mTestPaint.setTextAlign(Paint.Align.CENTER);
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(PADDING);
        typedArray.recycle();
        startWaitingAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth > mHeight) {
            mArcLeft = PADDING + (mWidth - mHeight) / 2;
            mArcTop = PADDING;
            mArcRight = mHeight - PADDING + (mWidth - mHeight) / 2;
            mArcBottom = mHeight - PADDING;
        } else {
            mArcLeft = PADDING;
            mArcTop = PADDING + (mHeight - mWidth) / 2;
            mArcRight = mWidth - PADDING;
            mArcBottom = mWidth - PADDING + (mHeight - mWidth) / 2;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTest(canvas);
        drawArc(canvas);
    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(mArcLeft, mArcTop, mArcRight, mArcBottom, mStartAngleProgress * 360 / 100 - 90, mProgress * 360 / 100, false, mProgressPaint);
    }

    private void drawTest(Canvas canvas) {
        if (mText != null) {
            canvas.drawText(mText, mWidth / 2, (mHeight / 2) + mTestSize / 2, mTestPaint);
        }
    }

    public void setProgress(int progress) {
        LogUtil.d(TAG, "[setProgress] mAnimatorSet = " + mAnimatorSet);
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet.end();
            mAnimatorSet = null;
        }
        mProgress = progress;
        mStartAngleProgress = 0;
        mText = progress + "%";
        invalidate();
    }

    /**
     * 该 API 用作等待动画，不可以删除
     */
    public void setWaitProgress(int progress) {
        mProgress = progress;
        mStartAngleProgress = 0;
        mText = null;
        invalidate();
    }

    public void setTestSize(float size) {
        mTestPaint.setTextSize(size);
    }

    public void startWaitingAnimation() {
        if (mAnimatorSet != null) {
            return;
        }
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtil.d(TAG, "[onAnimationEnd]");
                setRotation(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                LogUtil.d(TAG, "[onAnimationCancel]");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.setRepeatMode(ValueAnimator.RESTART);

        ObjectAnimator animator = ObjectAnimator.ofInt(this, "waitProgress", 5, 75);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(rotationAnimator, animator);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

}
