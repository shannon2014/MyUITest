package com.shannon.myuitest.reflection;

import com.shannon.myuitest.utis.LogUtil;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

/**
 * https://blog.csdn.net/huangliniqng/article/details/88554510
 */
public class ReflectionUtils {
    private static final String TAG = ReflectionUtils.class.getSimpleName();

    public static void testGetClass() {

        String name = "testGetClass";
        Class c = name.getClass();
        LogUtil.d(TAG, "testGetClass() c = " + c.getName());
    }

    public static void testForName(String className) {
        Class c = Boolean.TYPE;
        try {
            c = Class.forName(className);
            LogUtil.d(TAG, "testForName() c = " + c.getName());
        } catch (Exception e) {

        }
    }

    public static Object getFieldObject(String className, Object obj, String filedName) {
        try {
            Class obj_class = Class.forName(className);
            return getFieldObject(obj_class, obj, filedName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(Class clazz, Object obj, String filedName) {
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            Object value = field.get(obj);
            LogUtil.d(TAG, "testForName() c = " + value.toString());
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
