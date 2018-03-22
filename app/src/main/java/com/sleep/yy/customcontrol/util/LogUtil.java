package com.sleep.yy.customcontrol.util;

import android.util.Log;

/**
 * Created by YySleep on 2018/1/17.
 *
 * @author yysleep
 */

public class LogUtil {

    private static final String TAG = "CustomControl-";
    private static boolean DEBUG = true;

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(TAG + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(TAG + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(TAG + tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(TAG + tag, msg);
        }
    }

}
