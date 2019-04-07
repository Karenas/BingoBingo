package com.gmself.studio.mg.bingobingo.overall.module.weather;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;
import com.gmself.studio.mg.bingobingo.overall.module.weather.doPort.ReqHFWeatherNow;
import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.port_entity.Post_HFWeatherNow;

/**
 * Created by guomeng on 4/7.
 */

public class WeatherMaker{
    private static final WeatherMaker ourInstance = new WeatherMaker();

    public static WeatherMaker getInstance() {
        return ourInstance;
    }

    private DataBox dataBox;
    private Context mContext;



    public void init(Context context){
        this.mContext = context;
    }

    public void freed(){
        ServiceCallBackManager.getInstance().unRegisterServiceCallBack(ServiceCallBackType.WEATHER);
        dataBox = null;
        mContext = null;
    }

    public void registerServiceCallBack(){
        ServiceCallBackManager.getInstance().registerServiceCallBack(
                ServiceCallBackType.WEATHER,
                locationListener
        );
    }

    ServiceCallBack locationListener = new LocationListener();

    class LocationListener implements ServiceCallBack<LocationBasic>{
        @Override
        public int callback(LocationBasic param) {
            dataBox.setBaseData(param);

            reqNowWeather(dataBox.getCityID());

            return 0;
        }
    }

    private void reqNowWeather(String cityID){
        ReqHFWeatherNow reqHFWeatherNow = new ReqHFWeatherNow();
        reqHFWeatherNow.doPort(mContext, cityID, new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Post_HFWeatherNow weatherNow = JSON.parseObject(jsonStr, Post_HFWeatherNow.class);


            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });
    }


}
