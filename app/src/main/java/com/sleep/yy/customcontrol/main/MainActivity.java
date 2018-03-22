package com.sleep.yy.customcontrol.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sleep.yy.customcontrol.base.BaseActivity;
import com.sleep.yy.customcontrol.circular.CircularProgressActivity;
import com.sleep.yy.customcontrol.R;
import com.sleep.yy.customcontrol.geometry.GeometryActivity;
import com.sleep.yy.customcontrol.camera.CameraActivity;
import com.sleep.yy.customcontrol.pie.PieChartActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MainRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String toolBarTitle() {
        return "CustomControl";
    }

    @Override
    protected boolean backIconState() {
        return false;
    }

    private void init() {
        mAdapter = new MainRecyclerViewAdapter();
        mAdapter.list.add(new MainModel("CircularProgressView", CircularProgressActivity.class));
        mAdapter.list.add(new MainModel("PieChartView", PieChartActivity.class));
        mAdapter.list.add(new MainModel("GeometryView", GeometryActivity.class));
        mAdapter.list.add(new MainModel("CameraView", CameraActivity.class));
        mAdapter.setOnItemClickListener(new MainRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mAdapter.list.size() < position) {
                    return;
                }
                startActivity(new Intent(MainActivity.this, mAdapter.list.get(position).activityClass));
            }
        });

    }

    private void initView() {
        RecyclerView rv = findViewById(R.id.main_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DefaultDividerItemDecoration(this, DefaultDividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

}