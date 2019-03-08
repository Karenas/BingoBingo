package com.gmself.studio.mg.basemodule.mg_dataProcess;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic.DataProcessTask;
import com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic.ThreadPoolManager;


/**
 * Created by guomeng on 2018/4/8.
 *
 * 耗时任务处理工具类，将耗时操作放入子线程中进行，利用线程池对子线程进行管理
 *
 * 使用方法：
 * 调用 doProcess 方法后传入 MGDataProcess_processing 对象。
 *
 * 注意：当MGDataProcess_processing中excuse 方法返回null时，会以处理失败的方式进行处理（DataProcessTask.handler）
 *
 */

public class MGThreadTool {
    private static final MGThreadTool ourInstance = new MGThreadTool();

    public static MGThreadTool getInstance() {
        return ourInstance;
    }

    public<DataProcess extends MGThreadTool_processing> void doProcess(DataProcess dataProcess){
        ThreadPoolManager.getInstance().execute(new DataProcessTask(dataProcess));
    }

    public<DataProcess extends MGThreadTool_mainThread> void doInMainThread(DataProcess dataProcess){
        if (Thread.currentThread() != Looper.getMainLooper().getThread()){
            handler.sendMessage(handler.obtainMessage(-1, dataProcess));
        }else{
            dataProcess.executeInMainThread();
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MGThreadTool_mainThread doProcess =(MGThreadTool_mainThread) msg .obj;
            doProcess.executeInMainThread();
        }
    };


}
