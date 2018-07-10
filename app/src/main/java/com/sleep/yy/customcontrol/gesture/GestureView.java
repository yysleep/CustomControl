package com.sleep.yy.customcontrol.gesture;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/26.
 *
 * @author YySleep
 */

public class GestureView extends BaseView implements GestureDetector.OnGestureListener{

    private static final String TAG = "GestureView";

    public GestureView(Context context) {
        super(context);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "[onTouchEvent] " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        LogUtil.d(TAG, "[performClick] ");
        return super.performClick();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
