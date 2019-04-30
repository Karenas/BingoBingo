package com.gmself.studio.mg.basemodule.service.moduleService.download;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.gmself.studio.mg.basemodule.doPort.Port_getLocationInfo;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.entity.port_entity.Get_HFLocation;
import com.gmself.studio.mg.basemodule.environment.LocationInfo;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonStr;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;

import java.io.UnsupportedEncodingException;

/**
 * Created by guomeng on 4/5.
 */

public class DownloadService extends IntentService {

    private boolean survive = true;

    private int interval = 1000;

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
    protected void onHandleIntent(@Nullable Intent intent) {
        while (survive){
            SystemClock.sleep(interval);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.log(Logger.Type.DEBUG, " service  destroy---------");
    }
}
