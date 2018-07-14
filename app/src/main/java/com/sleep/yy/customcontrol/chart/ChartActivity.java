package com.sleep.yy.customcontrol.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        List<ChartData> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            list.add(new ChartData(SimpleDateFormat.getTimeInstance().format(new Date()), random.nextInt(1200)));
        }
        ChartView chartView = findViewById(R.id.chart_v);
        chartView.setDataList(list);
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
