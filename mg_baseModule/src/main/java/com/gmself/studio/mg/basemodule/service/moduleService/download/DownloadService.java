package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.gmself.studio.mg.basemodule.doPort.Port_getLocationInfo;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.entity.port_entity.Get_HFLocation;
import com.gmself.studio.mg.basemodule.environment.LocationInfo;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpManger;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonStr;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;

import java.io.UnsupportedEncodingException;

import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.PAUSE;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.READY;
import static com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus.RUN;

/**
 * Created by guomeng on 4/5.
 */

public class DownloadService extends IntentService {

    private boolean survive = true;

    private int interval = 2000;

    private int maxNum = 10;
    private int parallelNum = 3;

    private DownloadTaskPool taskPool = new DownloadTaskPool(parallelNum, maxNum);

    public DownloadService() {
        super("DownloadService");
    }


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public DownloadService getService(){
            return DownloadService.this;
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        boolean r = false;
        while (survive){
            SystemClock.sleep(interval);
            r |= checkExecuteTaskStatus();
            checkWaitTaskStatus();
            r |= checkExecutePush();

            Logger.log(Logger.Type.DEBUG, "task 更新一次UI 前service的r="+r);
            if (r){
                ServiceCallBackManager.getInstance().callback(ServiceCallBackType.DOWNLOAD, null);
                r = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.log(Logger.Type.DEBUG, " service  destroy---------");
    }

    private synchronized boolean checkExecuteTaskStatus(){
        DownloadTask task;
        boolean r = false;
        for (int i = 0; i < taskPool.getExecutionPool().size(); i++) {
            task = taskPool.getExecutionPool().valueAt(i);
            Logger.log(Logger.Type.DEBUG, " task checkExecuteTaskStatus  task status="+task.getLeash().getStatus() +"    taskName="+task.getTaskName());

            if (task.getLeash().getStatus()!=READY &&
                    task.getLeash().getStatus()!=RUN){
                r |= taskPool.checkTaskArray(task);
            }
        }
        return r;
    }

    private synchronized boolean checkWaitTaskStatus(){
        DownloadTask task;
        boolean r = false;
        for (int i = 0; i < taskPool.getWaitPool().size(); i++) {
            task = taskPool.getWaitPool().valueAt(i);
            if (!task.getSeed().isShutdown()){
                task.getSeed().setShutdown(true);
            }
        }
        return r;
    }

    private synchronized boolean checkExecutePush(){
        boolean r = false;
        DownloadTask task;
        for (int i = 0; i < taskPool.getExecutionPool().size(); i++) {
            task = taskPool.getExecutionPool().valueAt(i);

            Logger.log(Logger.Type.DEBUG, " task checkExecutePush  task bePush="+task.isBePush() +"    taskName="+task.getTaskName());

            if (!task.isBePush()){
                OkHttpManger.getInstance().downloadFileAsyn(task);
                task.setBePush(true);
                r = true;
            }
        }
        return r;
    }

    /**
     *
     * */
    public synchronized boolean addTask(DownloadTask task){
        int code =  taskPool.addTask(task);
        if (code == DownloadStatus.ADD_FAIL || code == DownloadStatus.TASK_POOL_FULL){
            return false;
        }else {
            return true;
        }
    }

    public boolean stopTask(DownloadTask task){
        int code =  taskPool.removeTask(task.getKey(), task.getLeash().getStatus());
        if (code == DownloadStatus.REMOVE_FAIL){
            return false;
        }else {
            return true;
        }
    }

    public boolean pauseTask(DownloadTask task){
        task.getLeash().setStatus(PAUSE);
//        task.getSeed().setShutdown(true);
        return true;
//        return taskPool.checkTaskArray(task);
    }

    public boolean startTask(DownloadTask task){
        task.getLeash().setStatus(READY);
        return taskPool.checkTaskArray(task);
    }

    public synchronized SparseArray<DownloadTask> getExecutionPool() {
        return taskPool.getExecutionPool();
    }

    public synchronized SparseArray<DownloadTask> getWaitPool() {
        return taskPool.getWaitPool();
    }

}
