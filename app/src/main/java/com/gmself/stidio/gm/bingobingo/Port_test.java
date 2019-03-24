package com.gmself.stidio.gm.bingobingo;

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
 * Created by guomeng on 2018/6/5.
 */

public class Port_test extends BaseDoPort {

    /**
     * @param
     * */
    public boolean doPort(Context context, String phoneNumber, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(phoneNumber);

        OkHttpManger.getInstance().doPostJson(context, postMsg, listener);
        return r;
    }

    public boolean doPortInCurrentThread(Context context, String phoneNumber, OkHttpListener listener){
        super.doPort(context);

        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(phoneNumber);

        OkHttpManger.getInstance().doPostJsonInCurrentThread(context, postMsg, listener);
        return r;
    }

    private PostUpJsonBean getPostMsg(String phoneNumber){
        String url =  PortUrl.getUrl(HttpPortType.TEST);
        PostUpJsonBean postMsg = new PostUpJsonBean(url);
        try {
            postMsg.setGlobalParam(true);
            postMsg.addMsg(HttpPortUpMessageType.TEST, phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postMsg;
    }


}
