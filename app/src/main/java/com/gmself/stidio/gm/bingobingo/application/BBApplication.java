package com.gmself.stidio.gm.bingobingo.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gmself.studio.mg.basemodule.BaseConfig;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpManger;

/**
 * Created by guomeng on 2/28.
 */

public class BBApplication extends Application {

    private final boolean isRunDebug = false;



    @Override
    public void onCreate() {
        super.onCreate();

        BaseConfig.getInstance().setRunDebug(isRunDebug);

        OkHttpManger.getInstance().initHttp(this);

        initARouter();
        initLogger();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
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
