package com.gmself.studio.mg.basemodule.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guomeng on 4/6.
 */

public class ServiceCallBackManager <T>{
    private static final ServiceCallBackManager ourInstance = new ServiceCallBackManager();

    public static ServiceCallBackManager getInstance() {
        return ourInstance;
    }

    private ServiceCallBackManager() {
    }

    public static final int UN_DEFINE = -1;

    private Map<ServiceCallBackType, ServiceCallBack> map = new HashMap<>();

    public synchronized void registerServiceCallBack(ServiceCallBackType type, ServiceCallBack callBack){
        map.put(type, callBack);
    }

    public synchronized void unRegisterServiceCallBack(ServiceCallBackType type){
        if (map.containsKey(type)){
            map.remove(type);
        }
    }

    public synchronized int callback(ServiceCallBackType type, T param){
        if (!map.containsKey(type)){
            return UN_DEFINE;
        }
        return map.get(type).callback(param);
    }
}
