package shannon.android.com.myuitest;

import android.app.Application;

import shannon.android.com.myuitest.utis.AppUtil;

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
