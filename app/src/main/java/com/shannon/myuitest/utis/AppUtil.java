package com.shannon.myuitest.utis;

import android.content.Context;

/**
 * Created by hegui on 2020/3/18.
 */

public class AppUtil {
    private static Context mApplicationContext;


    public static void init(Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    public static Context getContext() {
        return mApplicationContext;
    }
}
