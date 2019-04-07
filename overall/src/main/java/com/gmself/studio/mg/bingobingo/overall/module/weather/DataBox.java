package com.gmself.studio.mg.bingobingo.overall.module.weather;

import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.HFWeatherForecast;
import com.gmself.studio.mg.bingobingo.overall.module.weather.entity.HFWeatherNOW;

import java.util.List;

/**
 * Created by guomeng on 4/6.
 */

public class DataBox {

    private HFWeatherNOW nowWeather;

    private List<HFWeatherForecast> forecastWeathers;

    private String cityName;

    private String cityID;

    private String country;

    private String area;


    public void setBaseData(LocationBasic locationBasic){
        cityName = locationBasic.getParent_city()+" - "+locationBasic.getLocation();
        country = locationBasic.getCnty();
        area = locationBasic.getAdmin_area();
        cityID = locationBasic.getCid();
    }

    public void setNowWeather(HFWeatherNOW nowWeather) {
        this.nowWeather = nowWeather;
    }

    public void setForecastWeathers(List<HFWeatherForecast> forecastWeathers) {
        this.forecastWeathers = forecastWeathers;
    }

    public HFWeatherNOW getNowWeather() {
        return nowWeather;
    }

    public List<HFWeatherForecast> getForecastWeathers() {
        return forecastWeathers;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getArea() {
        return area;
    }

    public String getCityID() {
        return cityID;
    }
}
