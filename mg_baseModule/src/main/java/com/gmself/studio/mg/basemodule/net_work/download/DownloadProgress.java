package com.gmself.studio.mg.basemodule.net_work.download;

public class DownloadProgress{
    private float percent;
    private long completionSize;

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
}