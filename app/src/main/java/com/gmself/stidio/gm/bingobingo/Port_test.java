package com.gmself.stidio.gm.bingobingo;

import android.content.Context;

import com.gmself.studio.mg.basemodule.base.net_work.BaseDoPort;
import com.gmself.studio.mg.basemodule.entity.User;
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
    public boolean doPort(Context context, User user, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(user);

        OkHttpManger.getInstance().doPostJson(postMsg, listener);
        return r;
    }

    public boolean doPortInCurrentThread(Context context, User user, OkHttpListener listener){
        super.doPort(context);
        boolean r = true;

        PostUpJsonBean postMsg = getPostMsg(user);

        OkHttpManger.getInstance().doPostJsonInCurrentThread(postMsg, listener);
        return r;
    }

    private PostUpJsonBean getPostMsg(User user){
        String url =  PortUrl.getUrl(HttpPortType.TEST);
        PostUpJsonBean postMsg = new PostUpJsonBean(url);
        try {
            postMsg.setGlobalParam(false);
            postMsg.addMsg(HttpPortUpMessageType.NAME, user.getName());
            postMsg.addMsg(HttpPortUpMessageType.PHONE_NUMBER, user.getPhoneNumber());
            postMsg.addMsg(HttpPortUpMessageType.LAST_LOCATION_ID, user.getLastLocationId());
            postMsg.addMsg(HttpPortUpMessageType.EMAIL, user.getEmail());
            postMsg.addMsg(HttpPortUpMessageType.NICK_NAME, user.getNickName());
            postMsg.addMsg(HttpPortUpMessageType.SIGNATURE, user.getSignature());
            postMsg.addMsg(HttpPortUpMessageType.SEX, user.getSex());
            postMsg.addMsg(HttpPortUpMessageType.DEVICE_ID, user.getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postMsg;
    }


}
