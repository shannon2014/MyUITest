package com.shannon.myuitest.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shannon.myuitest.R;
import com.shannon.myuitest.reflection.ReflectionUtils;
import com.shannon.myuitest.reflection.TestEntity;
import com.shannon.myuitest.ujorm.Employee;
import com.shannon.myuitest.utis.DisplayUtil;
import com.shannon.myuitest.utis.LogUtil;
import com.shannon.myuitest.utis.SignUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by hegui on 2020/3/18.
 */

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_CAMERA_REQUESTCODE = 0x51;

    private static final String TEXT = "MangoTV";

    private ImageView mIV;
    private ImageView mIVWebp;
    private ImageView mIVFull;
    private TextView mTV;
    private TextView mTVWebp;
    private TextView mTVFull;
    private Camera camera;
    private TextView mTv1;

    private Camera.Parameters parameter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
//        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
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

        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv1.setOnClickListener(onClickListener);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = checkSelfPermission(Manifest.permission.CAMERA);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                camera = Camera.open();
                camera.startPreview();
            } else {
                requestCameraPermission();
            }
        }

        DisplayUtil.showScreenInfo();
        showImageInfo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.CAMERA)) {
            if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "你的手机没有闪光灯!", Toast.LENGTH_LONG).show();
            } else {
                camera = Camera.open();
            }
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUESTCODE);
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

    private void openFlash() {
        if (camera == null) {
            Toast.makeText(this, "camera == null", Toast.LENGTH_LONG).show();
            return;
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "你的手机没有闪光灯!", Toast.LENGTH_LONG).show();
            return;
        }
        //        camera.startPreview();
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameter);
    }

    private void testMD5(){
        LogUtil.d(TAG, "testMD5() start");
        long startTIme = System.currentTimeMillis();
        byte[] tempByte = SignUtils.encryptMD5(TEXT);
        String tempString = SignUtils.byte2hex(tempByte);
        LogUtil.d(TAG, "testMD5() end, time = " + (System.currentTimeMillis() - startTIme));
    }

    private void testSha1(){
        LogUtil.d(TAG, "testSha1() start");
        long startTIme = System.currentTimeMillis();
        byte[] tempByte = SignUtils.encryptSHA1(TEXT);
        String tempString = SignUtils.byte2hex(tempByte);
        LogUtil.d(TAG, "testSha1() end, time = " + (System.currentTimeMillis() - startTIme));
    }



    private void closeFlash() {
        if (camera == null) {
            Toast.makeText(this, "camera == null", Toast.LENGTH_LONG).show();
            return;
        }
        //        camera.stopPreview();
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);
    }

    private void testReflection(){
        ReflectionUtils.testGetClass();
        /*
         * String : "java.lang.String"
         * Employee : "com.shannon.myuitest.ujorm.Employee"
         */
        ReflectionUtils.testForName("com.shannon.myuitest.ujorm.Employee");
        TestEntity  testEntity = new TestEntity();
        testEntity.setIntValue(1);
        ReflectionUtils.getFieldObject("com.shannon.myuitest.reflection.TestEntity",testEntity,"intValue");
        Map hashMap = new WeakHashMap();
        hashMap.put("1",1);
        hashMap.put("2","2");
        hashMap.put("1",testEntity);
    }

    private void testEqual(){
        boolean result1 = equalUUID("","");
        boolean result2 = equalUUID("1234","");
        boolean result3 = equalUUID("","4321");
        boolean result4 = equalUUID("1234","1234");
        LogUtil.d(TAG, "testEqual() result1 = " + result1 + ", result2 = " + result2
        +  ", result3 = " + result3 +  ", result4 = " + result4);

    }

    private static boolean equalUUID(String id1, String id2) {
        if (TextUtils.equals(id1, id2)) {
            return true;
        }
        return false;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_png:
                    //                    testPngTime();
//                    openFlash();
                    testMD5();
                    break;
                case R.id.tv_webp:
                    //                    testWebpTime();
//                    closeFlash();
                    testSha1();
                    break;
                case R.id.tv_full:
                    testFullPngTime();
                    break;
                case R.id.tv1:
//                    testReflection();
                    testEqual();
                    break;
                default:
                    break;
            }
        }
    };
}

