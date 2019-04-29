package com.gmself.studio.mg.basemodule.net_work.download;

import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;

/**
 * Created by guomeng on 4/28.
 */

public class DownloadTask {

    private DownloadSeed seed;
    private DownloadLeash leash;
    private OKHttpListenerDownload listener;

    private String taskName;
    private long totalSize;
    private int type;


    public DownloadSeed getSeed() {
        return seed;
    }

    public void setSeed(DownloadSeed seed) {
        this.seed = seed;
    }

    public DownloadLeash getLeash() {
        return leash;
    }

    public void setLeash(DownloadLeash leash) {
        this.leash = leash;
    }

    public OKHttpListenerDownload getListener() {
        return listener;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
