package com.sleep.yy.customcontrol.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.sleep.yy.customcontrol.R;


/**
 * Created by Administrator on 2018/2/11.
 *
 * @author yysleep
 */

public class DefaultItemDecoration extends RecyclerView.ItemDecoration {

    private int defaultHeight = 8;
    private Paint paint;

    public DefaultItemDecoration() {
        paint = new Paint();
        LinearGradient shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#FFFFFF"),
                Color.parseColor("#000000"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        for(int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            float left = view.getLeft();
            float top = view.getTop();
            float right = view.getRight();
            float bottom = view.getBottom();
            c.drawRect(left, bottom, right, bottom + defaultHeight, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, defaultHeight);
    }

}

