package com.yang.common.lib;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author yangxiaoyi
 * @date 2019/1/18
 * @describe com.yang.common.lib
 */
public class MyApp extends Application {
    private boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
