package com.sleep.yy.customcontrol.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.util.LogUtil;

/**
 * Created by YySleep on 2018/3/22.
 *
 * @author YySleep
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initToolbar();
    }

    @LayoutRes
    protected abstract int layoutId();

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            LogUtil.e(TAG, "[initToolbar] you should include toolbar in your activity xml");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 展示 back 箭头
            actionBar.setDisplayHomeAsUpEnabled(backIconState());
            // 不展示 原本的 title
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle(toolBarTitle());
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract String toolBarTitle();

    protected boolean backIconState() {
        return true;
    }

}
