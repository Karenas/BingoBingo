package com.gmself.studio.mg.basemodule.net_work.constant;

/**
 * Created by guomeng on 2018/6/4.
 *
 *
 * 接口参数 键值key
 */

public enum HttpPortUpMessageType {

    PHONE_NUMBER("phoneNumber"),

    NAME("name"),

    LOCATION_HF_WEATHER("location"),

    KEY_HF_WEATHER("key"),

    CITY_ID_HF_WEATHER("cityId"),

    ;

    String paramName ;

    HttpPortUpMessageType(String paramName){
        this.paramName = paramName;
    }

    public String getParamName(){
        return paramName;
    }
}
