package com.gmself.studio.mg.basemodule.arouter;

/**
 * Created by guomeng on 3/1.
 */

public enum ENUM_RouterE {

    ACTIVITY_OVERALL_HOME("/overall/BingoHomeActivity"),//外壳首页

    ACTIVITY_FREE_FLYER_HOME("/freeFlyer/FreeFlyerActivity"),
    ;

    private String path;

    ENUM_RouterE(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
