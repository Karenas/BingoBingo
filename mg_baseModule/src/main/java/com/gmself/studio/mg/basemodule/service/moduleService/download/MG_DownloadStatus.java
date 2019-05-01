package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.ADD_FAIL;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.ADD_IN_EXECUTE_POOL;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.ADD_IN_WAIT_POOL;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.ALREADY_INSIDE;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.COMPLETE;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.FAIL;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.PAUSE;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.PROCESS;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.READY;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.REMOVE_FAIL;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.REMOVE_SUCCESS;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.RUN;

@Documented
@IntDef(flag = false, value = {ALREADY_INSIDE, ADD_IN_EXECUTE_POOL, ADD_IN_WAIT_POOL, ADD_FAIL, REMOVE_SUCCESS, REMOVE_FAIL,
        READY, RUN, PAUSE, PROCESS, COMPLETE, FAIL})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface MG_DownloadStatus{

}