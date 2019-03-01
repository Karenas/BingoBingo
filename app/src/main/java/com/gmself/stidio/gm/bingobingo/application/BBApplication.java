package com.gmself.stidio.gm.bingobingo.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by guomeng on 2/28.
 */

public class BBApplication extends Application {

    private final boolean isRunDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();

        initARouter();
    }


    private void initARouter(){
        if (isRunDebug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
