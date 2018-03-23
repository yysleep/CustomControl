package com.sleep.yy.customcontrol.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/22.
 * @author YySleep
 */

public class WeatherActivity extends BaseActivity{
    private final static String TAG = "WeatherActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        final WeatherView wv = findViewById(R.id.weather_v);
        Button btn = findViewById(R.id.weather_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.startAnimation();
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_weather;
    }

    @Override
    protected String toolBarTitle() {
        return "WeatherView";
    }
}
