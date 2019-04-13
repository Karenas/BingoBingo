package com.gmself.studio.mg.bingobingo.overall.module.weather.doPort;

import android.content.Context;

import com.gmself.studio.mg.basemodule.base.net_work.BaseDoPort;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortType;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortUpMessageType;
import com.gmself.studio.mg.basemodule.net_work.constant.PortUrl;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpManger;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.net_work.http_message.PostUpJsonBean;

import org.json.JSONException;

/**
 * Created by guomeng on 4/7.
 */

public class ReqHFWeatherForecast extends BaseDoPort{

    /**
     * @param
     * */
    public boolean doPort(Context context, String cityID, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(cityID);

        OkHttpManger.getInstance().doPostJson(context, postMsg, listener);
        return r;
    }

    public boolean doPortInCurrentThread(Context context, String cityID, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(cityID);

        OkHttpManger.getInstance().doPostJsonInCurrentThread(context, postMsg, listener);
        return r;
    }

    private PostUpJsonBean getPostMsg(String cityID){
        String url =  PortUrl.getUrl(HttpPortType.REQ_HF_WEATHER_FORECAST);
        PostUpJsonBean postMsg = new PostUpJsonBean(url);
        try {
            postMsg.setGlobalParam(false);
            postMsg.addMsg(HttpPortUpMessageType.CITY_ID_HF_WEATHER, cityID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postMsg;
    }
}
