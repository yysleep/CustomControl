package com.sleep.yy.customcontrol.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/26.
 * @author YySleep
 */

public class GestureActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_gesture;
    }

    @Override
    protected String toolBarTitle() {
        return "GestureView";
    }
}
