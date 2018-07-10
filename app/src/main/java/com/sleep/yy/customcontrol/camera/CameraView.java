package com.sleep.yy.customcontrol.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseView;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/21.
 *
 * @author YySleep
 */

public class CameraView extends BaseView {
    private final static String TAG = "GeometryView";

    private Paint mPaint;
    private Bitmap mBitmap;
    private Point mPoint;
    private int mBmpWidth;
    private int mBmpHeight;
    private Camera mCamera;
    private int mRotateX;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        mCamera = new Camera();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPoint = new Point(200, 200);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        LogUtil.d(TAG, "[init] mBitmap = " + mBitmap);
        if (mBitmap == null)
            return;

        mBmpWidth = mBitmap.getWidth();
        mBmpHeight = mBitmap.getHeight();

        setDefaultSize(mBmpWidth * 2, mBmpHeight * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null)
            return;

        canvas.save();
        canvas.clipRect(mPoint.x, mPoint.y, mPoint.x + mBmpWidth, mPoint.y + mBmpHeight / 2);
        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y, mPaint);
        canvas.restore();


        canvas.save();
        if (mRotateX > 90) {
            canvas.clipRect(mPoint.x, mPoint.y, mPoint.x + mBmpWidth, mPoint.y + mBmpHeight / 2);

        } else {
            canvas.clipRect(mPoint.x, mPoint.y + mBmpHeight / 2, mPoint.x + mBmpWidth, mPoint.y + mBmpHeight);
        }
        mCamera.save();
        mCamera.rotateX(mRotateX);
        canvas.translate(mPoint.x + mBmpWidth / 2, mPoint.y + mBmpHeight / 2);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-(mPoint.x + mBmpWidth / 2), -(mPoint.y + mBmpHeight / 2));
        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y, mPaint);
        mCamera.restore();
        canvas.restore();

        canvas.save();
        mCamera.save();
        mCamera.rotateX(mRotateX);
        canvas.translate(mPoint.x + mBmpWidth / 2 + mBmpWidth + 100, mPoint.y + mBmpHeight / 2);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-(mPoint.x + mBmpWidth / 2 + mBmpWidth + 100), -(mPoint.y + mBmpHeight / 2));
        canvas.drawBitmap(mBitmap, mPoint.x + mBmpWidth + 100, mPoint.y, mPaint);
        mCamera.restore();
        canvas.restore();
    }

    public void setRotateX(int rotateX) {
        if (rotateX > 90)
            rotateX = 90;
        mRotateX = rotateX * 2;
        invalidate();
    }
}
