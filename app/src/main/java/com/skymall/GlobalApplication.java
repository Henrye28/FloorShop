package com.skymall;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * Created by dan on 17/10/24.
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        checkAppReplacingState();
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
        Fresco.initialize(this);
    }

    private void checkAppReplacingState() {
        if (getResources() == null) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    //判断是否安装目标应用
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }
}
