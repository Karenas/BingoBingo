package com.gmself.studio.mg.basemodule.net_work.download;

public class DownloadProgress{
    private float percent;
    private long completionSize;
    private long totalSize;

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

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}