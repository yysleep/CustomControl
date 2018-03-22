package com.sleep.yy.customcontrol.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/21.
 * @author YySleep
 */

public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        final CameraView cv = findViewById(R.id.camera_v);
        SeekBar seekBar = findViewById(R.id.camera_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cv.setRotateX(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected String toolBarTitle() {
        return "CameraView";
    }
}
