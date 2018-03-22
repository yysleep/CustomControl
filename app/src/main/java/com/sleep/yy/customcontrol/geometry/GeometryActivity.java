package com.sleep.yy.customcontrol.geometry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/21.
 * @author YySleep
 */

public class GeometryActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_geometry;
    }

    @Override
    protected String toolBarTitle() {
        return "GeometryView";
    }
}
