package com.gmself.studio.mg.basemodule.net_work.http_message;

import android.text.TextUtils;

import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortUpMessageType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guomeng on 2018/5/22.
 *
 * 上行参数 Json实体类
 *
 * https://blog.csdn.net/u010058586/article/details/41822843
 *
 */

public class PostUpJsonBean {
    private JSONObject paramsJSON = null;
    private String url = null;

    public PostUpJsonBean(String url){
        paramsJSON = new JSONObject();
        this.url = url;
    }

    public void setGlobalParam(boolean mustBeLogin) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
//        paramsJSON.put(HttpPortUpMessageType.GCID.getParamName(), "0371070");
        if (mustBeLogin){

        }

    }

    public void addMsg(HttpPortUpMessageType key, int int_value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), int_value);
    }

    public void addMsg(HttpPortUpMessageType key, long long_value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), long_value);
    }

    public void addMsg(HttpPortUpMessageType key, boolean boolean_value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), boolean_value);
    }

    public void addMsg(HttpPortUpMessageType key, double doublei_value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), doublei_value);
    }

    public void addMsg(HttpPortUpMessageType key, String value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), value);
    }

    public void addMsg(HttpPortUpMessageType key, Object value) throws JSONException {
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        paramsJSON.put(key.getParamName(), value);
    }

    public String getMsg(){
        if (null == paramsJSON){
            paramsJSON = new JSONObject();
        }
        return paramsJSON.toString();
    }

    public String getUrl(){
        if (TextUtils.isEmpty(url))
            url = "undefind url";
        return url;
    }



}
