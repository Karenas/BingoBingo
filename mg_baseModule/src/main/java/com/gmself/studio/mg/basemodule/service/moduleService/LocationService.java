package com.gmself.studio.mg.basemodule.service.moduleService;

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
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;

import java.io.UnsupportedEncodingException;

/**
 * Created by guomeng on 4/5.
 */

public class LocationService extends IntentService {

    private static double longitude;//经度
    private static double latitude;//纬度

    private boolean doGetLocationInfoPort = true;

    public LocationService() {
        super("LocationService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final Location[] locationOnChange = new Location[1];
        LocationInfo.getInstance(this).getLngAndLat(new LocationInfo.OnLocationResultListener() {
            @Override
            public void onLocationResult(Location location) {
                if (null!=location){
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }

            @Override
            public void OnLocationChange(Location location) {
                if (null!=location){
                    locationOnChange[0] = location;
                }
                Logger.log(Logger.Type.DEBUG, " service 2 ----longitude = "+longitude +"  latitude = "+latitude);
            }
        });

        while (true){
            SystemClock.sleep(5000);
             Location location = locationOnChange[0];
            if (!doGetLocationInfoPort && null != location){
                doGetLocationInfoPort = true;
            }

            if (doGetLocationInfoPort){

                if (null == location){
                    try {
                        doPortGetCityInfo();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    doGetLocationInfoPort = false;
                    continue;
                }

                double lon_ =  location == null ? 0 : Math.abs(longitude - location.getLongitude());
                double lat_ =  location == null ? 0 : Math.abs(latitude = location.getLatitude());

                if ((Math.pow(lon_,2) + Math.pow(lat_,2)) > Math.pow(0.05,2)){
                    longitude = location == null ? longitude : location.getLongitude();
                    latitude = location == null ? latitude : location.getLatitude();

                    try {
                        doPortGetCityInfo();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    doGetLocationInfoPort = false;
                }
            }
        }
    }

    private void doPortGetCityInfo() throws UnsupportedEncodingException {
        Port_getLocationInfo portGetLocationInfo = new Port_getLocationInfo();

        portGetLocationInfo.doPort_get(this, longitude + "," + latitude, new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Get_HFLocation location = JSON.parseObject(jsonStr, Get_HFLocation.class);
                if (location.getHeWeather6().get(0).getStatus().equals("ok")){
                    LocationBasic basic = location.getHeWeather6().get(0).getBasic().get(0);

                    //回调请求并处理天气信息
                    ServiceCallBackManager.getInstance().callback(ServiceCallBackType.WEATHER, basic);
                }

            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.log(Logger.Type.DEBUG, " service  destroy---------");
    }
}
