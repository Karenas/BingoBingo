package com.gmself.studio.mg.bingobingo.overall.module.weather;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonObj;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;
import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.module.weather.doPort.ReqHFWeatherForecast;
import com.gmself.studio.mg.bingobingo.overall.module.weather.doPort.ReqHFWeatherNow;
import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.HFWeatherNOW;
import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.port_entity.Post_HFWeatherNow;
import com.gmself.studio.mg.bingobingo.overall.module.weather.moudle_life.LifeCycleListener;
import com.gmself.studio.mg.bingobingo.overall.module.weather.moudle_life.WeatherLifeView;

/**
 * Created by guomeng on 4/7.
 */

public class WeatherMaker implements LifeCycleListener {
    private static final WeatherMaker ourInstance = new WeatherMaker();

    public static WeatherMaker getInstance() {
        return ourInstance;
    }

    private DataBox dataBox;
    private Weather_layout_view weatherLayoutView_now;

    private Activity activity;

    public void freed(){
        ServiceCallBackManager.getInstance().unRegisterServiceCallBack(ServiceCallBackType.WEATHER);
        dataBox = null;
        weatherLayoutView_now = null;
    }

    /**
     * 当然这里可能还有FragmentActivity，Fragment等参数，再此只给出一种方式，其他同理
     * @param activity 传入的上下文
     */
    public void bindThis(Activity activity, Class weatherUIView) {
        this.activity = activity;
        setWeatherLayoutView(weatherUIView);
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentGet(fragmentManager, isActivityVisible(activity));
    }

    private boolean isActivityVisible(Activity activity) {
        return !activity.isFinishing();
    }

    private void fragmentGet(FragmentManager fragmentManager, boolean activityVisible) {
        WeatherLifeView current = new WeatherLifeView();
        fragmentManager.beginTransaction().add(current, "weatherLife").commitAllowingStateLoss();
        current.getLifeCycle().addListener(this);
    }

    private void setWeatherLayoutView(Class weatherUIView){
        if (weatherUIView == Weather_layout_view.class){
            weatherLayoutView_now = this.activity.getWindow().findViewById(R.id.overall_home_weather_root_ll);
        }
    }

    private void updateUI(){
        if (weatherLayoutView_now!=null && null!=dataBox.getNowWeather()){
            refreshHomeWeatherView();
        }
    }

    private void refreshHomeWeatherView(){
        HFWeatherNOW weatherNOW = dataBox.getNowWeather();
        weatherLayoutView_now.setDate(null);
        weatherLayoutView_now.setLocation(dataBox.getCityName());
        weatherLayoutView_now.setTemperature("温度："+weatherNOW.getTmp()+"℃  体感温度："+weatherNOW.getFl()+"℃");
        weatherLayoutView_now.setWeather(weatherNOW.getCondTxt());
        weatherLayoutView_now.setWind(weatherNOW.getWindDir()+" "+weatherNOW.getWindSc()+" 级");
        weatherLayoutView_now.setUpdateTime_tv("数据测量时间："+weatherNOW.getUpdateTime());
    }

    public void registerServiceCallBack(){
        //注册前准备好数据容器
        if (null == this.dataBox){
            this.dataBox = new DataBox();
        }

        ServiceCallBackManager.getInstance().registerServiceCallBack(
                ServiceCallBackType.WEATHER,
                locationListener
        );
    }

    ServiceCallBack locationListener = new LocationListener();

    @Override
    public void onStart() {
        if (dataBox!=null){
            updateUI();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }


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
        reqHFWeatherNow.doPort(null, cityID, new OKHttpListenerJsonObj() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Post_HFWeatherNow weatherNow = JSON.toJavaObject(jsonObject, Post_HFWeatherNow.class);
                dataBox.setNowWeather(weatherNow.getWeatherNow());
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
        reqHFWeatherForecast.doPort(null, cityID, new OKHttpListenerJsonObj() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
//                Post_HFWeatherNow weatherNow = JSON.parseObject(jsonStr, Post_HFWeatherNow.class);
//                dataBox.setNowWeather(null);
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
