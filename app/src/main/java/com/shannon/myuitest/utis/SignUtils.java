package com.shannon.myuitest.utis;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by liushu on 2017/11/29.
 */

public class SignUtils {
    public static String signRequest(Map<String, Object> params, String secret) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);

        for (String key : keys) {
            Object value = params.get(key);
            if (value != null) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5加密
        byte[] bytes;
        query.append(secret);
        bytes = encryptMD5(query.toString());

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static String signStringRequest(Map<String, String> params, String secret) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            Object value = params.get(key);
            if (value != null) {
                query.append(key).append(value);
            }
        }
        // 第三步：使用MD5加密
        byte[] bytes;
        query.append(secret);
        bytes = encryptMD5(query.toString());
        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static byte[] encryptMD5(String data) {
        byte[] md5Bytes = null;
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = data.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            md5Bytes = md.digest(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Bytes;
    }

    public static byte[] encryptSHA1(String data)  {
        byte[] md5Bytes = null;
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 2 将消息变成byte数组
            byte[] input = data.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            md5Bytes = md.digest(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Bytes;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
