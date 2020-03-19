package shannon.android.com.myuitest.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import shannon.android.com.myuitest.R;
import shannon.android.com.myuitest.utis.DisplayUtil;
import shannon.android.com.myuitest.utis.LogUtil;

/**
 * Created by hegui on 2020/3/18.
 */

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        init();
    }

    private void initUI() {
        mIV = (ImageView) findViewById(R.id.img);
    }

    private void init() {
        DisplayUtil.showScreenInfo();

        showImageInfo();
    }

    private void showImageInfo() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIV.getDrawable();
        if (null != bitmapDrawable) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (null != bitmap) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int byteCount = bitmap.getByteCount();
                LogUtil.d(TAG, "showImageInfo() width = " + width + ", height = " + height
                        + ", byteCount = " + byteCount);
            }
        }
    }
}

