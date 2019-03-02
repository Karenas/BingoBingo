package com.gmself.stidio.gm.bingobingo.application;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gmself.studio.mg.basemodule.log_tool.Logger;

/**
 * Created by guomeng on 2/28.
 */

public class BBApplication extends Application {

    private final boolean isRunDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();

        initARouter();
        initLogger();
    }


    private void initARouter(){
        if (isRunDebug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    private void initLogger(){
        Logger.init(this);
    }
}
