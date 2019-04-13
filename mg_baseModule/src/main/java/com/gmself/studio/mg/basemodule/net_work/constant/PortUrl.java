package com.gmself.studio.mg.basemodule.net_work.constant;

import com.gmself.studio.mg.basemodule.BaseConfig;

/**
 * Created by guomeng on 2018/5/22.
 *
 * 接口地址拼接类
 */

public class PortUrl {

//    private static final String port_81 = "81";
//
    private static final String debugPortIp = "http://192.168.50.30:59000";
//    private static final String debugPortIp = "http://192.168.1.240:59000";
//    private static final String debugPortIp = "http://192.168.137.1:59000";
//    private static final String debugPortIp = "http://47.94.200.244:81";
//    private static final String debugPortIp = "http://101.254.117.74:59002";
//        private static final String debugPortIp = "http://192.168.1.110:52002";
//    private static final String debugPortIp = "http://192.168.1.236:52001";
//    private static final String debugPortIp = "http://192.168.1.233:52001";//薛赟

//    private static final String releasePortIp = "https://pms.hntpsjwy.com";
    private static final String releasePortIp = "http://www.gmself.com";

    public static String getUrl(HttpPortType httpPortType){
        String url = null;
        switch (httpPortType){
            case DEFAULT:
//                url = "http://ip.taobao.com/service/getIpInfo.php";
                break;
            case UPLOAD_FILE:
                url = "/UploadAllObjectServlet?server=upload&";
                return releasePortIp+url;
            case TEST:
                url = "/bingobingo/m_subject/punch";
                break;
            case REQ_HF_WEATHER_NOW:
                url = "/bingobingo/m_subject/requestWeatherNow";
                break;
            case REQ_HF_WEATHER_FORECAST:
                url = "/bingobingo/m_subject/requestWeatherForecast";
                break;
            default:
                break;
        }

        if (BaseConfig.getInstance().isRunDebug()){
            url = debugPortIp+url;
        }else{
            url = releasePortIp+url;
        }
        return url;
    }

}
