package com.sleep.yy.customcontrol.main;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by YySleep on 2018/3/14.
 *
 * @author YySleep
 */

public class MainModel {

    public MainModel(String title, Class<? extends AppCompatActivity> activityClass) {
        this.title = title;
        this.activityClass = activityClass;
    }

    public String title;

    public Class<? extends AppCompatActivity> activityClass;
}
