package com.gmself.studio.mg.basemodule.utils.zipUtil;

import android.text.TextUtils;

/**
 * Created by guomeng on 4/27.
 */

public class ZipTaskBean {

    private ZipSeed zipSeed;

    private ZipTaskListener taskListener;

    private int code;

    public ZipSeed getZipSeed() {
        return zipSeed;
    }

    public void setZipSeed(ZipSeed zipSeed) {
        this.zipSeed = zipSeed;
        code = checkSeedType(getZipSeed());
    }

    public ZipTaskListener getTaskListener() {
        return taskListener;
    }

    public void setTaskListener(ZipTaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public int getCode() {
        return code;
    }

    private int checkSeedType(ZipSeed seed){
        int code = 0;

        if (TextUtils.isEmpty(seed.getZipFileAbsolute()) || TextUtils.isEmpty(seed.getFilesPath())){
            return -1;
        }

        if (seed.isActionTag()){ // 1  or 0
            code^=1;
        }

        if (!TextUtils.isEmpty(seed.getPassword())){ // 11 or 01;
            code ^= 1<<1;
        }

        if (null!=seed.getSuffix()){// 111 or 011;
            code ^= 1<<2;
        }
        return code;
    }
}
