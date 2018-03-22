package com.sleep.yy.customcontrol.circular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/3/14.
 * @author YySleep
 */

public class CircularProgressActivity extends BaseActivity implements View.OnClickListener{

    private CircularProgressView mCpv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_circular_progress;
    }

    @Override
    protected String toolBarTitle() {
        return "CircularProgressView";
    }

    private void initView() {
        mCpv = findViewById(R.id.circular_progress_cpv);
        SeekBar progressSeekBar = findViewById(R.id.circular_progress_seekbar);
        Button waitBtn = findViewById(R.id.circular_progress_wait_animation_btn);
        waitBtn.setOnClickListener(this);
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mCpv.setProgress(progress);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circular_progress_wait_animation_btn:
                if (mCpv == null) {
                    return;
                }
                mCpv.startWaitingAnimation();
                break;
        }
    }
}
