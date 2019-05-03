package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.util.SparseArray;

import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;

import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.READY;

/**
 * Created by guomeng on 2019/4/30.
 */

public class DownloadTaskPool {

    private SparseArray<DownloadTask> executionPool;
    private SparseArray<DownloadTask> waitPool;

    private int parallelNum;
    private int maxNum;

    private volatile int taskCount = 0;

    private int key = -1;

    public DownloadTaskPool(int parallelNum, int maxNum) {
        this.parallelNum = parallelNum;
        this.maxNum = maxNum;

        executionPool = new SparseArray<>(this.parallelNum);
        waitPool = new SparseArray<>(this.maxNum);
    }

    public synchronized @MG_DownloadStatus int addTask(DownloadTask downloadTask){
        if(taskCount>=maxNum){
            Logger.log(Logger.Type.DEBUG, " task add to pool full  taskName="+downloadTask.getTaskName());
            return returnCode(DownloadStatus.TASK_POOL_FULL);
        }

        if (executionPool.indexOfValue(downloadTask) > 0 || waitPool.indexOfValue(downloadTask) > 0){
            Logger.log(Logger.Type.DEBUG, " task add to already has  taskName="+downloadTask.getTaskName());
            return returnCode(DownloadStatus.ALREADY_INSIDE);
        }
        if (executionPool.valueAt(parallelNum-1) == null){ //需保持执行队列对象连续排列
            downloadTask.setKey(key+=1);
            executionPool.put(downloadTask.getKey(), downloadTask);
            Logger.log(Logger.Type.DEBUG, " task add to executeLoop  taskName="+downloadTask.getTaskName());
            return returnCode(DownloadStatus.ADD_IN_EXECUTE_POOL);
        }else if (taskCount < maxNum){ //需在关键点统计任务个数
            downloadTask.setKey(key+=1);
            downloadTask.getLeash().setStatus(DownloadStatus.PAUSE);
            waitPool.put(downloadTask.getKey(), downloadTask);
            Logger.log(Logger.Type.DEBUG, " task add to waitLoop  taskName="+downloadTask.getTaskName());
            return returnCode(DownloadStatus.ADD_IN_WAIT_POOL);
        }{
            Logger.log(Logger.Type.DEBUG, " task add to fail  taskName="+downloadTask.getTaskName());
            return returnCode(DownloadStatus.ADD_FAIL);
        }
    }

    public synchronized @MG_DownloadStatus int removeTask(int key, @MG_DownloadStatus int status){
        if (status == DownloadStatus.RUN){
            if (executionPool.indexOfKey(key) < 0){
                return returnCode(DownloadStatus.REMOVE_FAIL);
            }else {
                executionPool.remove(key);
                return returnCode(DownloadStatus.REMOVE_SUCCESS);
            }
        }else {
            if (waitPool.indexOfKey(key) < 0){
                return returnCode(DownloadStatus.REMOVE_FAIL);
            }else {
                waitPool.remove(key);
                return returnCode(DownloadStatus.REMOVE_SUCCESS);
            }
        }
    }

    /**
     * @return false: 未找到task ; true: task 已在正确的array中存放
     * */
    public synchronized boolean checkTaskArray(DownloadTask downloadTask){
        assert downloadTask.getKey() >=0 ;

        if (downloadTask.getLeash().getStatus() == DownloadStatus.RUN || downloadTask.getLeash().getStatus() == READY){

            if (executionPool.indexOfKey(downloadTask.getKey()) < 0){

                if (executionPool.valueAt(parallelNum-1) != null){
                    DownloadTask lastExecuteTask = executionPool.valueAt(parallelNum-1);
                    lastExecuteTask.getLeash().setStatus(DownloadStatus.PAUSE);
                    turnArray(executionPool, waitPool, lastExecuteTask);
                }

                return turnArray(waitPool, executionPool, downloadTask);
            }else {
                return true;
            }
        }else {
            if (waitPool.indexOfKey(downloadTask.getKey()) < 0){
                boolean r = turnArray(executionPool, waitPool, downloadTask);
                Logger.log(Logger.Type.DEBUG, "task !!!!!!!!!!   r = "+r+"   taskName="+downloadTask.getTaskName());
                DownloadTask task;
                for (int i = 0; i < waitPool.size(); i++) {
                    task = waitPool.valueAt(i);
                    if (task!=null && task.getLeash().getStatus()==DownloadStatus.PAUSE){
                        task.getLeash().setStatus(DownloadStatus.READY);
                        task.getSeed().setShutdown(false);
                        r |= turnArray(waitPool, executionPool, task);
                        Logger.log(Logger.Type.DEBUG, "task !!!!!!!!!!   r1 = "+r+"   taskName="+task.getTaskName());
                        break;
                    }
                }
                Logger.log(Logger.Type.DEBUG, "task !!!!!!!!!!   has break");
                return r;
            }else {
                return true;
            }
        }
    }

    public synchronized SparseArray<DownloadTask> getExecutionPool() {
        return executionPool;
    }

    public synchronized SparseArray<DownloadTask> getWaitPool() {
        return waitPool;
    }

    private boolean turnArray(SparseArray<DownloadTask> from, SparseArray<DownloadTask> to, DownloadTask downloadTask){
        if (from.indexOfValue(downloadTask) < 0){
            return false;
        }
        downloadTask.setBePush(false);
        from.remove(downloadTask.getKey());
        to.put(downloadTask.getKey(), downloadTask);
        return true;
    }


    /**
     * 返回值拦截
     * */
    private @MG_DownloadStatus int returnCode(@MG_DownloadStatus int statusCode){

        if (statusCode == DownloadStatus.ADD_IN_EXECUTE_POOL || statusCode == DownloadStatus.ADD_IN_WAIT_POOL){
            taskCount+=1;
        }else if (statusCode == DownloadStatus.REMOVE_SUCCESS){
            taskCount-=1;
        }

        return statusCode;
    }

    private void reSetExecutePool(){

    }


}
