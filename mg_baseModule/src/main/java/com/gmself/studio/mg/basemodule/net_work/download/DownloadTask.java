package com.gmself.studio.mg.basemodule.net_work.download;

import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;

/**
 * Created by guomeng on 4/28.
 */

public class DownloadTask {

    private DownloadSeed seed;
    private DownloadHandler handler;
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

    public DownloadHandler getHandler() {
        return handler;
    }

    public void setHandler(DownloadHandler handler_) {
        this.handler = handler_;
        listener = new OKHttpListenerDownload() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(float percent, long completionSize) {
                handler.setPercent(percent);
                handler.setCompletionSize(completionSize);
            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        };
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
