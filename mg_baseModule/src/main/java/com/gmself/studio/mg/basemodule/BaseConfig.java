package com.gmself.studio.mg.basemodule;

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
}
