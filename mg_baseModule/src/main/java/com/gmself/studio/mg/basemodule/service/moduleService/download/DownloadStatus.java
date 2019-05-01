package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by guomeng on 5/1.
 */

public class DownloadStatus {

    public static final int ALREADY_INSIDE = 1;

    public static final int ADD_IN_EXECUTE_POOL = 2;
    public static final int ADD_IN_WAIT_POOL = 3;
    public static final int ADD_FAIL = 4;

    public static final int REMOVE_SUCCESS = 5;
    public static final int REMOVE_FAIL = 6;

    public static final int READY = 10;
    public static final int RUN = 11;
    public static final int PAUSE = 12;
    public static final int PROCESS = 13;
    public static final int COMPLETE = 14;
    public static final int FAIL = 15;


}
