package com.gmself.studio.mg.basemodule.base.net_work;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by guomeng on 2018/6/4.
 */

public class BaseDoPort {
    private Context context;

    private void onBefore(Context context){
        this.context = context;
        if (Thread.currentThread() == Looper.getMainLooper().getThread()){
            doOnBefore();
        }else {
            handler.sendMessage(handler.obtainMessage(0));
        }
    }

    public void doPortFinish(Context context){
        if (Thread.currentThread() == Looper.getMainLooper().getThread()){
            doOnFinish();
        }else {
            handler.sendMessage(handler.obtainMessage(1));
        }
    }

    protected void doPort(Context context){
        onBefore(context);
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                doOnBefore();
            }else {
                doOnFinish();
            }
        }
    };

    private void doOnBefore(){
        if (null != context){
//            PromptTools.showWaitNetDialog(context);
        }
//        Toast.makeText(context, "Http Port before ", Toast.LENGTH_LONG).show();
    }

    private void doOnFinish(){
//        PromptTools.closeWaitNetDialog();
//        Toast.makeText(context, "Http Port finish ", Toast.LENGTH_LONG).show();
    }

}
