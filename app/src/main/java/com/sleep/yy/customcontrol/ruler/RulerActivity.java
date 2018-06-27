package com.sleep.yy.customcontrol.ruler;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.base.BaseActivity;

/**
 * Created by YySleep on 2018/6/24.
 *
 * @author YySleep
 */

public class RulerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_ruler;
    }

    @Override
    protected String toolBarTitle() {
        return "RulerView";
    }
}
