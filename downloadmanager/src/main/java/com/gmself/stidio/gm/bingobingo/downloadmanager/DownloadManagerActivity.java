package com.gmself.stidio.gm.bingobingo.downloadmanager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool_mainThread;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadLeash;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpManger;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;
import com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadService;

import java.io.FileNotFoundException;

/**
 * Created by guomeng on 5/1.
 */
@Route(path = "/downloadManager/DownloadManagerActivity")
public class DownloadManagerActivity extends BaseActivity{

    private RecyclerView download_manager_main_rv;
    private DownloadManagerMainAdapter adapter;
    private DownloadService downloadService;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_downloadmanager_main;
    }

    @Override
    public void initView() {
        download_manager_main_rv = findViewById(R.id.download_manager_main_rv);

        adapter = new DownloadManagerMainAdapter(this);
        download_manager_main_rv.setLayoutManager(new LinearLayoutManager(this));
        download_manager_main_rv.setItemAnimator(new DefaultItemAnimator());
        download_manager_main_rv.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        registerDownloadServiceListener();
    }

    @Override
    public void setFunction() {
        bindDownloadService();
    }

    private void registerDownloadServiceListener(){
        ServiceCallBack callBack = new DownloadListener();
        ServiceCallBackManager.getInstance().registerServiceCallBack(ServiceCallBackType.DOWNLOAD, callBack);
    }

    class DownloadListener implements ServiceCallBack{
        @Override
        public int callback(Object param) {
            MGThreadTool.getInstance().doInMainThread(new MGThreadTool_mainThread() {
                @Override
                public void executeInMainThread() {
                    adapter.setmList(downloadService.getExecutionPool(), downloadService.getWaitPool());
                    adapter.notifyDataSetChanged();
                }
            });
            return 0;
        }
    }


    private void bindDownloadService(){
        Intent downloadServerIntent = new Intent(this.getApplicationContext(), DownloadService.class);
        bindService(downloadServerIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                downloadService = ((DownloadService.MsgBinder)service).getService();
                adapter.setmList(downloadService.getExecutionPool(), downloadService.getWaitPool());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
    }


    int tempI = 0;
    public void onTest(View view){
        if (downloadService == null){
            return;
        }

        DownloadLeash leash = new DownloadLeash();
        try {
            DownloadTask task = OkHttpManger.getInstance().makeDownloadTask("http://tcy.198424.com/aejiaobengifgunzuixin.zip",
                    "bingo_"+(tempI+=1)+".zip", leash);
            downloadService.addTask(task);
            adapter.setmList(downloadService.getExecutionPool(), downloadService.getWaitPool());
            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
