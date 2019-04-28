package com.gmself.studio.mg.basemodule;

import android.content.Context;

import com.gmself.studio.mg.basemodule.environment.DeviceInfo;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.utils.dirUtil.DirsTools;

/**
 * Created by guomeng on 3/24.
 */

public class BaseConfig {
    private static final BaseConfig ourInstance = new BaseConfig();

    public static BaseConfig getInstance() {
        return ourInstance;
    }

    private boolean isRunDebug = true;

    public boolean isRunDebug() {
        return isRunDebug;
    }

    public void setRunDebug(boolean runDebug) {
        isRunDebug = runDebug;
    }

    public void initData(Context context){
        initDirPath(context.getApplicationContext());
        initLogger(context.getApplicationContext());
    }

    public void initDeviceInfo(Context context){
        DeviceInfo.getInstance().init(context.getApplicationContext());
    }

    private void initDirPath(Context context){
        DirsTools.init(context, isRunDebug);
    }

    private void initLogger(Context context){
        Logger.init(context);
    }
}
