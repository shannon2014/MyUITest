package com.shannon.myuitest;

import android.app.Application;

import com.shannon.myuitest.utis.AppUtil;

/**
 * Created by hegui on 2020/3/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
