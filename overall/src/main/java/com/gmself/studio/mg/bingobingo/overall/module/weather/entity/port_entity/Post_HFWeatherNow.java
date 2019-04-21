package com.gmself.studio.mg.bingobingo.overall.module.weather.entity.port_entity;


import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.HFWeatherNOW;

/**
 * Created by guomeng on 4/6.
 */

public class Post_HFWeatherNow {
    private String code;
    private String msg;
    private HFWeatherNOW weatherNow;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HFWeatherNOW getWeatherNow() {
        return weatherNow;
    }

    public void setWeatherNow(HFWeatherNOW weatherNow) {
        this.weatherNow = weatherNow;
    }
}
