package com.sleep.yy.customcontrol.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/6/24.
 *
 * @author YySleep
 */

public class ChartActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_chart;
    }

    @Override
    protected String toolBarTitle() {
        return "ChartView";
    }
}
