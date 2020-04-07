package com.shannon.myuitest.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shannon.myuitest.R;
import com.shannon.myuitest.utis.DisplayUtil;
import com.shannon.myuitest.utis.LogUtil;

/**
 * Created by hegui on 2020/3/18.
 */

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mIV;
    private ImageView mIVWebp;
    private ImageView mIVFull;
    private TextView mTV;
    private TextView mTVWebp;
    private TextView mTVFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        init();
    }

    private void initUI() {
        mIV = (ImageView) findViewById(R.id.iv_png);
        mTV = (TextView) findViewById(R.id.tv_png);
        mTV.setOnClickListener(onClickListener);

        mIVWebp = (ImageView) findViewById(R.id.iv_webp);
        mTVWebp = (TextView) findViewById(R.id.tv_webp);
        mTVWebp.setOnClickListener(onClickListener);

        mIVFull = (ImageView) findViewById(R.id.iv_full);
        mTVFull = (TextView) findViewById(R.id.tv_full);
        mTVFull.setOnClickListener(onClickListener);
    }

    private void init() {
        DisplayUtil.showScreenInfo();
        showImageInfo();
    }

    private void testPngTime() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            mIV.setImageDrawable(getResources().getDrawable(R.drawable.img_png));
            mIV.setImageDrawable(null);
        }
        long end = System.currentTimeMillis();
        LogUtil.d(TAG, "testPngTime() time = " + (end - start));
    }

    private void testWebpTime() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            mIVWebp.setImageDrawable(getResources().getDrawable(R.drawable.img_webp));
            mIVWebp.setImageDrawable(null);
        }
        long end = System.currentTimeMillis();
        LogUtil.d(TAG, "testWebpTime() time = " + (end - start));
    }

    private void testFullPngTime() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            mIVFull.setImageDrawable(getResources().getDrawable(R.drawable.img_400));
            mIVFull.setImageDrawable(null);
        }
        long end = System.currentTimeMillis();
        LogUtil.d(TAG, "testFullPngTime() time = " + (end - start));
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_png:
                    testPngTime();
                    break;
                case R.id.tv_webp:
                    testWebpTime();
                    break;
                case R.id.tv_full:
                    testFullPngTime();
                    break;
                default:
                    break;
            }
        }
    };
}

