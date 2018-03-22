package com.sleep.yy.customcontrol.pie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/14.
 * @author YySleep
 */

public class PieChartActivity extends BaseActivity{

    private SeekBar mSeekBar;
    private PieChartView mPieChartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_pie;
    }

    @Override
    protected String toolBarTitle() {
        return "PieChartView";
    }

    private void initView() {
        mSeekBar = findViewById(R.id.pie_red_sb);
        mPieChartView = findViewById(R.id.pie_v);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPieChartView.setRedProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
