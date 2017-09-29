package com.bigdata.gree.androidbluetooth.util;

import android.util.Log;

/**
 * Author:liuyanguo
 * Date:2017/9/29
 * Time:10:23
 * Description:自定义Log
 */

public class LygLog {

    //自定义Log的全局标志
    public static final String TAG = "LygLog";

    //调试模式的开关
    private static final boolean DEBUG_MODE = true;

    //调试模式的等级
    private static final int GRADE_ONE = 1;
    private static final int GRADE_TWO = 2;
    private static final int GRADE_THREE = 3;

    /**
     * Log的Debug模式
     *
     * @param message 需要打印出来的信息
     */
    public static void d(String message) {
        if (DEBUG_MODE) {
            Log.d(TAG, message);
        }
    }

    /**
     * Log的Debug模式
     *
     * @param tag     局部TAG
     * @param message 需要打印出来的信息
     */
    public static void d(String tag, String message) {
        if (DEBUG_MODE) {
            Log.d(tag, message);
        }
    }

    /**
     * Log的Warning模式
     *
     * @param message 需要打印出来的信息
     */
    public static void w(String message) {
        if (DEBUG_MODE) {
            Log.d(TAG, message);
        }
    }

    /**
     * Log的Warningss模式
     *
     * @param tag     局部TAG
     * @param message 需要打印出来的信息
     */
    public static void w(String tag, String message) {
        if (DEBUG_MODE) {
            Log.d(tag, message);
        }
    }

    /**
     * Log的Error模式
     *
     * @param message 需要打印出来的信息
     */
    public static void e(String message) {
        if (DEBUG_MODE) {
            Log.e(TAG, message);
        }
    }

    /**
     * Log的Error模式
     *
     * @param tag     局部TAG
     * @param message 需要打印出来的信息
     */
    public static void e(String tag, String message) {
        if (DEBUG_MODE) {
            Log.d(tag, message);
        }
    }
}
