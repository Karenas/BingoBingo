package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.util.SparseArray;

import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;

/**
 * Created by guomeng on 2019/4/30.
 */

public class DownloadTaskPool {

    private SparseArray<DownloadTask> executionPool;
    private SparseArray<DownloadTask> waitPool;

    private int parallelNum;
    private int maxNum;

    private int key = -1;

    public DownloadTaskPool(int parallelNum, int maxNum) {
        this.parallelNum = parallelNum;
        this.maxNum = maxNum - parallelNum;

        executionPool = new SparseArray<>(this.parallelNum);
        waitPool = new SparseArray<>(this.maxNum);
    }

    public synchronized void addTake(DownloadTask downloadTask){
        if (executionPool.indexOfValue(downloadTask) != -1){
//            return executionPool.keyAt(executionPool.indexOfValue(downloadTask));
        }
        if (executionPool.valueAt(parallelNum-1) == null){
            downloadTask.setKey(key++);
            executionPool.put(downloadTask.getKey(), downloadTask);
//            return key;
        }else {
            downloadTask.setKey(key++);
            waitPool.put(downloadTask.getKey(), downloadTask);
//            return
        }



    }

}
