package com.gmself.studio.mg.basemodule.net_work.download;

import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;

/**
 * Created by guomeng on 4/28.
 */

public class DownloadTask {

    private DownloadSeed seed;
    private DownloadLeash leash;

    private boolean bePush = false;
//    private int key = -1;

    private String taskName;
    private long totalSize;

//    public int getKey() {
//        return key;
//    }
//
//    public void setKey(int key) {
//        this.key = key;
//    }

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
        return this.leash.getListener();
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
        if (this.totalSize <= 0 )
            this.totalSize = totalSize;
    }

    public boolean isBePush() {
        return bePush;
    }

    public void setBePush(boolean bePush) {
        this.bePush = bePush;
    }
}
