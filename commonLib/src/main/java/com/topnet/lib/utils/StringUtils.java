package com.topnet.lib.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YXY
 */

public class StringUtils {


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {

        String eNull = "null";
        if (str == null || "".equals(str) || eNull.equals(str)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断TextView 或者 EditText 是否为空  true 空
     */
    public static boolean checkTextIsEmpty(View view) {

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView != null && textView.getText().toString() != null && !"".equals(textView.getText().toString())) {
                return false;
            } else {
                return true;
            }

        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            if (editText != null && editText.getText().toString() != null && !"".equals(editText.getText().toString())) {
                return false;
            } else {
                return true;
            }

        }


        return true;
    }


    public static String genSignUrlPath(String serviceUrl, String password, String api) {
        long now = System.currentTimeMillis();
        String paramStr = "api=" + api + "&time=" + now + "&version=1";
        String urlPath = serviceUrl + "?" + paramStr + "&sign=" + getMd5Strig(paramStr + password);
        return urlPath;
    }

    public static String getMd5Strig(String str) {
//字符串容器
        StringBuilder sb = new StringBuilder();
        try {
            //获取md5加密器.public static MessageDigest getInstance(String algorithm)返回实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //把要加密的字符串转换成字节数组
            byte[] bytes = str.getBytes();
            //使用指定的 【byte 数组】对摘要进行最后更新，然后完成摘要计算。即完成md5的加密
            byte[] digest = md.digest(bytes);
            for (byte b : digest) {
                //把每个字节转换成16进制数
                //只保留后两位数
                int d = b & 0xff;
                //把int类型数据转为16进制字符串表示
                String herString = Integer.toHexString(d);
                //如果只有一位，则在前面补0.让其也是两位
                //字节高4位为0
                if (herString.length() == 1) {

                    //拼接字符串，拼成两位表示
                    herString = "0" + herString;
                }
                sb.append(herString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private static String fgf="&";
    /**
     * 将url中的参数转换成Map
     *
     * @return
     */
    public static Map paseURL(String url) {

        Map<String, String> map = new HashMap<>(10);
        int index = url.indexOf("?");
        if (index > -1 && index + 1 < url.length()) {
            url = url.substring(index + 1);
        }

        if (url.indexOf(fgf) > -1) {
            String[] arr = url.split(fgf);
            for (int i = 0; i < arr.length; i++) {
                String s1 = arr[i];
                String[] ar = s1.split("=");
                if (ar.length == 2) {
                    map.put(ar[0], ar[1]);
                    System.out.println(ar[0] + "----" + ar[1]);
                }
            }
        }

        System.out.println(url);
        return map;

    }

    /**
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static File getFilePath(String filePath,
                                   String fileName) {
        File file = null;
        LogUtils.d("==="+filePath+"----"+fileName);
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.d(e.toString());
        }
        return file;
    }

    /**
     *
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            LogUtils.d("makeRootDirectory"+e.toString());
        }
    }


}
