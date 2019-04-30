package com.gmself.studio.mg.basemodule.doPort;

import android.content.Context;

import com.gmself.studio.mg.basemodule.base.net_work.BaseDoPort;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortType;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortUpMessageType;
import com.gmself.studio.mg.basemodule.net_work.constant.PortUrl;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpManger;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.net_work.http_message.PostUpJsonBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guomeng on 2018/6/5.
 *
 * 外部接口
 *
 */

public class Port_getLocationInfo extends BaseDoPort {
    String url =  "https://search.heweather.net/find";
    /**
     * @param
     * */
    public boolean doPort(Context context, String location, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(location);

        OkHttpManger.getInstance().doPostJson(postMsg, listener);
        return r;
    }

    public boolean doPort_get(Context context, String location, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;
        Map<HttpPortUpMessageType, String> parameters = new HashMap<>();
        parameters.put(HttpPortUpMessageType.KEY_HF_WEATHER, "HE1904062149471156");
        parameters.put(HttpPortUpMessageType.LOCATION_HF_WEATHER, location);

        OkHttpManger.getInstance().doGet(url, parameters, listener);
        return r;
    }

    public boolean doPortInCurrentThread(Context context, String location, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(location);

        OkHttpManger.getInstance().doPostJsonInCurrentThread(postMsg, listener);
        return r;
    }

    private PostUpJsonBean getPostMsg(String location){
        PostUpJsonBean postMsg = new PostUpJsonBean(url);
        try {
            postMsg.setGlobalParam(false);
            postMsg.addMsg(HttpPortUpMessageType.LOCATION_HF_WEATHER, location);
            postMsg.addMsg(HttpPortUpMessageType.KEY_HF_WEATHER, "HE1904060746571223");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postMsg;
    }


}
