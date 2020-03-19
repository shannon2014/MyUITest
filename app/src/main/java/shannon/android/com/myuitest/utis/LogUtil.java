/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package shannon.android.com.myuitest.utis;

import android.util.Log;

public class LogUtil {

    private static final String DEFAULT_TAG = "HG_";
    public static boolean isFormat = true;

    @Deprecated
    public static final String TAG = "tag";

    private static int LOG_LEVEL = Log.VERBOSE;

    public static void setLogLevel(int logLevel) {
        LOG_LEVEL = logLevel;
    }

    public static void dumpException(Throwable t) {
        if (isLoggable(Log.WARN)) {
            final int maxLen = 256;
            StringBuilder err = new StringBuilder(maxLen);

            err.append("Got exception: ");
            err.append(t.toString());
            err.append("\n");

            System.out.println(err.toString());
            t.printStackTrace(System.out);
        }
    }

    public static void v(String aTag, String aMsg) {
        log(Log.VERBOSE, aTag, aMsg, null);
    }

    public static void v(String aTag, String aMsg, Throwable aThrowable) {
        log(Log.VERBOSE, aTag, aMsg, aThrowable);
    }

    public static void d(String aTag, String aMsg) {
        log(Log.DEBUG, aTag, aMsg, null);
    }

    public static void d(String aTag, String aMsg, Throwable aThrowable) {
        log(Log.DEBUG, aTag, aMsg, aThrowable);
    }

    public static void i(String aTag, String aMsg) {
        log(Log.INFO, aTag, aMsg, null);
    }

    public static void i(String aTag, String aMsg, Throwable aThrowable) {
        log(Log.INFO, aTag, aMsg, aThrowable);
    }

    public static void w(String aTag, String aMsg) {
        log(Log.WARN, aTag, aMsg, null);
    }

    public static void w(String aTag, String aMsg, Throwable aThrowable) {
        log(Log.WARN, aTag, aMsg, aThrowable);
    }

    public static void e(String aTag, String aMsg) {
        log(Log.ERROR, aTag, aMsg, null);
    }

    public static void e(String aTag, String aMsg, Throwable aThrowable) {
        log(Log.ERROR, aTag, aMsg, aThrowable);
    }

    @Deprecated
    public static void log(int aLogLevel, String aTag, String aMessage) {
        log(aLogLevel, aTag, aMessage, null);
    }

    /**
     * log Send a logLevel log message and log the exception, then collect the log entry.
     *
     * @param aLogLevel  Used to identify log level/
     * @param aTag       Used to identify the source of a log message. It usually identifies the class or activity
     *                   where the
     *                   log call occurs.
     * @param aMessage   The message you would like logged.
     * @param aThrowable An exception to log
     */
    @Deprecated
    public static void log(int aLogLevel, String aTag, String aMessage, Throwable aThrowable) {
        if (!isLoggable(aLogLevel)) {
            return;
        }
        if (isFormat) {
            aMessage = formatLogMsg(aMessage, 3);
        }
        if (aMessage == null || aMessage.length() < 4000) {
            printLog(aLogLevel, aTag, aMessage, aThrowable);
        } else if (aMessage.length() < 8000) {
            //            长度大于4k小于8k，分两次打印
            String preStr = aMessage.substring(0, aMessage.length() / 2);
            String afterStr = aMessage.substring(aMessage.length() / 2);
            printLog(aLogLevel, aTag, preStr, null);
            printLog(aLogLevel, aTag, afterStr, aThrowable);
        } else {
            int subLen = aMessage.length() / 3;
            String preStr = aMessage.substring(0, subLen);
            String midStr = aMessage.substring(subLen, subLen * 2);
            String afterStr = aMessage.substring(subLen * 2);
            printLog(aLogLevel, aTag, preStr, null);
            printLog(aLogLevel, aTag, midStr, null);
            printLog(aLogLevel, aTag, afterStr, aThrowable);
        }

    }

    private static void printLog(int aLogLevel, String aTag, String aMessage, Throwable aThrowable) {
        switch (aLogLevel) {
            case Log.VERBOSE:
                Log.v(DEFAULT_TAG + aTag, aMessage, aThrowable);
                break;
            case Log.DEBUG:
                Log.d(DEFAULT_TAG + aTag, aMessage, aThrowable);
                break;
            case Log.INFO:
                Log.i(DEFAULT_TAG + aTag, aMessage, aThrowable);
                break;
            case Log.WARN:
                Log.w(DEFAULT_TAG + aTag, aMessage, aThrowable);
                break;
            case Log.ERROR:
                Log.e(DEFAULT_TAG + aTag, aMessage, aThrowable);
                break;
            default:
                Log.e(DEFAULT_TAG + aTag, aMessage, aThrowable);
        }
    }
    /**
     * call when enter the method body that you want to debug with only one line
     */
    public static void method() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (null == stack || 2 > stack.length) {
            return;
        }
        StackTraceElement s = stack[1];
        if (null != s) {
            String className = s.getClassName();
            String methodName = s.getMethodName();
            d(className, "+++++" + methodName);
        }
    }

    /**
     * call when enter the method body that you want to debug.
     */
    public static void enter() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (null == stack || 2 > stack.length) {
            return;
        }

        StackTraceElement s = stack[1];
        if (null != s) {
            String className = s.getClassName();
            String methodName = s.getMethodName();
            d(className, "====>" + methodName);
        }
    }

    /**
     * call when leave the method body that you want to debug.
     */
    public static void leave() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (null == stack || 2 > stack.length) {
            return;
        }

        StackTraceElement s = stack[1];
        if (null != s) {
            String className = s.getClassName();
            String methodName = s.getMethodName();
            d(className, "<====" + methodName);
        }
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     *
     * @param aLevel The level to check.
     *
     * @return Whether or not that this is allowed to be logged.
     */
    public static boolean isLoggable(int aLevel) {
        return aLevel >= LOG_LEVEL;
    }

    /**
     * 格式化日志消息
     *
     * @param msg
     *
     * @return
     */
    private static String formatLogMsg(String msg, int index) {
        // log格式化start
        StackTraceElement[] stackTraces = (new Throwable()).getStackTrace();
        String fileName = "null";
        String methodName = "null";
        int lineNumber = 0;
        if (stackTraces.length > index) {
            StackTraceElement stackTrace = stackTraces[index];
            fileName = stackTrace.getFileName();
            methodName = stackTrace.getMethodName();
            lineNumber = stackTrace.getLineNumber();
        }
        if (fileName != null) {
            fileName = fileName.replace(".java", "");
        }
//        return String.format("[%s.%s():%d]%s", fileName, methodName, lineNumber, msg);
        return new StringBuilder("[").append(fileName).append(".")
                .append(methodName).append("():").append(lineNumber).append("]").append(msg).toString();
    }

    public static void setFormatOpen(boolean isOpen) {
        isFormat = isOpen;
    }

    public static boolean isFormatOpen() {
        return isFormat;
    }
}
