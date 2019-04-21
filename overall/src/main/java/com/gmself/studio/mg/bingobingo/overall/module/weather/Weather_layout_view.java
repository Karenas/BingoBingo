package com.gmself.studio.mg.bingobingo.overall.module.weather;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmself.studio.mg.basemodule.utils.UITool;
import com.gmself.studio.mg.basemodule.utils.dateUtil.DateStyles;
import com.gmself.studio.mg.basemodule.utils.dateUtil.DateTool;
import com.gmself.studio.mg.bingobingo.overall.R;

import java.util.Date;

/**
 * Created by guomeng on 4/19.
 */

public class Weather_layout_view extends RelativeLayout{

    private Context context;
    private View targetView;

    private TextView date_tv;
    private TextView location_tv;
    private TextView weather_tv;
    private TextView wind_tv;
    private TextView temperature_tv;
    private TextView updateTime_tv;

    public Weather_layout_view(Context context) {
        super(context);
        init(context);
    }

    public Weather_layout_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Weather_layout_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Weather_layout_view(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        setBackground(ContextCompat.getDrawable(context, R.drawable.background_overall_weather_home));
        targetView = LayoutInflater.from(context).inflate(R.layout.layout_overall_weather, this);
        initViews(targetView);
    }

    private void initViews(View rootView){
        date_tv = rootView.findViewById(R.id.overall_date_tv);
        location_tv = rootView.findViewById(R.id.overall_location_tv);
        weather_tv = rootView.findViewById(R.id.overall_weather_tv);
        wind_tv = rootView.findViewById(R.id.overall_wind_tv);
        temperature_tv = rootView.findViewById(R.id.overall_temperature_tv);
        updateTime_tv = rootView.findViewById(R.id.overall_update_time_tv);
    }

    public void setDate(String date){
        if (TextUtils.isEmpty(date)){
            Date d = DateTool.getSystemCurrentDate();
            date = DateTool.getStringDate(d, DateStyles.YYYY_MM_DD_CN)+" 星期"+DateTool.getDayOfWeek_str(d);
        }
        this.date_tv.setText(date);
    }

    public void setLocation(String location) {
        this.location_tv.setText(location);
    }

    public void setWeather(String weather) {
        this.weather_tv.setText(weather);
    }

    public void setWind(String wind) {
        this.wind_tv.setText(wind);
    }

    public void setTemperature(String temperature) {
        this.temperature_tv.setText(temperature);
    }

    public void setUpdateTime_tv(String updateTime) {
        this.updateTime_tv.setText(updateTime);
    }
}
