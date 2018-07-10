package com.sleep.yy.customcontrol.util;

import android.view.View;

/**
 * Created by YySleep on 2018/7/10
 *
 * @author YySleep
 */

public class MeasureUtil {

    public static int measureSize(int spec, int defaultSize) {
        int size = View.MeasureSpec.getSize(spec);
        if (View.MeasureSpec.getMode(spec) != View.MeasureSpec.EXACTLY) {
            if (size > defaultSize) {
                size = defaultSize;
            }
        }
        return size;
    }
}
