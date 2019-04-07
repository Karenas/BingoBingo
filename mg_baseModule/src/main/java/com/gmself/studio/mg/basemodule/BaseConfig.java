package com.gmself.studio.mg.basemodule;

import android.content.Context;

import com.gmself.studio.mg.basemodule.environment.DeviceInfo;

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
        initDeviceInfo(context);
    }

    private void initDeviceInfo(Context context){
        DeviceInfo.getInstance().init(context);
    }

}
