package com.topnet.lib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


/**
 * 提示公共类
 *
 * @author yangxiaoyi
 */
public class ToastUtils {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;


    private static final int CANCEL_TOAST = 100;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CANCEL_TOAST:
                    cancelToast();
                    break;
            }
        }
    };

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void showToast(Context context, String s) {
        String main = "main";
        if (main.equals(Thread.currentThread().getName())) {
            // 判断是否是主线程,主线程的名称默认为main
            if (toast == null) {
                toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (s.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = s;
                    toast.setText(s);
                    toast.show();
                }
            }
            oneTime = twoTime;
        }
        mHandler.removeMessages(CANCEL_TOAST);
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(CANCEL_TOAST, 1500);
        }
    }


    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文
     * @param s       文本内容
     */
    public static void show(Context context, String s) {
        showToast(context, s);
    }


    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文对象
     * @param resId   显示内容资源ID
     */
    public static final void showToast(Context context, int resId) {
        show(context, context.getString(resId));
    }


    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context     上下文对象
     * @param formatResId 被格式化的字符串资源的ID
     * @param args        参数数组
     */
    public static final void toastL(Context context, int formatResId, Object... args) {
        Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context     上下文对象
     * @param formatResId 被格式化的字符串资源的ID
     * @param args        参数数组
     */
    public static final void toastS(Context context, int formatResId, Object... args) {
        Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context 上下文对象
     * @param format  被格式化的字符串
     * @param args    参数数组
     */
    public static final void toastL(Context context, String format, Object... args) {
        Toast.makeText(context, String.format(format, args), Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文对象
     * @param format  被格式化的字符串
     * @param args    参数数组
     */
    public static final void toastS(Context context, String format, Object... args) {
        Toast.makeText(context, String.format(format, args), Toast.LENGTH_SHORT).show();
    }


    /**
     * @param context 上下文
     * @param str     显示内容
     * @param time    显示时间
     */
    public static void show(Context context, String str, int time) {
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                Toast toast = Toast.makeText(context, str, time);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (Exception e) {
            }
        }
    }
}
