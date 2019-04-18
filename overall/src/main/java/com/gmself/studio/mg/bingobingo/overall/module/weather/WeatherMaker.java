package com.gmself.studio.mg.bingobingo.overall.module.weather;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;
import com.gmself.studio.mg.bingobingo.overall.module.weather.doPort.ReqHFWeatherForecast;
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
    private Weather_layout_view weatherLayoutView_now;



    public void init(Context context){
//        this.mContext = context;
        this.dataBox = new DataBox();
    }

    public void freed(){
        ServiceCallBackManager.getInstance().unRegisterServiceCallBack(ServiceCallBackType.WEATHER);
        dataBox = null;
        weatherLayoutView_now = null;
    }

    public void setWeatherLayoutView(Weather_layout_view weatherLayoutView){
        this.weatherLayoutView_now = weatherLayoutView;
    }

    private void updateUI(){
        if (weatherLayoutView_now!=null){
            refreshHomeWeatherView();
        }
    }

    private void refreshHomeWeatherView(){
//        weatherLayoutView_now.set
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
            reqForecastWeather(dataBox.getCityID());
            return 0;
        }
    }

    private void reqNowWeather(String cityID){
        ReqHFWeatherNow reqHFWeatherNow = new ReqHFWeatherNow();
        reqHFWeatherNow.doPort(null, cityID, new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Post_HFWeatherNow weatherNow = JSON.parseObject(jsonStr, Post_HFWeatherNow.class);
                dataBox.setNowWeather(null);
                updateUI();
            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });
    }

    private void reqForecastWeather(String cityID){
        ReqHFWeatherForecast reqHFWeatherForecast = new ReqHFWeatherForecast();
        reqHFWeatherForecast.doPort(null, cityID, new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Post_HFWeatherNow weatherNow = JSON.parseObject(jsonStr, Post_HFWeatherNow.class);
                dataBox.setNowWeather(null);
                updateUI();
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
