package com.gmself.studio.mg.basemodule.net_work.download;

/**
 * Created by guomeng on 4/28.
 */

public class DownloadHandler {


    private int status = -1; // -1准备中， 0 下载， 1 暂停 2 停止 3 取消中 4 已取消 5 保存中 6 已完成
    private float percent; //百分比
    private long completionSize; //已完成大小
    private int speed;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
