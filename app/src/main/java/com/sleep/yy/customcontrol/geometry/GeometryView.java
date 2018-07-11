package com.sleep.yy.customcontrol.geometry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

public class GeometryView extends BaseView {
    private final static String TAG = "GeometryView";

    private Paint mPaint;
    private Path mPath01;
    private Path mPath02;
    private Bitmap mBitmap;
    private Point mPoint;
    private int mBmpWidth;
    private int mBmpHeight;

    public GeometryView(Context context) {
        this(context, null);
    }

    public GeometryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GeometryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public GeometryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    protected void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath01 = new Path();
        mPath02 = new Path();
        mPoint = new Point(200, 200);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        LogUtil.d(TAG, "[init] mBitmap = " + mBitmap);
        if (mBitmap == null)
            return;

        mBmpWidth = mBitmap.getWidth();
        mBmpHeight = mBitmap.getHeight();
        mPath01.addCircle(mPoint.x + mBmpWidth - 150, mPoint.y + mBmpHeight - 100, 100, Path.Direction.CW);

        mPath02.setFillType(Path.FillType.INVERSE_WINDING);
        mPath02.addCircle(mPoint.x + mBmpWidth + 150, mPoint.y + mBmpHeight - 100, 100, Path.Direction.CW);

        setDefaultSize(500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null)
            return;

        canvas.save();
        canvas.clipPath(mPath01);
        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y, mPaint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(mPath02);
        canvas.drawBitmap(mBitmap, mPoint.x + 300, mPoint.y, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(180, mPoint.x + mBmpWidth / 2, mPoint.y + mBmpHeight + 100 + mBmpHeight / 2);
        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y + mBmpHeight + 100, mPaint);
        canvas.restore();

        canvas.save();
        canvas.scale(0.5f, 0.8f, mPoint.x + 300 + mBmpWidth / 2, mPoint.y + mBmpHeight + 100 + mBmpHeight / 2);
        canvas.drawBitmap(mBitmap, mPoint.x + 300, mPoint.y + mBmpHeight + 100, mPaint);
        canvas.restore();

        canvas.save();
        canvas.skew(0.3f, 0.3f);
        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y + 2 * mBmpHeight + 200, mPaint);
        canvas.restore();
    }
}
