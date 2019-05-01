package com.gmself.studio.mg.basemodule.net_work.download;

import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;
import com.gmself.studio.mg.basemodule.service.moduleService.download.MG_DownloadStatus;

import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.READY;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.RUN;

/**
 * Created by guomeng on 4/28.
 *
 * DownTask中用于通知主线程的部分
 *
 */

public class DownloadLeash {


    private @MG_DownloadStatus int status = READY;
    private float percent; //百分比
    private long completionSize; //已完成大小
    private int speed;

    private OKHttpListenerDownload listener;

    public void setListener(OKHttpListenerDownload listener) {
        this.listener = listener;
    }

    public OKHttpListenerDownload getListener() {
        return listener;
    }

    public @MG_DownloadStatus int getStatus() {
        return status;
    }

    public void setStatus(@MG_DownloadStatus int status) {
        this.status = status;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public long getCompletionSize() {
        return completionSize;
    }

    public void setCompletionSize(long completionSize) {
        this.completionSize = completionSize;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
