package com.topnet.lib.base;

/**
 * Created by yangxiaoyi on 2018-10-9
 *
 * @author yangxiaoyi
 */
public interface BaseView {
    /**
     * 显示加载Dialog
     */
    void showProgess();
    /**
     * 隐藏加载Dialog
     */
    void hideProgess();

    /**
     * 显示提示信息
     * @param msg
     */
    void showMsg(String msg);

    /**
     * 做一些销毁处理
     */
    void destory();


}
