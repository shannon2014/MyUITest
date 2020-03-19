package shannon.android.com.myuitest.utis;

import android.util.DisplayMetrics;

/**
 * Created by hegui on 2020/3/18.
 */

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    public static final int getScreenHeight() {
        int height = 0;
        return height;
    }

    public static final int getScreenWidth() {
        int width = 0;
        return width;
    }

    public static void showScreenInfo() {
        DisplayMetrics metrics = AppUtil.getContext().getResources().getDisplayMetrics();
        // 屏幕的逻辑密度，是密度无关像素（dip）的缩放因子，160dpi是系统屏幕显示的基线，1dip = 1px， 所以，在160dpi的屏幕上，density = 1， 而在一个120dpi屏幕上 density = 0.75。
        float density = metrics.density;

        //  屏幕的绝对宽度（像素）
        int screenWidth = metrics.widthPixels;

        // 屏幕的绝对高度（像素）
        int screenHeight = metrics.heightPixels;

        //  屏幕上字体显示的缩放因子，一般与density值相同，除非在程序运行中，用户根据喜好调整了显示字体的大小时，会有微小的增加。
        float scaledDensity = metrics.scaledDensity;

        // X轴方向上屏幕每英寸的物理像素数。
        float xdpi = metrics.xdpi;

        // Y轴方向上屏幕每英寸的物理像素数。
        float ydpi = metrics.ydpi;

        // 每英寸的像素点数，屏幕密度的另一种表示。densityDpi = density * 160.
        float desityDpi = metrics.densityDpi;

        LogUtil.d(TAG,"showScreenInfo() density = " + density + ", desityDpi = " + desityDpi);
    }
}
