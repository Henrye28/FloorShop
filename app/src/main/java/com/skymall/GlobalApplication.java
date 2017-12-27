package com.skymall;

import android.app.Application;

//import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);
        checkAppReplacingState();
        Bmob.initialize(this, "ee80fab0407209723c93996bff00b101");
        Fresco.initialize(this);
    }

    private void checkAppReplacingState() {
        if (getResources() == null) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
