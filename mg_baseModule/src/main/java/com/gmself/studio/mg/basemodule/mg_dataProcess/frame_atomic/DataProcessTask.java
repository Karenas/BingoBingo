package com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * Created by guomeng on 2018/3/20.
 * 一次数据处理
 */

public class DataProcessTask implements Runnable {
    private IDataProcess dataProcess;

    public<T> DataProcessTask(IDataProcess iDataProcess) {
        this.dataProcess=iDataProcess;
    }

    @Override
    public void run() {
        Object obj = dataProcess.execute();

        Message msg = handler.obtainMessage(0, obj);
        handler.sendMessage(msg);

    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj == null){ //若无返回数据则判为处理失败
                dataProcess.onFailure();
            }else{
                dataProcess.onSuccess(msg.obj);
            }
        }
    };

}
