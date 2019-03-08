package com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by guomeng on 2018/3/20.
 * 线程管理类
 */

public class ThreadPoolManager {
    private static final ThreadPoolManager ourInstance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return ourInstance;
    }

    //1，把任务添加 到请求队列中
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public void execute(Runnable runnable){
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //2，产生一个线程池用来执行任务
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(4, 15, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }
    //添加一个拒绝策略
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //r 是被丢出来的线程
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //工作开始
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                Runnable runnable = null;
                try {
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(null != runnable){
                    threadPoolExecutor.execute(runnable);
                }

            }
        }
    };
}
