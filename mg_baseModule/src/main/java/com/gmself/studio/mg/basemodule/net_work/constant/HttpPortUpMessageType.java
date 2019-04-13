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

    LAST_LOCATION_ID("lastLocationId"),

    EMAIL("email"),

    NICK_NAME("nickName"),

    SIGNATURE("signature"),

    SEX("sex"),

    DEVICE_ID("deviceId"),

    LOCATION_HF_WEATHER("location"),

    KEY_HF_WEATHER("key"),

    CITY_ID_HF_WEATHER("cityID"),

    ;

    String paramName ;

    HttpPortUpMessageType(String paramName){
        this.paramName = paramName;
    }

    public String getParamName(){
        return paramName;
    }
}
