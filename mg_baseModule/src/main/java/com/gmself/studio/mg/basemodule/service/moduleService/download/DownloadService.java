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

    private int interval = 1000;

    private DownloadTaskPool taskPool = new DownloadTaskPool(3, 10);

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
            r |= checkExecutePush();

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

    private boolean checkExecuteTaskStatus(){
        DownloadTask task;
        boolean r = false;
        for (int i = 0; i < taskPool.getExecutionPool().size(); i++) {
            task = taskPool.getExecutionPool().valueAt(i);
            if (task.getLeash().getStatus()!=READY &&
                    task.getLeash().getStatus()!=RUN){
                r |= taskPool.checkTaskArray(task);
            }
        }
        return r;
    }

    private boolean checkExecutePush(){
        boolean r = false;
        DownloadTask task;
        for (int i = 0; i < taskPool.getExecutionPool().size(); i++) {
            task = taskPool.getExecutionPool().valueAt(i);
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
    public boolean addTask(DownloadTask task){
        int code =  taskPool.addTask(task);
        if (code == DownloadStatus.ADD_FAIL){
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
        return taskPool.checkTaskArray(task);
    }

    public boolean startTask(DownloadTask task){
        task.getLeash().setStatus(READY);
        return taskPool.checkTaskArray(task);
    }

    public SparseArray<DownloadTask> getExecutionPool() {
        return taskPool.getExecutionPool();
    }

    public SparseArray<DownloadTask> getWaitPool() {
        return taskPool.getWaitPool();
    }

}
